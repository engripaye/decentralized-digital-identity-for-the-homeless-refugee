package dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserProfile {

    @Id @GeneratedValue
    private Long id;

    // SUB FROM OIDC AS STABLE USER ID
    @Column(unique = true, nullable = false)
    private String subject;

    //MINIMAL PII (USER CONTROLS WHAT'S SHARED)
    private String preferredName;
    private String birthYear;
    private String countryOfOrigin;
    private String contactPhone;


    // LINKS TO UPLOADED DOCS (HASHES/PATHS)
    private String docPointer;

    // Consent flags
    private boolean contentShareAge;
    private boolean contentShareVaccination;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getDocPointer() {
        return docPointer;
    }

    public void setDocPointer(String docPointer) {
        this.docPointer = docPointer;
    }

    public boolean isContentShareAge() {
        return contentShareAge;
    }

    public void setContentShareAge(boolean contentShareAge) {
        this.contentShareAge = contentShareAge;
    }

    public boolean isContentShareVaccination() {
        return contentShareVaccination;
    }

    public void setContentShareVaccination(boolean contentShareVaccination) {
        this.contentShareVaccination = contentShareVaccination;
    }
}
