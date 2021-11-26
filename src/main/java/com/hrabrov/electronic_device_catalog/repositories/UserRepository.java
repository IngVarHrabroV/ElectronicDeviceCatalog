package com.hrabrov.electronic_device_catalog.repositories;

import com.hrabrov.electronic_device_catalog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
