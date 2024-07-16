package group.aist.cinemaapp.controller;

import group.aist.cinemaapp.dto.request.SeatRequest;
import group.aist.cinemaapp.dto.response.SeatResponse;
import group.aist.cinemaapp.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/seats")
public class SeatController {

    private final SeatService seatService;

    @GetMapping("{id}")
    public SeatResponse getSeatById(@PathVariable Long id) {
        return seatService.getSeatById(id);
    }
    @GetMapping
    public List<SeatResponse> getAllSeats() {
        return seatService.getSeats();
    }
    @PostMapping
    public void saveSeat(@RequestBody SeatRequest seatRequest) {
        seatService.saveSeat(seatRequest);
    }
    @PutMapping("{id}")
    public void updateSeat(@PathVariable Long id, @RequestBody SeatRequest seatRequest) {
        seatService.updateSeat(id, seatRequest);
    }
    @PatchMapping("{id}")
    public void updateSeatStatus(@PathVariable Long id, @RequestParam String status) {
        seatService.updateSeatStatus(id,status);
    }
    @DeleteMapping("{id}")
    public void deleteSeatById(@PathVariable Long id) {
        seatService.deleteSeatById(id);
    }
    @GetMapping("/book/{id}")
    public void bookSeatById(@PathVariable Long id) {
        seatService.bookSeatById(id);
    }
}
