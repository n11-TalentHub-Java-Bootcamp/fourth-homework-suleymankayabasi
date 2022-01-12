package com.suleymankayabasi.fourthhomework.mapper;

import com.suleymankayabasi.fourthhomework.dto.UserDTO;
import com.suleymankayabasi.fourthhomework.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE  = Mappers.getMapper(UserMapper.class);

    User convertUserDTOtoUser(UserDTO userDTO);

    UserDTO convertUserToUserDto(User user);

    List<User> convertUserDTOListToUserList(List<UserDTO> userDTOList);

    List<UserDTO> convertUserListToUserDTOList(List<User> userList);
}
