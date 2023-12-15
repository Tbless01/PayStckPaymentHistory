package com.example.paymentIntegration.services.user;

import com.example.paymentIntegration.data.models.User;
import com.example.paymentIntegration.dtos.response.UserResponse;

import java.util.List;

public interface UserService {

    User save(User user);
    User findUserByEmailAddress(String emailAddress);
    List<UserResponse> getAllUsers();
    void deleteAll();
}
