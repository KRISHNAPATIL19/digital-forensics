
package com.forensics.digital_forensics.casework;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CaseRepository
        extends JpaRepository<Case, UUID>,
                JpaSpecificationExecutor<Case> {
}

