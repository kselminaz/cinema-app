package group.aist.cinemaapp.service;
import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.HallRequest;
import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.dto.response.LanguageResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.HallStatus;
import group.aist.cinemaapp.model.Hall;

import java.util.List;
public interface HallService {

    HallResponse getHallById(Long id);

    void saveHall(HallRequest request);

    void updateHall(Long id, HallRequest request);

    void updateHallWithStatus(Long id, HallStatus status);

    void deleteHall(Long id);
    PageableResponse<HallResponse> getHall(PageCriteria pageCriteria);

    Hall getHallIfExist(Long id);

}