package com.example.appcoding_bat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true,nullable = false)
    private String name;

    /**
     * Bitta dasturlash tilida bir nechta mavzular bo'ladi
     * Bitta mavzu bir nechta dasturlash tilida bo'ladi
     */
    @ManyToMany
    private Set<Language> languages;
}
