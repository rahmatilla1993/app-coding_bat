package com.example.appcoding_bat.repository;

import com.example.appcoding_bat.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeRepository extends JpaRepository<Theme,Integer> {

    @Query("SELECT theme.* FROM theme_languages JOIN language ON theme_languages.languages_id = language.id " +
            "JOIN theme ON theme.id = theme_languages.theme_id WHERE language.id = ?1")
    List<Theme> getAllByLanguageId(Integer id);

    boolean existsByName(String name);

    boolean existsByIdIsNotAndName(Integer id, String name);
}
