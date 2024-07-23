package group.aist.cinemaapp.controller;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.SectorCreateRequest;
import group.aist.cinemaapp.dto.request.SectorUpdateRequest;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.dto.response.SectorResponse;
import group.aist.cinemaapp.service.SectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/sectors")
public class SectorController {

    private final SectorService sectorService;

    @GetMapping("/{id}")
    public SectorResponse getSectorById(@PathVariable Long id) {
        return sectorService.getSectorById(id);
    }

    @GetMapping
    public PageableResponse<SectorResponse> getSectors(PageCriteria pageCriteria) {
        return sectorService.getSectors(pageCriteria);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(CREATED)
    public void saveSector(@Valid @RequestBody SectorCreateRequest request) {
        sectorService.saveSector(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteSector(@PathVariable Long id) {
        sectorService.deleteSector(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateSector(@PathVariable Long id, @Valid @RequestBody SectorUpdateRequest request) {
        sectorService.updateSector(id, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateSectorWithStatus(@PathVariable Long id, @RequestParam String status) {
        sectorService.updateSectorWithStatus(id, status);
    }
}

