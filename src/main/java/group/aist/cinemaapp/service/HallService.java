package group.aist.cinemaapp.service;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.HallCreateRequest;
import group.aist.cinemaapp.dto.request.HallUpdateRequest;
import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.model.Hall;

public interface HallService {

    HallResponse getHallById(Long id);

    void saveHall(HallCreateRequest request);

    void updateHall(Long id, HallUpdateRequest request);

    void updateHallWithStatus(Long id, String status);

    void deleteHall(Long id);

    PageableResponse<HallResponse> getHalls(PageCriteria pageCriteria);

    Hall fetchHallIfExist(Long id);

}