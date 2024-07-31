package group.aist.cinemaapp.service.impl;

import com.google.zxing.WriterException;
import group.aist.cinemaapp.annotation.Log;
import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.MailDto;
import group.aist.cinemaapp.dto.request.UserTicketCreateRequest;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.dto.response.UserTicketResponse;
import group.aist.cinemaapp.enums.TicketStatus;
import group.aist.cinemaapp.enums.UserTicketStatus;
import group.aist.cinemaapp.mapper.UserTicketMapper;
import group.aist.cinemaapp.model.UserTicket;
import group.aist.cinemaapp.repository.UserTicketRepository;
import group.aist.cinemaapp.service.*;
import group.aist.cinemaapp.util.MailSenderUtil;
import group.aist.cinemaapp.util.PdfUtil;
import group.aist.cinemaapp.util.QrCodeUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

import static group.aist.cinemaapp.enums.UserTicketStatus.ACTIVE;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
@Log
public class UserTicketServiceImpl implements UserTicketService {

    private final UserTicketRepository userTicketRepository;
    private final UserBalanceService userBalanceService;
    private final UserService userService;
    private final TicketService ticketService;

    private final QrCodeUtil qrCodeUtil;
    private final PdfUtil pdfUtil;

    private final UserTicketMapper userTicketMapper;

    private final MailSenderUtil mailSenderUtil;
    private final CompanyInfoService companyInfoService;

    @Value("${server.port}")
    private Integer port;

    @Override
    @Transactional
    public UserTicketResponse getUserTicketById(Long id) {

        var entity = fetchUserTicketIfExist(id);

        return userTicketMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public PageableResponse<UserTicketResponse> getUserTickets(PageCriteria pageCriteria) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getSubject();
        var resultsPage = userTicketRepository.findAllByUserIdAndStatusIs(PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()), userId, ACTIVE.getId());
        return userTicketMapper.toPageableResponse(resultsPage);
    }

    @Override
    @Transactional
    public List<UserTicketResponse> saveUserTicket(UserTicketCreateRequest request) {

        String qrString;
        var userTickets = new ArrayList<UserTicketResponse>();


        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getSubject();

        var user = userService.getUserById(userId);


        for (var ticketId : request.getTicketId()) {

            var ticket = ticketService.fetchTicketIfExist(ticketId);
            if (ticket.getStatus() == TicketStatus.ACTIVE.getId()) {
                var currency = ticket.getCurrency();
                var price = ticket.getPrice();
                var userBalance = userBalanceService.getUserBalanceByUserAndCurrency(user.getId(), currency);
                var userBalanceAmount = userBalance.getAmount();
                if (userBalanceAmount.compareTo(price) >= 0) {

                    var userTicket = UserTicket.builder()
                            .user(user)
                            .ticket(ticket)
                            .ticketNumber(UUID.randomUUID().toString())
                            .status(UserTicketStatus.ACTIVE.getId())
                            .build();

                    userTicketRepository.save(userTicket);
                    userTickets.add(userTicketMapper.toResponse(userTicket));
                    userBalanceService.updateUserBalance(userBalance.getId(), userBalanceAmount.subtract(price));
                    ticketService.updateTicketWithStatus(ticketId, TicketStatus.SOLD.name());

                    byte[] qrcode;
                    try {
                        qrcode = qrCodeUtil.generateQRCodeBase64("localhost:" + port + "/v1/user-tickets/" + userTicket.getId(), 100, 100);
                    } catch (WriterException | IOException e) {
                        throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Qr Code generator is not working");
                    }

                    qrString = Base64.getEncoder().encodeToString(qrcode);

                   // var companyInfo = companyInfoService.getCompanyById(1L);

                    var companyInfo = companyInfoService.getCompanyData();

                    var pdfdto = userTicketMapper.toPDFResponse(userTicket, companyInfo, qrString);


                    try {
                        var pdf = pdfUtil.generatePdfFromHtml(pdfUtil.parseThymeleafTemplate(pdfdto));
                        mailSenderUtil.sendMail(new MailDto(user.getMail(), "Ticket Sale", "Your ticket pdf", user.getFullName() + " ticket number:" + userTicket.getTicketNumber()), pdf);
                    } catch (IOException | MessagingException e) {
                        throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Pdf sender is not working");
                    }


                } else throw new ResponseStatusException(BAD_REQUEST, String.format(
                        "User Balance isn't suitable for Ticket with id [%d] ", ticketId));
            } else throw new ResponseStatusException(BAD_REQUEST, String.format(
                    "Ticket  with id [%d] isn't suitable for Sale", ticketId));
        }
        return userTickets;
    }

    @Override
    @Transactional
    public void updateUserTicketWithStatus(Long id, String status) {

        var userTicket = fetchUserTicketIfExist(id);
        var userTicketStatus = Arrays.stream(UserTicketStatus.values()).filter(e -> e.name().equalsIgnoreCase(status)).findFirst().orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, String.format(
                "User Ticket Status with status [%s] was not found!", status
        )));
        if (userTicketStatus.equals(UserTicketStatus.REFUNDED)) {
            var ticket = userTicket.getTicket();
            ticketService.updateTicketWithStatus(ticket.getId(), TicketStatus.ACTIVE.name());
            var userbalance = userBalanceService.getUserBalanceByUserAndCurrency(userTicket.getUser().getId(), ticket.getCurrency());
            var updatedAmount = userbalance.getAmount().add(ticket.getPrice());
            userBalanceService.updateUserBalance(userbalance.getId(), updatedAmount);
        }
        userTicket.setStatus(userTicketStatus.getId());
        userTicketRepository.save(userTicket);
    }

    @Override
    public void deleteUserTicket(Long id) {

        var entity = fetchUserTicketIfExist(id);
        entity.setStatus(UserTicketStatus.DELETED.getId());
        userTicketRepository.save(entity);
    }

    @Override
    public UserTicket fetchUserTicketIfExist(Long id) {
        return userTicketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format(
                "User Ticket with id [%d] was not found!", id
        )));
    }


}
