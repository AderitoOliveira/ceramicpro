package nuvemdesoftware.ceramicpro.services;

import nuvemdesoftware.ceramicpro.model.RefreshToken;
import nuvemdesoftware.ceramicpro.model.User;
import nuvemdesoftware.ceramicpro.repository.RefreshTokenRepository;
import nuvemdesoftware.ceramicpro.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * @author mhmdz
 * Created By Zeeshan on 20-05-2023
 * @project oauth-jwt
 */

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private final UsersRepository usersRepository;

    public RefreshTokenService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public RefreshToken createRefreshToken(String email){

        Optional<User> user = usersRepository.findByEmail(email);

        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(user.get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }



    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;

    }

}