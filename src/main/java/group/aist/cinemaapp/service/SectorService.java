package group.aist.cinemaapp.service;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.SectorRequest;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.dto.response.SectorResponse;
import group.aist.cinemaapp.enums.SectorStatus;
import group.aist.cinemaapp.model.Hall;

public interface SectorService {
    SectorResponse getSectorById(Long id);

    void saveSector(SectorRequest request);

    void updateSector(Long id, SectorRequest request);

    void updateSectorWithStatus(Long id, SectorStatus status);

    void deleteSector(Long id);
    PageableResponse<SectorResponse> getSector(PageCriteria pageCriteria);

    Hall getSectorIfExist(Long id);

}

