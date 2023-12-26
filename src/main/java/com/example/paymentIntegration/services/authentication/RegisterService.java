//package com.tbless.inventoryManagementApp.services.authentication;
//
//import com.tbless.inventoryManagementApp.data.models.Roles;
//import com.tbless.inventoryManagementApp.data.models.User;
//import com.tbless.inventoryManagementApp.dtos.request.RegistrationRequest;
//import com.tbless.inventoryManagementApp.exceptions.UserAlreadyExistsException;
//import com.tbless.inventoryManagementApp.exceptions.UserRegistrationException;
//import com.tbless.inventoryManagementApp.security.JwtService;
//import com.tbless.inventoryManagementApp.services.user.UserService;
//import com.tbless.inventoryManagementApp.utils.ApiResponse;
//import com.tbless.inventoryManagementApp.utils.GenerateApiResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import static com.tbless.inventoryManagementApp.utils.ExceptionUtils.USER_ALREADY_EXISTS;
//
//@Service
//@RequiredArgsConstructor
//
//public class RegisterService {
//
//    private final UserService userService;
//    private final JwtService jwtService;
//    public final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//        public ApiResponse register(RegistrationRequest registrationRequest) throws UserRegistrationException {
//        boolean isRegisteredUser = userService.findUserByEmailAddress(registrationRequest.getEmailAddress()) != null;
//        if (isRegisteredUser) {throw new UserRegistrationException("User already exists");}
//        else {
//            User savedUser = buildUser(registrationRequest);
//            System.out.println(savedUser.toString());
//            UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmailAddress());
//            String jwt = jwtService.generateToken(userDetails);
//            return GenerateApiResponse.createdResponse("Bearer " + jwt);
//        }
//    }
//
//    private User buildUser(RegistrationRequest registrationRequest) {
//        Set<Roles> userRoles = new HashSet<>();
//        userRoles.add(Roles.USER);
//        User user = User.builder()
//                .emailAddress(registrationRequest.getEmailAddress())
//                .firstName(registrationRequest.getFirstName())
//                .lastName(registrationRequest.getLastName())
//                .phoneNumber(registrationRequest.getPhoneNumber())
//                .password(passwordEncoder.encode(registrationRequest.getPassword()))
//                .genderType(registrationRequest.getGenderType())
//                .userRoles(userRoles)
//                .build();
//        return userService.save(user);
//    }
//
//    private User checkIfUserAlreadyExist(RegistrationRequest userRegistrationRequest) throws UserAlreadyExistsException {
//        if (userExist(userRegistrationRequest.getEmailAddress()))
//            throw new UserAlreadyExistsException(String.format(USER_ALREADY_EXISTS, userRegistrationRequest.getEmailAddress()));
//        User user = new User();
//        return user;
//    }
//
//    private boolean userExist(String emailAddress) {
//        User foundUser = userService.findUserByEmailAddress(emailAddress);
//        return foundUser != null;
//    }
//}


package com.example.paymentIntegration.services.authentication;

import com.example.paymentIntegration.data.models.Enum.Roles;
import com.example.paymentIntegration.data.models.Token;
import com.example.paymentIntegration.data.models.User;
import com.example.paymentIntegration.data.models.Wallet;
import com.example.paymentIntegration.dtos.request.RegistrationRequest;
import com.example.paymentIntegration.exceptions.BadNetworkException;
import com.example.paymentIntegration.exceptions.UserRegistrationException;
import com.example.paymentIntegration.security.JwtService;
import com.example.paymentIntegration.services.token.TokenService;
import com.example.paymentIntegration.services.user.UserService;
import com.example.paymentIntegration.services.wallet.WalletService;
import com.example.paymentIntegration.utils.ApiResponse;
import com.example.paymentIntegration.utils.GenerateApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final WalletService walletService;


    public ApiResponse register(RegistrationRequest registrationRequest) throws UserRegistrationException, BadNetworkException {
        boolean isRegisteredUser = userService.findUserByEmailAddress(registrationRequest.getEmailAddress()) != null;
        if (isRegisteredUser) {
            throw new UserRegistrationException("User already exists");
        } else {
            User user = buildUser(registrationRequest);
            userService.save(user);

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmailAddress());
            String jwt = jwtService.generateToken(userDetails);
            Token token = Token.builder()
                    .userEmailAddress(user.getEmailAddress())
                    .jwt(jwt)
                    .build();
            tokenService.saveToken(token);
            return GenerateApiResponse.createdResponse("Bearer " + jwt);
        }
    }

    private String generateUniqueWallet(String emailAddress) {
        Optional<Wallet> wallet = walletService.findByEmailAddress(emailAddress);
        UUID uuid = UUID.randomUUID();
        String uniqueWallet = uuid.toString().replace("-", "");
        if (wallet.isPresent()) generateUniqueWallet(emailAddress);
        return uniqueWallet;
    }

    private User buildUser(RegistrationRequest registrationRequest) {
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.USER);
        Wallet wallet = new Wallet();
        wallet.setEmailAddress(registrationRequest.getEmailAddress());
        wallet.setWalletID(generateUniqueWallet(registrationRequest.getEmailAddress()));
        wallet.setBalance(BigDecimal.valueOf(0.0));
        walletService.save(wallet);
        return User.builder()
                .emailAddress(registrationRequest.getEmailAddress())
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .userRoles(userRoles)
                .wallet(wallet)
                .build();
    }
}
