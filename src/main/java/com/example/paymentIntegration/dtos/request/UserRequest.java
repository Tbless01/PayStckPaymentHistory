package com.example.paymentIntegration.dtos.request;

import com.example.paymentIntegration.data.models.Enum.Roles;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
        private Long id;
        private String emailAddress;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private Set<Roles> userRoles;
}
