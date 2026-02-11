package fr.ensitech.copalive_backend.service;

import fr.ensitech.copalive_backend.entity.User;
import fr.ensitech.copalive_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- Import important
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(String email, String password) {
        if (userRepository.existsByMail(email)) {
            throw new RuntimeException("Cet email est déjà utilisé !");
        }
        User newUser = new User();
        newUser.setMail(email);

        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);

        return userRepository.save(newUser);
    }

    public User login(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByMail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return user;
            }
        }
        throw new RuntimeException("Email ou mot de passe incorrect");
    }
}
