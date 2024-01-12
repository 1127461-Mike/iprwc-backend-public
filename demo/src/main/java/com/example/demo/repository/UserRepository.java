package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
@Component
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String username);


}
