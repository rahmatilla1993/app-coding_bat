package com.example.appcoding_bat.repository;

import com.example.appcoding_bat.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Integer> {

    List<Answer> getAllByTask_Id(Integer task_id);
}
