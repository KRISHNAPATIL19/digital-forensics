
package com.forensics.digital_forensics.casework;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/cases")
public class CaseController {

    private final CaseService service;

    public CaseController(CaseService service) {
        this.service = service;
    }

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

    public record UpdateStatusRequest(CaseStatus status) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Case create(@Valid @RequestBody CreateCaseRequest request) {
        return service.create(new Case(
                request.title(),
                request.description(),
                request.priority() == null
                        ? CasePriority.MEDIUM : request.priority()
        ));
    }

    @GetMapping
    public Page<Case> getAll(
            @PageableDefault(size = 20, sort = "createdAt")
            Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Case getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Case update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCaseRequest request) {
        return service.update(id, new Case(
                request.title(),
                request.description(),
                request.priority() == null
                        ? CasePriority.MEDIUM : request.priority()
        ));
    }

    @PatchMapping("/{id}/status")
    public Case updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateStatusRequest request) {
        if (request.status() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Status is required");
        }
        return service.updateStatus(id, request.status());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}

