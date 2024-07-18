package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.TicketCreateRequest;
import group.aist.cinemaapp.dto.request.TicketUpdateRequest;
import group.aist.cinemaapp.dto.response.TicketResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.TicketStatus;
import group.aist.cinemaapp.mapper.TicketMapper;
import group.aist.cinemaapp.model.Ticket;
import group.aist.cinemaapp.repository.TicketRepository;
import group.aist.cinemaapp.service.MovieSessionService;
import group.aist.cinemaapp.service.SeatService;
import group.aist.cinemaapp.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static group.aist.cinemaapp.enums.TicketStatus.VISIBLE;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final SeatService seatService;
    private final MovieSessionService movieSessionService;

    @Override
    @Transactional
    public TicketResponse getTicketById(Long id) {
        var entity = fetchTicketIfExist(id);
        return ticketMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public PageableResponse<TicketResponse> getTickets(PageCriteria pageCriteria) {
        var resultsPage = ticketRepository.findAllByStatusIs(PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()), VISIBLE.getId());
        return ticketMapper.toPageableResponse(resultsPage);
    }

    @Override
    @Transactional
    public void saveTicket(TicketCreateRequest request) {
        var entity = ticketMapper.toEntity(request);
        entity.setStatus(TicketStatus.VISIBLE.getId());
        addRelations(entity, request.getSeatId(),request.getSessionId());
        ticketRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateTicket(Long id, TicketUpdateRequest request) {
        var entity = fetchTicketIfExist(id);
        ofNullable(request.getPrice()).ifPresent(entity::setPrice);
        ofNullable(request.getStatus()).ifPresent(status -> entity
                .setStatus(TicketStatus.valueOf(request.getStatus()).getId()));
        addRelations(entity, request.getSeatId(), request.getSessionId());
        ticketRepository.save(entity);
    }

    @Override
    public void updateTicketWithStatus(Long id, String status) {
        var entity = fetchTicketIfExist(id);
        var ticketStatus = Arrays.stream(TicketStatus.values()).filter(ts -> ts.name().equalsIgnoreCase(status)).findFirst().orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, String.format(
                "Ticket Status with status [%s] was not found!", status
        )));
        entity.setStatus(ticketStatus.getId());
        ticketRepository.save(entity);
    }

    @Override
    public void deleteTicket(Long id) {
        var entity = fetchTicketIfExist(id);
        entity.setStatus(TicketStatus.DELETED.getId());
        ticketRepository.save(entity);
    }

    @Override
    public Ticket fetchTicketIfExist(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format(
                "Ticket with id [%d] was not found!", id
        )));
    }

    private void addRelations(Ticket entity, Long seatId,Long sessionId) {
        if (seatId != null) {
            var seat = seatService.getSeatIfExist(seatId);
            entity.setSeat(seat);
        }
        if (sessionId != null) {
            var session = movieSessionService.fetchMovieSessionIfExist(sessionId);
            entity.setSession(session);
        }
    }
}