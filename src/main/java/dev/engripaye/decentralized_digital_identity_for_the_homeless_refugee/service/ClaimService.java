package dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.service;

import com.nimbusds.jose.KeyLengthException;
import dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.model.VerifiedClaim;
import dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.repository.VerifiedClaimRepo;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ClaimService {

    private final VerifiedClaimRepo verifiedClaimRepo;
    private final SignInService signInService;

    public ClaimService(VerifiedClaimRepo verifiedClaimRepo, SignInService signInService) {
        this.verifiedClaimRepo = verifiedClaimRepo;
        this.signInService = signInService;
    }

    public VerifiedClaim issueClaim(String subject, String type, String value){
        var vc = new VerifiedClaim();
        vc.setSubject(subject);
        vc.setIssuerOrgId("NGO_DEFAULT");
        vc.setType(type);
        vc.setIssuedAt(Instant.now());
        vc.setExpiresAt(Instant.now().plus(180, ChronoUnit.DAYS));
        vc.setJws(signInService.signSimple(subject, type, value));
        return verifiedClaimRepo.save(vc);

    }

    public List<VerifiedClaim> forSubject(String subject){
        return verifiedClaimRepo.findBySubject(subject);
    }
}
