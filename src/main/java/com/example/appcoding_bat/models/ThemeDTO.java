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

    @NotNull
    private String name;

    private Set<Integer> languages;
}
