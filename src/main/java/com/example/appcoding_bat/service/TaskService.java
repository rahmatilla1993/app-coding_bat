package com.example.appcoding_bat.service;

import com.example.appcoding_bat.entity.Language;
import com.example.appcoding_bat.entity.Task;
import com.example.appcoding_bat.entity.Theme;
import com.example.appcoding_bat.enums.ElementNotFound;
import com.example.appcoding_bat.models.Result;
import com.example.appcoding_bat.models.TaskDTO;
import com.example.appcoding_bat.repository.LanguageRepository;
import com.example.appcoding_bat.repository.TaskRepository;
import com.example.appcoding_bat.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    LanguageRepository languageRepository;

    ElementNotFound messageTask = ElementNotFound.TASK;
    ElementNotFound messageTheme = ElementNotFound.THEME;
    ElementNotFound messageLanguage = ElementNotFound.LANGUAGE;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Result getTaskById(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(task -> new Result(true, task)).orElseGet(() -> new Result(messageTask.getMessage(), false));
    }

    public List<Result> getAllTasksByThemeId(Integer theme_id) {
        List<Result> results = new ArrayList<>();
        Optional<Theme> optionalTheme = themeRepository.findById(theme_id);
        if (optionalTheme.isPresent()) {
            List<Task> tasks = taskRepository.findAllByTheme_Id(theme_id);
            for (Task task : tasks) {
                Result result = new Result(true, task);
                results.add(result);
            }
            return results;
        }
        Result result = new Result(messageTheme.getMessage(), false);
        results.add(result);
        return results;
    }

    private Result addingTask(TaskDTO taskDTO, boolean create, boolean edit, Integer id) {
        Task task = new Task();

        Optional<Language> optionalLanguage = languageRepository.findById(taskDTO.getLanguage_id());
        if (!optionalLanguage.isPresent()) {
            return new Result(messageLanguage.getMessage(), false);
        }
        Language language = optionalLanguage.get();

        Optional<Theme> optionalTheme = themeRepository.findById(taskDTO.getTheme_id());
        if (!optionalTheme.isPresent()) {
            return new Result(messageTheme.getMessage(), false);
        }
        Theme theme = optionalTheme.get();

        if (create && taskRepository.existsByNameAndLanguage_Id(taskDTO.getName(), taskDTO.getLanguage_id()) ||
                edit && taskRepository.existsByNameAndIdIsNotAndLanguage_Id(taskDTO.getName(), id, taskDTO.getLanguage_id())) {
            return new Result("Bunday masala ko'rsatilgan dasturlash tilida bor", false);
        }
        task.setName(taskDTO.getName());
        task.setTheme(theme);
        task.setLanguage(language);
        return new Result(true, task);
    }

    public Result addTask(TaskDTO taskDTO) {
        Result result = addingTask(taskDTO, true, false, null);
        if (result.isSuccess()) {
            Task task = (Task) result.getObject();
            taskRepository.save(task);
            return new Result("Masala qo'shildi", true);
        }
        return result;
    }

    public Result deleteTaskById(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            taskRepository.delete(optionalTask.get());
            return new Result("Masala o'chirildi", true);
        }
        return new Result(messageTask.getMessage(), false);
    }

    public Result editTaskById(Integer id, TaskDTO taskDTO) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return new Result(messageTask.getMessage(), false);
        }
        Task editTask = optionalTask.get();
        Result result = addingTask(taskDTO, false, true, id);
        Task task = (Task) result.getObject();
        editTask.setLanguage(task.getLanguage());
        editTask.setName(task.getName());
        editTask.setTheme(task.getTheme());
        taskRepository.save(editTask);
        return new Result("Masala o'zgartildi", true);
    }
}
