package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.dto.request.SeatRequest;
import group.aist.cinemaapp.dto.response.SeatResponse;
import group.aist.cinemaapp.enums.SeatStatus;
import group.aist.cinemaapp.mapper.SeatMapper;
import group.aist.cinemaapp.model.Seat;
import group.aist.cinemaapp.repository.SeatRepository;
import group.aist.cinemaapp.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static group.aist.cinemaapp.enums.SeatStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;


    @Override
    public SeatResponse getSeatById(Long id) {
        Seat seat = getSeatIfExist(id);
        if(seat.getStatus() == DELETED.getId()){
            throw new ResponseStatusException(NOT_FOUND, "Seat with id " + id + " is DELETED");
        }
        return seatMapper.toSeatResponse(seat);
    }

    @Override
    public List<SeatResponse> getSeats() {
        return List.of();
    }

    @Override
    public void saveSeat(SeatRequest seatRequest) {
        Seat seat = seatMapper.toSeat(seatRequest);
        seat.setStatus(AVAILABLE.getId());
        setSectorIfExist(seat, seatRequest);
        seatRepository.save(seat);
    }


    @Override
    public void updateSeat(Long id, SeatRequest seatRequest) {
        Seat seat = getSeatIfExist(id);
        seat.setRow(seatRequest.getRow());
        seat.setSeat_number(seatRequest.getSeat_number());
        setSectorIfExist(seat, seatRequest);
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

    private void setSectorIfExist(Seat seat, SeatRequest seatRequest) {
        Long sectorId = seatRequest.getSector();
        if(sectorId != null){
            Sector sector = sectorService.fetchSectorIfExist(sectorId);
            seat.setSector(sector);
        }
    }
}
