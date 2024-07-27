package group.aist.cinemaapp.controller;
import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.TicketUpdateRequest;
import group.aist.cinemaapp.dto.request.TicketCreateRequest;
import group.aist.cinemaapp.dto.response.TicketResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public TicketResponse getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public PageableResponse<TicketResponse> getTickets(PageCriteria pageCriteria) {
        return ticketService.getTickets(pageCriteria);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public void saveTicket(@RequestBody TicketCreateRequest request) {
        ticketService.saveTicket(request);
    }

    @PutMapping("/{id}")
    public void updateTicket(@PathVariable Long id, @RequestBody TicketUpdateRequest request) {
        ticketService.updateTicket(id, request);
    }
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void updateTicketStatus(@PathVariable Long id, @RequestParam String status) {
        ticketService.updateTicketWithStatus(id, status);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteTicketById(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }
}