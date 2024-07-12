package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.HallRequest;
import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.dto.response.LanguageResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.HallStatus;
import group.aist.cinemaapp.exception.NotFoundException;
import group.aist.cinemaapp.mapper.HallMapper;
import group.aist.cinemaapp.model.Hall;
import group.aist.cinemaapp.repository.HallRepository;
import group.aist.cinemaapp.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {
    private final HallRepository hallRepository;
    private final HallMapper hallMapper;

   @Override
   public HallResponse getHallById(Long id) {
//        Hall hall = getHallIfExist(id);
//        HallResponse hallResponse = hallMapper.toHallResponse(hall);
//        hallResponse.setStatus(HallStatus.findById(hall.getStatus()).toString());
        return null;
    }

    @Override
    public PageableResponse<HallResponse> getHall(PageCriteria pageCriteria) {
        return (PageableResponse<HallResponse>) List.of();
    }

    @Override
    public void saveHall(HallRequest request) {

    }

    @Override
    public void updateHall(Long id, HallRequest request) {

    }

    @Override
    public void updateHallWithStatus(Long id, HallStatus status) {

    }

    @Override
    public void deleteHall(Long id) {

    }


    @Override
    public Hall getHallIfExist(Long id) {
        return hallRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "Hall with id [%d] was not found!", id)));

    }
}