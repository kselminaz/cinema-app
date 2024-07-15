package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.HallCreateRequest;
import group.aist.cinemaapp.dto.request.HallUpdateRequest;
import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.HallStatus;
import group.aist.cinemaapp.mapper.HallMapper;
import group.aist.cinemaapp.model.Hall;
import group.aist.cinemaapp.repository.HallRepository;
import group.aist.cinemaapp.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static group.aist.cinemaapp.enums.HallStatus.ACTIVE;
import static java.util.Optional.ofNullable;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {
    private final HallRepository hallRepository;
    private final HallMapper hallMapper;

    @Override
    public HallResponse getHallById(Long id) {
        var entity = fetchHallIfExist(id);
        return hallMapper.toResponse(entity);
    }

    @Override
    public void saveHall(HallCreateRequest request) {
        var entity = hallMapper.toEntity(request);
        entity.setStatus(ACTIVE.getId());
        hallRepository.save(entity);
    }

    @Override
    public void updateHall(Long id, HallUpdateRequest request) {

        var entity = fetchHallIfExist(id);
        ofNullable(request.getName()).ifPresent(entity::setName);
        ofNullable(request.getTotalSeatsCount()).ifPresent(entity::setTotalSeatsCount);
        ofNullable(request.getStatus()).ifPresent(status -> entity
                .setStatus(HallStatus.valueOf(request.getStatus()).getId()));
        hallRepository.save(entity);
    }

    @Override
    public void updateHallWithStatus(Long id, String status) {

        var entity = fetchHallIfExist(id);
        var hallStatus = Arrays.stream(HallStatus.values()).filter(hall -> hall.name().
                        equalsIgnoreCase(status)).
                findFirst().orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, String.format(
                        "Hall Status with status [%s] was not found!", status
                )));
        entity.setStatus(hallStatus.getId());
        hallRepository.save(entity);

    }

    @Override
    public void deleteHall(Long id) {

        var entity = fetchHallIfExist(id);
        entity.setStatus(HallStatus.DELETED.getId());
        hallRepository.save(entity);

    }

    @Override
    public PageableResponse<HallResponse> getHalls(PageCriteria pageCriteria) {
        var resultsPage = hallRepository.findAllByStatusIs(PageRequest.of(pageCriteria.getPage(),
                pageCriteria.getCount(), Sort.by(ASC, "name")), ACTIVE.getId());
        return hallMapper.toPageableResponse(resultsPage);
    }

    @Override
    public Hall fetchHallIfExist(Long id) {
        return hallRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(NOT_FOUND, String.format(
                "Hall with id [%d] was not found!", id
        )));
    }
}