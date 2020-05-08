package dev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Collegue;

public interface CollegueRepo extends JpaRepository<Collegue, Integer> {

    Optional<Collegue> findByEmail(String email);
}
