package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.UserTicketPdfDto;
import group.aist.cinemaapp.dto.response.CompanyInfoResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.dto.response.UserTicketResponse;
import group.aist.cinemaapp.enums.UserTicketStatus;
import group.aist.cinemaapp.model.UserTicket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserTicketStatus.class, UserMapper.class, TicketMapper.class})


public interface UserTicketMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "getById")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "ticket", source = "ticket")
    UserTicketResponse toResponse(UserTicket entity);


    @Mapping(target = " movieName", expression = "java(userTicket.getTicket().getSession().getMovie().getName())")
    @Mapping(target = " movieDateTime", expression = "java(userTicket.getTicket().getSession().getDatetime())")
    @Mapping(target = " price", expression = "java(userTicket.getTicket().getPrice())")
    @Mapping(target = " currency", expression = "java(userTicket.getTicket().getCurrency().name())")
    @Mapping(target = " qrCode", source = "qrcode")
    @Mapping(target = " ticketNumber", expression = "java(userTicket.getTicketNumber())")
    @Mapping(target = " hall", expression = "java(userTicket.getTicket().getSession().getHall().getName())")
    @Mapping(target = " sectorName", expression = "java(userTicket.getTicket().getSeat().getSector().getName())")
    @Mapping(target = " row", expression = "java(userTicket.getTicket().getSeat().getRow())")
    @Mapping(target = " seatNumber", expression = "java(userTicket.getTicket().getSeat().getSeatNumber())")
    @Mapping(target = " companyName", expression = "java(companyInfo.getName())")
    @Mapping(target = " companyImage", expression = "java(companyInfo.getLogo())")
    @Mapping(target = "customersInfo", expression = "java(companyInfo.getCostumersInformationText())")
    UserTicketPdfDto toPDFResponse(UserTicket userTicket, CompanyInfoResponse companyInfo, String qrcode);

    @Named("getById")
    default UserTicketStatus getById(Integer id) {
        return UserTicketStatus.getStatusById(id);
    }

    @Mapping(target = "data", source = "content", qualifiedByName = "getDataList")
    @Mapping(target = "hasNextPage", expression = "java(page.hasNext())")
    @Mapping(target = "lastPageNumber", source = "totalPages")
    @Mapping(target = "totalElements", source = "totalElements")
    PageableResponse<UserTicketResponse> toPageableResponse(Page<UserTicket> page);

    @Named("getDataList")
    default List<UserTicketResponse> getMovieSessionList(List<UserTicket> data) {
        return data.stream().map(this::toResponse).toList();
    }


}
