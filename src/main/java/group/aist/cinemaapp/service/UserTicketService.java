package group.aist.cinemaapp.service;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.UserTicketCreateRequest;
import group.aist.cinemaapp.dto.request.UserTicketUpdateRequest;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.dto.response.UserTicketResponse;
import group.aist.cinemaapp.model.UserTicket;

public interface UserTicketService {

    UserTicketResponse getUserTicketById(Long id);

    PageableResponse<UserTicketResponse> getUserTickets(PageCriteria pageCriteria);

    String saveUserTicket(UserTicketCreateRequest request);

    void updateUserTicket(Long id, UserTicketUpdateRequest request);

    void updateUserTicketWithStatus(Long id, String status);

    void deleteUserTicket(Long id);

    UserTicket fetchUserTicketIfExist(Long id);
}
