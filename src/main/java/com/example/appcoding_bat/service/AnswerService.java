package com.example.appcoding_bat.service;

import com.example.appcoding_bat.entity.Answer;
import com.example.appcoding_bat.entity.Task;
import com.example.appcoding_bat.enums.ElementNotFound;
import com.example.appcoding_bat.models.AnswerDTO;
import com.example.appcoding_bat.models.Result;
import com.example.appcoding_bat.repository.AnswerRepository;
import com.example.appcoding_bat.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    ElementNotFound messageAnswer = ElementNotFound.ANSWER;
    ElementNotFound messageTask = ElementNotFound.TASK;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    TaskRepository taskRepository;

    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    public Result getAnswerById(Integer id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        return optionalAnswer.map(answer -> new Result(true, answer)).orElseGet(() -> new Result(messageAnswer.getMessage(), false));
    }

    public List<Result> getAllAnswersByTaskId(Integer task_id) {
        Optional<Task> optionalTask = taskRepository.findById(task_id);
        List<Result> results = new ArrayList<>();
        if (optionalTask.isPresent()) {
            List<Answer> answers = answerRepository.getAllByTask_Id(task_id);
            for (Answer answer : answers) {
                Result result = new Result(true, answer);
                results.add(result);
            }
            return results;
        }
        Result result = new Result(messageTask.getMessage(), false);
        results.add(result);
        return results;
    }

    private Result hasTask(Integer task_id) {
        Optional<Task> optionalTask = taskRepository.findById(task_id);
        return optionalTask.map(task -> new Result(true, task)).orElseGet(() -> new Result(messageTask.getMessage(), false));
    }

    public Result addAnswer(AnswerDTO answerDTO) {
        Answer answer = new Answer();
        Result result = hasTask(answerDTO.getTask_id());
        if (!result.isSuccess()) {
            return result;
        }
        answer.setName(answerDTO.getName());
        Task task = (Task) result.getObject();
        answer.setTask(task);
        answerRepository.save(answer);
        return new Result("Javob qo'shildi", true);
    }

    public Result editAnswerById(Integer id, AnswerDTO answerDTO) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isPresent()) {
            Answer editAnswer = optionalAnswer.get();
            Result result = hasTask(answerDTO.getTask_id());
            if (result.isSuccess()) {
                Task task = (Task) result.getObject();
                editAnswer.setName(answerDTO.getName());
                editAnswer.setTask(task);
                answerRepository.save(editAnswer);
                return new Result("Jovob tahrirlandi", true);
            }
            return new Result(messageTask.getMessage(), false);
        }
        return new Result(messageAnswer.getMessage(), false);
    }

    public Result deleteAnswerById(Integer id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isPresent()) {
            answerRepository.delete(optionalAnswer.get());
            return new Result("Jovob o'chirildi", true);
        }
        return new Result(messageAnswer.getMessage(), false);
    }
}
