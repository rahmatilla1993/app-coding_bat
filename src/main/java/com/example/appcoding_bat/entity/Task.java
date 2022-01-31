package com.example.appcoding_bat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    /**
     * Bitta mavzudan bir nechta topshiriqlar bo'ladi
     * Bitta topshiriq bitta mavzuga tegishli
     */
    @ManyToOne
    private Theme theme;

    /**
     * Bitta dasturlash tilida ko'p topshiriqlar bo'ladi
     */
    @ManyToOne
    private Language language;
}
