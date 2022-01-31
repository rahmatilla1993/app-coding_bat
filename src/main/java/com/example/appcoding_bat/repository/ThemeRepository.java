package com.example.appcoding_bat.repository;

import com.example.appcoding_bat.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Integer> {

    @Query(value = "SELECT t.* FROM theme_languages JOIN language l ON l.id = theme_languages.languages_id\n" +
            "    JOIN theme t ON t.id = theme_languages.theme_id WHERE l.id = ?1", nativeQuery = true)
    List<Theme> getAllByLanguageId(Integer id);

    boolean existsByName(String name);

    boolean existsByIdIsNotAndName(Integer id, String name);
}
