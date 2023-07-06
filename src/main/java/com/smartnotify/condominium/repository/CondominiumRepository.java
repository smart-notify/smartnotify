package com.smartnotify.condominium.repository;

import com.smartnotify.condominium.model.Condominium;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface CondominiumRepository extends JpaRepository<Condominium, String> {
    Optional<Condominium> findByEmail(String email);

}
