package com.example.appcoding_bat.repository;

import com.example.appcoding_bat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByUserName(String userName);

    boolean existsByIdIsNotAndUserName(Integer id, String userName);

    boolean existsByEmail(String email);

    boolean existsByIdIsNotAndEmail(Integer id, String email);

    boolean existsByPassword(String password);

    boolean existsByIdIsNotAndPassword(Integer id, String password);

    boolean existsByAnswer_Id(Integer answer_id);

    boolean existsByIdIsNotAndAnswer_Id(Integer id, Integer answer_id);
}
