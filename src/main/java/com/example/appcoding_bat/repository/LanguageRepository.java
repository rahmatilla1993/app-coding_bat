package com.example.appcoding_bat.repository;

import com.example.appcoding_bat.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language,Integer> {

    boolean existsByName(String name);

    boolean existsByIdIsNotAndName(Integer id, String name);
}
