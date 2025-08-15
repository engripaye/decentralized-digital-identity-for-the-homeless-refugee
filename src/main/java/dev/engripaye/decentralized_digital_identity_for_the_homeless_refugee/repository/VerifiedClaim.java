package dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerifiedClaim extends JpaRepository<VerifiedClaim, Long> {

    List<VerifiedClaim> findBySubject(String subject);
}
