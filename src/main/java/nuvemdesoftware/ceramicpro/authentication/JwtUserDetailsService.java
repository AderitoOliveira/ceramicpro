package nuvemdesoftware.ceramicpro.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nuvemdesoftware.ceramicpro.model.Role;
import nuvemdesoftware.ceramicpro.model.User;
import nuvemdesoftware.ceramicpro.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = passwordEncoder();

        List<User> optionalUser = usersRepository.findByUsername(username);

        if(optionalUser!=null) {
            User users = optionalUser.get(0);

            List<String> roleList = new ArrayList<String>();
            for(Role role:users.getRoles()) {
                roleList.add(role.getRoleName());
            }

            return org.springframework.security.core.userdetails.User.builder()
                    .username(users.getUsername())
                    //change here to store encoded password in db
                    .password( encoder.encode(users.getPassword()) )
                    .disabled(users.isDisabled())
                    .accountExpired(users.isAccountExpired())
                    .accountLocked(users.isAccountLocked())
                    .credentialsExpired(users.isCredentialsExpired())
                    .roles(roleList.toArray(new String[0]))
                    .build();
        } else {
            throw new UsernameNotFoundException("User Name is not Found");
        }
        /*
        if ("techgeeknext".equals(username)) {
            return new User("techgeeknext", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        */
    }

}