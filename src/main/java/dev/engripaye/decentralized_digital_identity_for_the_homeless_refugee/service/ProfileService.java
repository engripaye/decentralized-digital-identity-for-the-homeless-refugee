package dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.service;

import dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.model.UserProfile;
import dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.repository.UserProfileRepo;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final UserProfileRepo userProfileRepo;

    public ProfileService(UserProfileRepo userProfileRepo) {
        this.userProfileRepo = userProfileRepo;
    }

    public UserProfileRepo getOrCreateBySubject(String sub) {
        return userProfileRepo.findBySubject(sub)
                .orElseGet(() -> userProfileRepo.save(newUser(sub)));
    }

    public UserProfile update(String sub, UserProfile dto){

    }
}
