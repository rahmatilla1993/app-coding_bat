package com.example.appcoding_bat.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull(message = "UserName kiritilmadi")
    private String userName;

    @NotNull(message = "Email kiritilmadi")
    private String email;

    @NotNull(message = "Parol kiritilmadi")
    private String password;

    @NotNull(message = "Masala id si kiritilmadi")
    private Set<Integer> tasks_ids;

    @NotNull(message = "Javob id si kiritilmadi")
    private Integer answer_id;
}
