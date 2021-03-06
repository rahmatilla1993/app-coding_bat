package com.example.appcoding_bat.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDTO {

    @NotNull(message = "Dasturlash tili kiritilmadi")
    private String name;
}
