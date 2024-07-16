package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.dto.request.SeatCreateRequest;
import group.aist.cinemaapp.dto.request.SeatUpdateRequest;
import group.aist.cinemaapp.dto.response.SeatResponse;
import group.aist.cinemaapp.enums.MovieSessionStatus;
import group.aist.cinemaapp.enums.MovieStatus;
import group.aist.cinemaapp.enums.SeatStatus;
import group.aist.cinemaapp.mapper.SeatMapper;
import group.aist.cinemaapp.model.Seat;
import group.aist.cinemaapp.model.Sector;
import group.aist.cinemaapp.repository.SeatRepository;
import group.aist.cinemaapp.service.SeatService;
import group.aist.cinemaapp.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static group.aist.cinemaapp.enums.SeatStatus.*;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    private final SectorService sectorService;


    @Override
    public SeatResponse getSeatById(Long id) {
        Seat seat = getSeatIfExist(id);
        if (seat.getStatus() == DELETED.getId()) {
            throw new ResponseStatusException(NOT_FOUND, "Seat with id " + id + " is DELETED");
        }
        return seatMapper.toSeatResponse(seat);
    }

    @Override
    public List<SeatResponse> getSeats() {
        List<Seat> seats = (List<Seat>) seatRepository.findAll();
        return seatMapper.getSeatList(seats);
    }

    @Override
    public void saveSeat(SeatCreateRequest seatCreateRequest) {
        Seat seat = seatMapper.toSeat(seatCreateRequest);
        seat.setStatus(AVAILABLE.getId());
        addRelations(seat, seatCreateRequest.getSector());
        seatRepository.save(seat);
    }


    @Override
    public void updateSeat(Long id, SeatUpdateRequest seatUpdateRequest) {

        Seat seat = getSeatIfExist(id);
        ofNullable(seatUpdateRequest.getRow()).ifPresent(seat::setRow);
        ofNullable(seatUpdateRequest.getSeat_number()).ifPresent(seat::setSeat_number);
        ofNullable(seatUpdateRequest.getStatus()).ifPresent(status -> seat.setStatus(SeatStatus.valueOf(seatUpdateRequest.getStatus()).getId()));
        addRelations(seat, seatUpdateRequest.getSector());
        seatRepository.save(seat);
    }

    @Override
    public void updateSeatStatus(Long id, String status) {
        Seat seatEntity = getSeatIfExist(id);
        SeatStatus seatStatus = Arrays.stream(SeatStatus.values()).filter(seat -> seat.name().equalsIgnoreCase(status)).findFirst().orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, String.format(
                "Movie Status with status [%s] was not found!", status)));
        seatEntity.setStatus(seatStatus.getId());
        seatRepository.save(seatEntity);
    }

    @Override
    public void deleteSeatById(Long id) {
        Seat seat = getSeatIfExist(id);
        seat.setStatus(DELETED.getId());
        seatRepository.save(seat);
    }

    @Override
    public Seat getSeatIfExist(Long id) {
        return seatRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Seat with id [" + id + "] is not found."));

    }

    @Override
    public void bookSeatById(Long id) {
        Seat seat = getSeatIfExist(id);
        seat.setStatus(BOOKED.getId());
        seatRepository.save(seat);

    }

    private void addRelations(Seat seat, Long sectorId) {
        if (sectorId != null) {
            Sector sector = sectorService.fetchSectorIfExist(sectorId);
            seat.setSector(sector);
        }
    }
}
