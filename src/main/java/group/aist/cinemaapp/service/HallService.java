package group.aist.cinemaapp.service;
import group.aist.cinemaapp.dto.request.HallRequest;
import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.enums.HallStatus;
import group.aist.cinemaapp.model.Hall;

import java.util.List;
public interface HallService {

    HallResponse getHallById(Long id);

    List<HallResponse> getHall();

    void saveHall(HallRequest request);

    void updateHall(Long id, HallRequest request);

    void updateHallWithStatus(Long id, HallStatus status);

    void deleteHall(Long id);

    Hall fetchAuthorIfExist(Long id);


}