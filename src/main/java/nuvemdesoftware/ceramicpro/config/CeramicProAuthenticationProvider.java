package nuvemdesoftware.ceramicpro.config;

import nuvemdesoftware.ceramicpro.model.Authority;
import nuvemdesoftware.ceramicpro.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import nuvemdesoftware.ceramicpro.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CeramicProAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        List<User> user = usersRepository.findByUsername(username);
        if (user.size() > 0) {
            if (passwordEncoder.matches(pwd, user.get(0).getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities(user.get(0).getAuthorities()));
            } else {
                throw new BadCredentialsException("Invalid password!");
            }
        }else {
            throw new BadCredentialsException("No user registered with this details!");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        //return false;
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
