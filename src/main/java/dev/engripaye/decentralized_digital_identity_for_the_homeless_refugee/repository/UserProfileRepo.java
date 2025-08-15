package dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.repository;

import dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepo extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findBySubject(String subject);
}
