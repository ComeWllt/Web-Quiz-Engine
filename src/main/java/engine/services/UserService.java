package engine.services;

import engine.dto.AccountDTO;
import engine.exceptions.UsernameAlreadyTakenException;
import engine.models.Role;
import engine.models.User;
import engine.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("Email[%s] not found", s));
        }
    }

    public long registerUser(AccountDTO accountDTO) {
        if (userRepository.findByUsername(accountDTO.getEmail()).isPresent()) {
            throw new UsernameAlreadyTakenException(accountDTO.getEmail());
        }
        User user = new User();
        user.setUsername(accountDTO.getEmail());
        user.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        user.grantAuthority(Role.ROLE_USER);
        return userRepository.save(user).getId();
    }

}
