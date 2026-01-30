package com.Natlav.QuizApp.services.Implement;

import com.Natlav.QuizApp.services.IUserService;
import com.Natlav.QuizApp.entities.User;
import com.Natlav.QuizApp.enums.RoleType;
import com.Natlav.QuizApp.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UsersRepository _userRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public List<User> getAllUsers() {
        return _userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return _userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return _userRepository.findByEmail(email);
    }

    @Override
    public User registerUser(User user) {
        //Check if email exists
        _userRepository.findByEmail(user.getEmail()).ifPresent(
                u-> {throw new IllegalArgumentException("Email Already Exists");}
        );

        if(user.getProvider() == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setProvider("local");
        }


        if(user.getRole() == null){
            user.setRole(RoleType.ROLE_PLAYER);
        }else if(user.getRole() != RoleType.ROLE_PLAYER && user.getRole() != RoleType.ROLE_GAMEMASTER){
            throw new IllegalArgumentException("Invalid User Role");
        }
        user.setActive(true);

        return  _userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {

        User existedUser = _userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("user does not exist"));

        existedUser.setUsername(updatedUser.getUsername());
        existedUser.setEmail(updatedUser.getEmail());
        existedUser.setRole(updatedUser.getRole());


        if(updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()){
            existedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        if(updatedUser.getRole() == null){
            existedUser.setRole(RoleType.ROLE_PLAYER);
        }else if(updatedUser.getRole() == RoleType.ROLE_PLAYER || updatedUser.getRole() == RoleType.ROLE_GAMEMASTER){
            existedUser.setRole(updatedUser.getRole());
        }else{
            throw new IllegalArgumentException("Invalid User Role");
        }

        return _userRepository.save(existedUser);
    }

    @Override
    public void deactivateUser(Long id) {
       User user = _userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("user does not exist"));
       user.setActive(false);
        _userRepository.save(user);
    }
}
