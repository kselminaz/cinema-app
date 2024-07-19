package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.response.UserResponse;
import group.aist.cinemaapp.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper {

    UserResponse toResponse(User entity);

}
