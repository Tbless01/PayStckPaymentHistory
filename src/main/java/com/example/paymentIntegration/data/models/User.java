package com.example.paymentIntegration.data.models;

import com.example.paymentIntegration.data.models.Enum.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Roles> userRoles;
    @OneToOne
    private Wallet wallet;
}
