package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.SectorCreateRequest;
import group.aist.cinemaapp.dto.request.SectorUpdateRequest;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.dto.response.SectorResponse;
import group.aist.cinemaapp.enums.HallStatus;
import group.aist.cinemaapp.enums.SectorStatus;
import group.aist.cinemaapp.mapper.SectorMapper;
import group.aist.cinemaapp.model.Sector;
import group.aist.cinemaapp.repository.SectorRepository;
import group.aist.cinemaapp.service.HallService;
import group.aist.cinemaapp.service.SectorService;
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
public class SectorServiceImpl implements SectorService {
    private final SectorRepository sectorRepository;
    private final SectorMapper sectorMapper;
    private final HallService hallService;

    @Override
    public SectorResponse getSectorById(Long id) {
        var entity = fetchSectorIfExist(id);
        return sectorMapper.toResponse(entity);
    }

    @Override
    public void saveSector(SectorCreateRequest request) {
        var entity = sectorMapper.toEntity(request);

        entity.setStatus(ACTIVE.getId());
        addRelations(entity, request.getHallId());
        sectorRepository.save(entity);
    }

    @Override
    public void updateSector(Long id, SectorUpdateRequest request) {

        var entity = fetchSectorIfExist(id);
        ofNullable(request.getName()).ifPresent(entity::setName);
        ofNullable(request.getStatus()).ifPresent(status -> entity
                .setStatus(SectorStatus.valueOf(request.getStatus()).getId()));
        addRelations(entity, request.getHallId());
        sectorRepository.save(entity);

    }

    @Override
    public void updateSectorWithStatus(Long id, String status) {

        var entity = fetchSectorIfExist(id);
        var sectorStatus = Arrays.stream(SectorStatus.values()).filter(sector -> sector.name().
                        equalsIgnoreCase(status)).
                findFirst().orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, String.format(
                        "Sector Status with status [%s] was not found!", status
                )));
        entity.setStatus(sectorStatus.getId());
        sectorRepository.save(entity);

    }

    @Override
    public void deleteSector(Long id) {

        var entity = fetchSectorIfExist(id);
        entity.setStatus(HallStatus.DELETED.getId());
        sectorRepository.save(entity);

    }

    @Override
    public PageableResponse<SectorResponse> getSectors(PageCriteria pageCriteria) {
        var resultsPage = sectorRepository.findAllByStatusIs(PageRequest.of(pageCriteria.getPage(),
                pageCriteria.getCount(), Sort.by(ASC, "name")), ACTIVE.getId());
        return sectorMapper.toPageableResponse(resultsPage);
    }

    @Override
    public Sector fetchSectorIfExist(Long id) {
        return sectorRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(NOT_FOUND, String.format(
                "Hall with id [%d] was not found!", id
        )));
    }

    private void addRelations(Sector entity, Long hallId) {
        if (hallId != null) {
            var hall = hallService.fetchHallIfExist(hallId);
            entity.setHall(hall);
        }
    }
}
