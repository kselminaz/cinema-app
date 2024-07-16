package group.aist.cinemaapp.service;

import group.aist.cinemaapp.dto.request.SeatRequest;
import group.aist.cinemaapp.dto.response.SeatResponse;
import group.aist.cinemaapp.model.Movie;
import group.aist.cinemaapp.model.Seat;

import java.util.List;

public interface SeatService {


    SeatResponse getSeatById(Long id);
    List<SeatResponse> getSeats();
    void saveSeat(SeatRequest seatRequest);
    void updateSeat(Long id, SeatRequest seatRequest);
    void updateSeatStatus(Long id, String status);
    void deleteSeatById(Long id);
    Seat getSeatIfExist(Long id);
    void bookSeatById(Long id);
}
