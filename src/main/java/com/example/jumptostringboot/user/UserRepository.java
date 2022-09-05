package com.example.jumptostringboot.user;

import com.example.jumptostringboot.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Integer> {

    Optional<SiteUser> findByUsername(String username);

    Optional<SiteUser> findByNicknameAndEmail( String nickname, String email);



}
