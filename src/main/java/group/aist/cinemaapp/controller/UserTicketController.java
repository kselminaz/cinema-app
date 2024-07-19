package group.aist.cinemaapp.controller;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.UserTicketCreateRequest;
import group.aist.cinemaapp.dto.request.UserTicketUpdateRequest;
import group.aist.cinemaapp.dto.response.UserTicketResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.service.UserTicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user-tickets")
public class UserTicketController {

    private final UserTicketService userTicketService;

    @GetMapping("/{id}")
    public UserTicketResponse getUserTicketById(@PathVariable Long id) {

        return userTicketService.getUserTicketById(id);
    }

    @GetMapping
    public PageableResponse<UserTicketResponse> getUserTickets(@RequestParam Long userId,PageCriteria pageCriteria) {
        return userTicketService.getUserTickets(userId,pageCriteria);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public String saveUserTicket(@Valid @RequestBody UserTicketCreateRequest request) {

       return  userTicketService.saveUserTicket(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUserTicket(@PathVariable Long id) {
        userTicketService.deleteUserTicket(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateUserTicket(@PathVariable Long id, @Valid @RequestBody UserTicketUpdateRequest request) {

        userTicketService.updateUserTicket(id, request);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateUserTicketWithStatus(@PathVariable Long id, @RequestParam String status) {

        userTicketService.updateUserTicketWithStatus(id, status);
    }


}
