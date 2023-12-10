package org.flowio.authenticationservice.repository;

import org.flowio.authenticationservice.model.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {
    Optional<LoginUser> findByLoginEmail(String loginEmail);


}
