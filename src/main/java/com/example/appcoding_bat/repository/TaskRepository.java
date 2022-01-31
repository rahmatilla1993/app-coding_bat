package com.example.appcoding_bat.repository;

import com.example.appcoding_bat.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

    List<Task> findAllByTheme_Id(Integer theme_id);

    boolean existsByNameAndLanguage_Id(String name, Integer language_id);

    boolean existsByNameAndIdIsNotAndLanguage_Id(String name, Integer id, Integer language_id);
}
