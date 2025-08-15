package dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.service;

import dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.model.UserProfile;
import dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.repository.UserProfileRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.Year;
import java.util.Optional;

@Service
public class ProfileService {

    private final UserProfileRepo userProfileRepo;

    public ProfileService(UserProfileRepo userProfileRepo) {
        this.userProfileRepo = userProfileRepo;
    }

    public UserProfileRepo getOrCreateBySubject(String sub) {
        return (UserProfileRepo) userProfileRepo.findBySubject(sub)
                .orElseGet(() -> userProfileRepo.save(newUser(sub)));
    }

    public UserProfile update(String sub, UserProfile dto){
        var p = userProfileRepo.findBySubject(sub).orElseThrow();

        // only allow safe fields
        p.setPreferredName(dto.getPreferredName());
        p.setBirthYear(dto.getBirthYear());
        p.setCountryOfOrigin(dto.getCountryOfOrigin());
        p.setContactPhone(dto.getContactPhone());
        p.setContentShareAge(dto.isContentShareAge());
        p.setContentShareVaccination(dto.isContentShareVaccination());

        return userProfileRepo.save(p);
    }

    public String readAgeGroupIfConsented(String subject) throws AccessDeniedException {
        var p = userProfileRepo.findBySubject(subject).orElseThrow();
        if(!p.isContentShareAge()) throw new AccessDeniedException("No consent");
        var year = Optional.ofNullable(p.getBirthYear()).orElse("unknown");
        return switch (year) {
            case "unknown" -> "UNDECLARED";
            default -> computeGroup(year);
        };
    }

    private String computeGroup(String birthYear){
        try {
            int y = Integer.parseInt(birthYear);
            int age = Year.now().getValue() - y;
            if (age < 12) return "CHILD";
            if (age < 18) return "YOUTH";
            if (age < 60) return "ADULT";
            return "SENIOR";
        } catch (NumberFormatException e) {
            return "UNDECLARED";
        }
    }

    private UserProfile newUser(String sub){
        var u = new UserProfile();
        u.setSubject(sub);
        u.setContentShareAge(false);
        u.setContentShareVaccination(false);
        return u;
    }

}
