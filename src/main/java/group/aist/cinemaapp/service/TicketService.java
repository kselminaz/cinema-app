package group.aist.cinemaapp.service;
import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.TicketUpdateRequest;
import group.aist.cinemaapp.dto.response.TicketResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.model.Ticket;

public interface TicketService {

    TicketResponse getTicketById(Long id);

    PageableResponse<TicketResponse> getTickets(PageCriteria pageCriteria);

    void saveTicket(TicketCreateRequest ticketRequest);

    void updateTicket(Long id, TicketUpdateRequest ticketRequest);

    void updateTicketWithStatus(Long id, String status);

    void deleteTicket(Long id);

    Ticket fetchTicketIfExist(Long id);
}