package nuvemdesoftware.ceramicpro.services;


import jakarta.servlet.http.HttpServletResponse;
import nuvemdesoftware.ceramicpro.dto.RefreshTokenRequestDTO;
import nuvemdesoftware.ceramicpro.exception.TokenRefreshException;
import nuvemdesoftware.ceramicpro.model.RefreshToken;
import nuvemdesoftware.ceramicpro.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import nuvemdesoftware.ceramicpro.model.User;
import nuvemdesoftware.ceramicpro.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import nuvemdesoftware.ceramicpro.security.dao.JwtAuthenticationResponse;
import nuvemdesoftware.ceramicpro.security.dao.SignUpRequest;
import nuvemdesoftware.ceramicpro.security.dao.SigninRequest;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${token.cookieExpiryTime}")
    private int cookieExpiryTime;
    @Value("${token.refreshTokenServiceTokenExpiryTime}")
    private int refreshTokenServiceTokenExpiryTime;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        usersRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request, HttpServletResponse response) {
//
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if (auth.isAuthenticated()) {
            User user = usersRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
            String jwtToken = jwtService.generateToken(user);

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
            ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken.getToken())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    //.sameSite("None")
                    .path("/")
                    .maxAge(refreshTokenServiceTokenExpiryTime)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return JwtAuthenticationResponse.builder().accessToken(jwtToken).build();
        } else {
            throw new UsernameNotFoundException("Invalid email or password.");
        }
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO, HttpServletResponse response) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo);
                    return JwtAuthenticationResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new TokenRefreshException("Refresh token was expired. Please make a new signin request"));
    }
}