package com.kartaca.kartacaoffer.services;

import com.kartaca.kartacaoffer.config.security.jwt.services.JwtUtil;
import com.kartaca.kartacaoffer.entities.User;
import com.kartaca.kartacaoffer.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final HttpServletRequest request;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.request = request;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public Iterable<User> userList(){
        return this.userRepository.findAll();
    }

    public User userRetrieve(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public User updateUser(User user, Long id){
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()){
            User updatedUser = userOptional.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            return this.userRepository.save(updatedUser);
        }
        return null;
    }

    public void userDelete(Long id) {
        this.userRepository.deleteById(id);
    }

    public User userRetrieveByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }


}
