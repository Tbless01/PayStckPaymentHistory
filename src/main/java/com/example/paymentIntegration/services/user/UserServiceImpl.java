package com.example.paymentIntegration.services.user;

import com.example.paymentIntegration.data.models.User;
import com.example.paymentIntegration.data.repository.UserRepository;
import com.example.paymentIntegration.dtos.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


//    private final UserService userService;
//    private final JwtService jwtService;
//    public final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserByEmailAddress(String emailAddress) {
        return userRepository.findUserByEmailAddressIgnoreCase(emailAddress).orElse(null);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserServiceImpl::buildUserResponse)
                .toList();
    }

    private static UserResponse buildUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .emailAddress(user.getEmailAddress())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .userRoles(user.getUserRoles())
                .build();
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

}