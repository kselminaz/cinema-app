package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.LanguageCreateRequest;
import group.aist.cinemaapp.dto.request.LanguageUpdateRequest;
import group.aist.cinemaapp.dto.response.LanguageResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.LanguageStatus;
import group.aist.cinemaapp.mapper.LanguageMapper;
import group.aist.cinemaapp.model.Language;
import group.aist.cinemaapp.repository.LanguageRepository;
import group.aist.cinemaapp.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static group.aist.cinemaapp.enums.LanguageStatus.VISIBLE;
import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    @Override
    public LanguageResponse getLanguageById(Long id) {
        var entity = fetchLanguageIfExist(id);
        return languageMapper.toResponse(entity);
    }

    @Override
    public void saveLanguage(LanguageCreateRequest request) {
        var entity = languageMapper.toEntity(request);
        entity.setStatus(VISIBLE.getId());
        languageRepository.save(entity);
    }

    @Override
    public void updateLanguage(Long id, LanguageUpdateRequest request) {

        var entity = fetchLanguageIfExist(id);
        ofNullable(request.getIsoCode()).ifPresent(entity::setIsoCode);
        ofNullable(request.getTitle()).ifPresent(entity::setTitle);
        ofNullable(request.getStatus()).ifPresent(status -> entity.setStatus(LanguageStatus.valueOf(request.getStatus()).getId()));
        languageRepository.save(entity);

    }

    @Override
    public void updateLanguageWithStatus(Long id, String status) {

        var entity = fetchLanguageIfExist(id);
        var langStatus = Arrays.stream(LanguageStatus.values()).filter(lang -> lang.name().equalsIgnoreCase(status)).findFirst().orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, String.format(
                "Language Status with status [%s] was not found!", status
        )));
        entity.setStatus(langStatus.getId());
        languageRepository.save(entity);

    }

    @Override
    public void deleteLanguage(Long id) {

        var entity = fetchLanguageIfExist(id);
        entity.setStatus(LanguageStatus.DELETED.getId());
        languageRepository.save(entity);

    }

    @Override
    public PageableResponse<LanguageResponse> getLanguages(PageCriteria pageCriteria) {
        var resultsPage = languageRepository.findAllByStatusIs(PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount(), Sort.by(ASC, "title")), VISIBLE.getId());
        return languageMapper.toPageableResponse(resultsPage);
    }

    @Override
    public Language fetchLanguageIfExist(Long id) {
        return languageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format(
                "Language with id [%d] was not found!", id
        )));
    }
}