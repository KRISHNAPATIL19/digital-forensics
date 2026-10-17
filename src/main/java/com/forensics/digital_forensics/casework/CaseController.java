
package com.forensics.digital_forensics.casework;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cases")
@RequiredArgsConstructor
public class CaseController {

    private final CaseService service;

    public record CreateCaseRequest(
            @NotBlank @Size(max = 150) String title,
            @NotBlank String description,
            CasePriority priority
    ) {}

    public record UpdateCaseRequest(
            @NotBlank @Size(max = 150) String title,
            @NotBlank String description,
            CasePriority priority
    ) {}

    public record UpdateStatusRequest(
            @NotNull CaseStatus status
    ) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Case create(
            @Valid @RequestBody CreateCaseRequest request
    ) {
        CasePriority priority = request.priority() == null
                ? CasePriority.MEDIUM
                : request.priority();

        return service.create(new Case(
                request.title(),
                request.description(),
                priority
        ));
    }

    @GetMapping
    public Page<Case> getAll(
            @PageableDefault(size = 20, sort = "createdAt")
            Pageable pageable
    ) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Case getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Case update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCaseRequest request
    ) {
        CasePriority priority = request.priority() == null
                ? CasePriority.MEDIUM
                : request.priority();

        return service.update(id, new Case(
                request.title(),
                request.description(),
                priority
        ));
    }

    @PatchMapping("/{id}/status")
    public Case updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateStatusRequest request
    ) {
        return service.updateStatus(id, request.status());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
