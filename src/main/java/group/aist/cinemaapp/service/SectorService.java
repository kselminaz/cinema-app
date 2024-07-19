package group.aist.cinemaapp.service;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.SectorCreateRequest;
import group.aist.cinemaapp.dto.request.SectorUpdateRequest;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.dto.response.SectorResponse;
import group.aist.cinemaapp.model.Sector;

public interface SectorService {
    SectorResponse getSectorById(Long id);

    void saveSector(SectorCreateRequest request);

    void updateSector(Long id, SectorUpdateRequest request);

    void updateSectorWithStatus(Long id, String status);

    void deleteSector(Long id);

    PageableResponse<SectorResponse> getSectors(PageCriteria pageCriteria);

    Sector fetchSectorIfExist(Long id);
}

