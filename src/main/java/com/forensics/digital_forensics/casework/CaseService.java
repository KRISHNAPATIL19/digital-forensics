
package com.forensics.digital_forensics.casework;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CaseService {

    private final CaseRepository repository;

    public Case create(Case request) {
        return repository.save(request);
    }

    @Transactional(readOnly = true)
    public Page<Case> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Case getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Case not found"
                ));
    }

    public Case update(UUID id, Case request) {
        Case existing = getById(id);

        existing.updateDetails(
                request.getTitle(),
                request.getDescription(),
                request.getPriority()
        );

        return existing;
    }

    public Case updateStatus(UUID id, CaseStatus status) {
        Case existing = getById(id);
        existing.changeStatus(status);

        return existing;
    }

    public void delete(UUID id) {
        Case existing = getById(id);
        repository.delete(existing);
    }
}
