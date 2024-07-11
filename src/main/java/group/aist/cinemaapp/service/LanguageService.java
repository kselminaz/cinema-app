package group.aist.cinemaapp.service;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.LanguageRequest;
import group.aist.cinemaapp.dto.response.LanguageResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.LanguageStatus;
import group.aist.cinemaapp.model.Language;

public interface LanguageService {

    LanguageResponse getLanguageById(Long id);

    void saveLanguage(LanguageRequest request);

    void updateLanguage(Long id, LanguageRequest request);

    void updateLanguageWithStatus(Long id, LanguageStatus status);

    void deleteLanguage(Long id);

    PageableResponse<LanguageResponse> getLanguages(PageCriteria pageCriteria);

    Language fetchLanguageIfExist(Long id);
}
