package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.dto.request.HallRequest;
import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.enums.HallStatus;
import group.aist.cinemaapp.mapper.HallMapper;
import group.aist.cinemaapp.mapper.LanguageMapper;
import group.aist.cinemaapp.model.Hall;
import group.aist.cinemaapp.repository.HallRepository;
import group.aist.cinemaapp.repository.LanguageRepository;
import group.aist.cinemaapp.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;
    private final HallMapper hallMapper;

    @Override
    public HallResponse getHallById(Long id) {
        return null;
    }

    @Override
    public List<HallResponse> getHall() {
        return null;
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
    public Hall fetchAuthorIfExist(Long id) {
        return null;
    }
}
