package nuvemdesoftware.ceramicpro.services;


import jakarta.servlet.http.HttpServletResponse;
import nuvemdesoftware.ceramicpro.dto.RefreshTokenRequestDTO;
import nuvemdesoftware.ceramicpro.security.dao.JwtAuthenticationResponse;
import nuvemdesoftware.ceramicpro.security.dao.SignUpRequest;
import nuvemdesoftware.ceramicpro.security.dao.SigninRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request,  HttpServletResponse response);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequestDTO request, HttpServletResponse response);
}