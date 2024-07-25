package group.aist.cinemaapp.service;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.UserTicketCreateRequest;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.dto.response.UserTicketResponse;
import group.aist.cinemaapp.model.UserTicket;

import java.util.List;

public interface UserTicketService {

    UserTicketResponse getUserTicketById(Long id);

    PageableResponse<UserTicketResponse> getUserTickets(PageCriteria pageCriteria);

    List<UserTicketResponse> saveUserTicket(UserTicketCreateRequest request);

    void updateUserTicketWithStatus(Long id, String status);

    void deleteUserTicket(Long id);

    UserTicket fetchUserTicketIfExist(Long id);
}
