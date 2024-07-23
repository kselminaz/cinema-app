package group.aist.cinemaapp.controller;

import group.aist.cinemaapp.dto.request.SeatCreateRequest;
import group.aist.cinemaapp.dto.request.SeatUpdateRequest;
import group.aist.cinemaapp.dto.response.SeatResponse;
import group.aist.cinemaapp.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public void saveSeat(@RequestBody SeatCreateRequest seatCreateRequest) {
        seatService.saveSeat(seatCreateRequest);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{id}")
    public void updateSeat(@PathVariable Long id, @RequestBody SeatUpdateRequest seatUpdateRequest) {
        seatService.updateSeat(id, seatUpdateRequest);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("{id}")
    public void updateSeatStatus(@PathVariable Long id, @RequestParam String status) {
        seatService.updateSeatStatus(id,status);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public void deleteSeatById(@PathVariable Long id) {
        seatService.deleteSeatById(id);
    }
    @GetMapping("/book/{id}")
    public void bookSeatById(@PathVariable Long id) {
        seatService.bookSeatById(id);
    }
}
