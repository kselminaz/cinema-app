package group.aist.cinemaapp.controller;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.LanguageCreateRequest;
import group.aist.cinemaapp.dto.request.LanguageUpdateRequest;
import group.aist.cinemaapp.dto.response.LanguageResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.service.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/languages")
public class LanguageController {
    
    private final LanguageService languageService;

    @GetMapping("/{id}")
    public LanguageResponse getLanguageById(@PathVariable Long id) {

        return languageService.getLanguageById(id);
    }

    @GetMapping
    public PageableResponse<LanguageResponse> getLanguages(PageCriteria pageCriteria) {
        return languageService.getLanguages(pageCriteria);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void saveLanguage(@Valid @RequestBody LanguageCreateRequest request) {

        languageService.saveLanguage(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteLanguage(@PathVariable Long id) {
        languageService.deleteLanguage(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateLanguage(@PathVariable Long id,@Valid @RequestBody LanguageUpdateRequest request) {

        languageService.updateLanguage(id, request);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateLanguageWithStatus(@PathVariable Long id,@RequestParam String status) {

        languageService.updateLanguageWithStatus(id, status);
    }


}
