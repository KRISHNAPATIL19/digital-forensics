
package com.forensics.digital_forensics.identity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvestigatorRepository
        extends JpaRepository<Investigator, UUID> {

    Optional<Investigator> findByUsername(String username);

    Optional<Investigator> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}

