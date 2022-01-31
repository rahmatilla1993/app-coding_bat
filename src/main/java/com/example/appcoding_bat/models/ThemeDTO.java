package com.example.appcoding_bat.models;

import com.example.appcoding_bat.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDTO {

    @NotNull(message = "Mavzu nomi kiritilmadi")
    private String name;

    @NotNull(message = "Birorta dasturlash tili kiritilmadi")
    private Set<Integer> languages;
}
