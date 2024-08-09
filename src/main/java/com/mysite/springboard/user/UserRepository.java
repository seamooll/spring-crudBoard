package com.mysite.springboard.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> { //PK인 id가 Long
    Optional<SiteUser> findByusername(String username);
}
