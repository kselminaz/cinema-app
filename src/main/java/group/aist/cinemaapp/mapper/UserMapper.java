package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.UserRegisterRequest;
import group.aist.cinemaapp.dto.response.UserResponse;
import group.aist.cinemaapp.enums.CurrencyType;
import group.aist.cinemaapp.model.User;
import group.aist.cinemaapp.model.UserBalance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {CurrencyType.class, BigDecimal.class})
public interface UserMapper {

    @Mapping(target = "fullName", expression = "java(request.getLastName()+request.getFirstName())")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "dob", source = "dob")
    @Mapping(target = "mail", source = "email")
    @Mapping(target = "phone", source = "phone")
    User toUserEntity(UserRegisterRequest request);

    UserResponse toResponse(User entity);

    /*@Mapping(target = "user", source = "entity")
    @Mapping(target = "currency", qualifiedByName = "getCurrencyType")
    @Mapping(target = "amount", expression = "java(BigDecimal.valueOf(100))")
    UserBalance toUserBalanceEntity(User entity);*/

    @Named("getCurrencyType")
    default CurrencyType getCurrencyType(Integer id) {
        return CurrencyType.AZN;
    }


}
