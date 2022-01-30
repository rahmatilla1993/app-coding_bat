package com.example.appcoding_bat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @Column(nullable = false,unique = true)
    private String userName;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private String password;

    /**
     * Bitta user bir nechta dasturlsh tilidan masala yechadi
     * Bitta dasturlash tilida bir nechta user masala yechadi
     */
    @ManyToMany
    private List<Task> tasks;

    /**
     * Bitta jovob bitta userga tegishli
     */
    @OneToOne
    private Answer answer;
}
