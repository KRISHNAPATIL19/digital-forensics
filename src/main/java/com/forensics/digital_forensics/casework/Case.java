
package com.forensics.digital_forensics.casework;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "investigation_cases")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CaseStatus status = CaseStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CasePriority priority = CasePriority.MEDIUM;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    public Case(
            String title,
            String description,
            CasePriority priority
    ) {
        this.title = title;
        this.description = description;
        this.priority = priority == null
                ? CasePriority.MEDIUM
                : priority;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public void updateDetails(
            String title,
            String description,
            CasePriority priority
    ) {
        this.title = title;
        this.description = description;
        this.priority = priority == null
                ? CasePriority.MEDIUM
                : priority;
    }

    public void changeStatus(CaseStatus status) {
        if (status == null) {
            throw new IllegalArgumentException(
                    "Case status must not be null"
            );
        }

        this.status = status;
    }
}
