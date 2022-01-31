package com.example.appcoding_bat.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    @NotNull(message = "Topshiriq kiritilmadi")
    private String name;

    @NotNull(message = "Mavzu id si kiritilmadi")
    private Integer theme_id;

    @NotNull(message = "Dasturlash tili id si kiritilmadi")
    private Integer language_id;
}
