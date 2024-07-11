package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.LanguageRequest;
import group.aist.cinemaapp.dto.response.LanguageResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.LanguageStatus;
import group.aist.cinemaapp.exception.NotFoundException;
import group.aist.cinemaapp.mapper.LanguageMapper;
import group.aist.cinemaapp.model.Language;
import group.aist.cinemaapp.repository.LanguageRepository;
import group.aist.cinemaapp.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Direction.ASC;

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
    public void saveLanguage(LanguageRequest request) {
        var entity = languageMapper.toEntity(request);
        entity.setStatus(LanguageStatus.VISIBLE.getValue());
        languageRepository.save(entity);
    }

    @Override
    public void updateLanguage(Long id, LanguageRequest request) {

    }

    @Override
    public void updateLanguageWithStatus(Long id, LanguageStatus status) {

    }

    @Override
    public void deleteLanguage(Long id) {

    }

    @Override
    public PageableResponse<LanguageResponse> getLanguages(PageCriteria pageCriteria) {
        return null;
    }

    @Override
    public Language fetchLanguageIfExist(Long id) {
        return languageRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "Language with id [%d] was not found!", id
        )));
    }
}
