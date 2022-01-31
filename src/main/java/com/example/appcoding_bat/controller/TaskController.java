package com.example.appcoding_bat.controller;

import com.example.appcoding_bat.entity.Task;
import com.example.appcoding_bat.enums.ElementNotFound;
import com.example.appcoding_bat.models.Result;
import com.example.appcoding_bat.models.TaskDTO;
import com.example.appcoding_bat.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    ElementNotFound messageTheme = ElementNotFound.THEME;
    ElementNotFound messageLanguage = ElementNotFound.LANGUAGE;
    ElementNotFound messageTask = ElementNotFound.TASK;

    @GetMapping
    public HttpEntity<List<Task>> getAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public HttpEntity<Result> getTaskById(@PathVariable Integer id) {
        Result result = taskService.getTaskById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/byThemeId/{theme_id}")
    public HttpEntity<List<Result>> getAllTasksByThemeId(@PathVariable Integer theme_id) {
        List<Result> results = taskService.getAllTasksByThemeId(theme_id);
        return ResponseEntity.status(results.size() != 1 || results.get(0).isSuccess() ? HttpStatus.OK :
                HttpStatus.NOT_FOUND).body(results);
    }

    @PostMapping
    public HttpEntity<Result> addTask(@Valid @RequestBody TaskDTO taskDTO) {
        Result result = taskService.addTask(taskDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : result.getMessage().equals(messageTheme.getMessage()) ||
                result.getMessage().equals(messageLanguage.getMessage()) ? HttpStatus.NOT_FOUND : HttpStatus.CONFLICT).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<Result> deleteTaskById(@PathVariable Integer id) {
        Result result = taskService.deleteTaskById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<Result> editTaskById(@PathVariable Integer id, @Valid @RequestBody TaskDTO taskDTO) {
        Result result = taskService.editTaskById(id, taskDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : result.getMessage().equals(messageLanguage.getMessage()) ||
                result.getMessage().equals(messageTask.getMessage()) || result.getMessage().equals(messageTheme.getMessage()) ?
                HttpStatus.NOT_FOUND : HttpStatus.CONFLICT).body(result);
    }
}
