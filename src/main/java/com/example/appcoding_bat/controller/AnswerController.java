package com.example.appcoding_bat.controller;

import com.example.appcoding_bat.entity.Answer;
import com.example.appcoding_bat.enums.ElementNotFound;
import com.example.appcoding_bat.models.AnswerDTO;
import com.example.appcoding_bat.models.Result;
import com.example.appcoding_bat.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    ElementNotFound messageTask = ElementNotFound.TASK;
    ElementNotFound messageAnswer = ElementNotFound.ANSWER;

    @GetMapping
    public HttpEntity<List<Answer>> getAllAnswers() {
        return ResponseEntity.status(HttpStatus.OK).body(answerService.getAllAnswers());
    }

    @GetMapping("/{id}")
    public HttpEntity<Result> getAnswerById(@PathVariable Integer id) {
        Result result = answerService.getAnswerById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/byTaskId/{task_id}")
    public HttpEntity<List<Result>> getAllAnswersByTaskId(@PathVariable Integer task_id) {
        List<Result> results = answerService.getAllAnswersByTaskId(task_id);
        return ResponseEntity.status(results.size() != 1 || results.get(0).isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(results);
    }

    @PostMapping
    public HttpEntity<Result> addAnswer(@RequestBody AnswerDTO answerDTO) {
        Result result = answerService.addAnswer(answerDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<Result> editAnswerById(@PathVariable Integer id, @Valid @RequestBody AnswerDTO answerDTO) {
        Result result = answerService.editAnswerById(id, answerDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<Result> deleteAnswerById(@PathVariable Integer id) {
        Result result = answerService.deleteAnswerById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }
}
