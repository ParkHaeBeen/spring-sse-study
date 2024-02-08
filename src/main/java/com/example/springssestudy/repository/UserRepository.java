package com.example.springssestudy.repository;

import com.example.springssestudy.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {

  Optional <User> findByUserName(String username);
}
