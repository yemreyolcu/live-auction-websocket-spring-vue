package com.kartaca.kartacaoffer.repositories;

import com.kartaca.kartacaoffer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}