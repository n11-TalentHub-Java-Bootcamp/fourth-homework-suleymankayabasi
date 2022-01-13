package com.suleymankayabasi.fourthhomework.service;

import com.suleymankayabasi.fourthhomework.dto.UserDTO;
import com.suleymankayabasi.fourthhomework.exception.UserNotFoundException;
import com.suleymankayabasi.fourthhomework.mapper.UserMapper;
import com.suleymankayabasi.fourthhomework.model.User;
import com.suleymankayabasi.fourthhomework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.convertUserDTOtoUser(userDTO);
        user = userRepository.save(user);
        UserDTO userDTOResult = UserMapper.INSTANCE.convertUserToUserDto(user);
        return userDTOResult;
    }

    @Override
    public void deleteByUserId(Long id){

        User user = userRepository.findByUserId(id);
        if(user == null) throw new UserNotFoundException("User not found");
        userRepository.delete(user);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> userList = userRepository.findAll();
        if(userList.isEmpty()) throw  new UserNotFoundException("User List is empty");
        List<UserDTO> userDTOList = UserMapper.INSTANCE.convertUserListToUserDTOList(userList);
        return userDTOList;
    }
}
