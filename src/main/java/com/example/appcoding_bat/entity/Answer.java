package com.example.appcoding_bat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String name;

    /**
     * Bitta masalaga bir necha user jovob yozadi
     * Biita jovob bitta masalaga bo'ladi
     */
    @ManyToOne
    private Task task;
}
