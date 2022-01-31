package com.example.appcoding_bat.models;

import com.example.appcoding_bat.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    @NotNull(message = "Javob kiritilmadi")
    private String name;

    @NotNull(message = "Masala id si kiritilmadi")
    private Integer task_id;
}
