package group.aist.cinemaapp.service.impl;

import com.google.zxing.WriterException;
import group.aist.cinemaapp.annotation.Log;
import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.MailDto;
import group.aist.cinemaapp.dto.request.UserTicketCreateRequest;
import group.aist.cinemaapp.dto.request.UserTicketUpdateRequest;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.dto.response.UserTicketResponse;
import group.aist.cinemaapp.enums.TicketStatus;
import group.aist.cinemaapp.enums.UserTicketStatus;
import group.aist.cinemaapp.exception.QRCodeGenerateException;
import group.aist.cinemaapp.mapper.UserTicketMapper;
import group.aist.cinemaapp.model.UserTicket;
import group.aist.cinemaapp.repository.UserBalanceRepository;
import group.aist.cinemaapp.repository.UserTicketRepository;
import group.aist.cinemaapp.service.CompanyInfoService;
import group.aist.cinemaapp.service.TicketService;
import group.aist.cinemaapp.service.UserService;
import group.aist.cinemaapp.service.UserTicketService;
import group.aist.cinemaapp.util.MailSenderUtil;
import group.aist.cinemaapp.util.PdfUtil;
import group.aist.cinemaapp.util.QrCodeUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import static group.aist.cinemaapp.enums.UserTicketStatus.ACTIVE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Log
public class UserTicketServiceImpl implements UserTicketService {


    private final UserTicketRepository userTicketRepository;
    private final UserBalanceRepository userBalanceRepository;
    private final UserService userService;
    private final TicketService ticketService;

    private final QrCodeUtil qrCodeUtil;
    private final PdfUtil pdfUtil;

    private final UserTicketMapper userTicketMapper;

    private final MailSenderUtil mailSenderUtil;
    private final CompanyInfoService companyInfoService;

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
        String userId=jwt.getSubject();
        var resultsPage = userTicketRepository.findAllByUserIdAndStatusIs(PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()), userId, ACTIVE.getId());
        return userTicketMapper.toPageableResponse(resultsPage);
    }

    @Override
    @Transactional
    public String saveUserTicket(UserTicketCreateRequest request) {

        String qrString = null;

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId=jwt.getSubject();

        var user = userService.getUserById(userId);


        for (var ticketId : request.getTicketId()) {

            var ticket = ticketService.fetchTicketIfExist(ticketId);
            if (ticket.getStatus() == TicketStatus.ACTIVE.getId()) {
                var currency = ticket.getCurrency();
                var price = ticket.getPrice();
                var userBalance = userBalanceRepository.findByUserIdAndCurrency(user.getId(), currency);
                var userBalanceAmount = userBalance.getAmount();
                if (userBalanceAmount.compareTo(price) >= 0) {

                    var userTicket = UserTicket.builder()
                            .user(user)
                            .ticket(ticket)
                            .ticketNumber(UUID.randomUUID().toString()).status(ACTIVE.getId())
                            .build();
                    userTicketRepository.save(userTicket);
                    userBalance.setAmount(userBalanceAmount.subtract(price));
                    userBalanceRepository.save(userBalance);
                    ticketService.updateTicketWithStatus(ticketId, TicketStatus.SOLD.name());



                    byte[] qrcode = null;
                    try {
                        qrcode = qrCodeUtil.generateQRCodeBase64("localhost:8086/v1/user-tickets/" + userTicket.getId(), 300, 300);
                    } catch (WriterException | IOException e) {
                        throw new QRCodeGenerateException("Exception in generating QR Code");
                    }

                    qrString = Base64.getEncoder().encodeToString(qrcode);

                    var companyInfo=companyInfoService.getCompanyById(1L);

                    var pdfdto=userTicketMapper.toPDFResponse(userTicket,companyInfo,qrString);

                    System.out.println(pdfdto);

                    try {
                        var pdf = pdfUtil.generatePdfFromHtml(pdfUtil.parseThymeleafTemplate(pdfdto));
                        mailSenderUtil.sendMail(new MailDto(user.getMail(), "Ticket Sale", "Your ticket pdf", user.getFullName() + " ticket number:" + userTicket.getTicketNumber()), pdf);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }


                } else throw new ResponseStatusException(BAD_REQUEST, String.format(
                        "User Balance isn't suitable for Ticket with id [%d] ", ticketId));
            } else throw new ResponseStatusException(BAD_REQUEST, String.format(
                    "Ticket  with id [%d] isn't suitable for Sale", ticketId));
        }
        return qrString;
    }

    @Override
    public void updateUserTicket(Long id, UserTicketUpdateRequest request) {

    }

    @Override
    public void updateUserTicketWithStatus(Long id, String status) {

        var entity = fetchUserTicketIfExist(id);
        var sessionStatus = Arrays.stream(UserTicketStatus.values()).filter(e -> e.name().equalsIgnoreCase(status)).findFirst().orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, String.format(
                "Movie Session Status with status [%s] was not found!", status
        )));
        entity.setStatus(sessionStatus.getId());
        userTicketRepository.save(entity);

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
