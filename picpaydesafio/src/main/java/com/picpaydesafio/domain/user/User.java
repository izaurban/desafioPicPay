package com.picpaydesafio.domain.user;

import com.picpaydesafio.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name="users")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private String password;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDto userDto){
        this.firstName = userDto.firstName();
        this.lastName = userDto.lastName();
        this.balance = userDto.balance();
        this.userType = userDto.userType();
        this.password = userDto.password();
        this.document = userDto.document();
        this.email = userDto.email();
    }
}
