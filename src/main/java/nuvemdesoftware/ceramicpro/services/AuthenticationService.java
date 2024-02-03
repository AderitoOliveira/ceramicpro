package nuvemdesoftware.ceramicpro.services;


import nuvemdesoftware.ceramicpro.security.dao.JwtAuthenticationResponse;
import nuvemdesoftware.ceramicpro.security.dao.SignUpRequest;
import nuvemdesoftware.ceramicpro.security.dao.SigninRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}