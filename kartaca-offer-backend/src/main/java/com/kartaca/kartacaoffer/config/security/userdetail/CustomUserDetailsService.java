package com.kartaca.kartacaoffer.config.security.userdetail;

import com.kartaca.kartacaoffer.entities.User;
import com.kartaca.kartacaoffer.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if (user==null)
            throw new UsernameNotFoundException("User not found");

        return new CustomUserDetails(user);
    }
}
