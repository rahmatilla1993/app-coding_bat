package com.example.appcoding_bat.controller;

import com.example.appcoding_bat.entity.Theme;
import com.example.appcoding_bat.enums.ElementNotFound;
import com.example.appcoding_bat.models.Result;
import com.example.appcoding_bat.models.ThemeDTO;
import com.example.appcoding_bat.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/theme")
public class ThemeController {

    @Autowired
    ThemeService themeService;

    ElementNotFound messageTheme = ElementNotFound.THEME;
    ElementNotFound messageLanguage = ElementNotFound.LANGUAGE;

    @GetMapping
    public HttpEntity<List<Theme>> getAllThemes() {
        List<Theme> allThemes = themeService.getAllThemes();
        return ResponseEntity.status(HttpStatus.OK).body(allThemes);
    }

    @GetMapping("/{id}")
    public HttpEntity<Result> getThemeById(@PathVariable Integer id) {
        Result result = themeService.getThemeById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/byLanguageId/{language_id}")
    public HttpEntity<List<Result>> getAllThemesByLanguageId(@PathVariable Integer language_id) {
        List<Result> results = themeService.getAllThemesByLanguageId(language_id);
        return ResponseEntity.status(results.size() != 1 || results.get(0).isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(results);
    }

    @PostMapping
    public HttpEntity<Result> addTheme(@Valid @RequestBody ThemeDTO themeDTO) {
        Result result = themeService.addTheme(themeDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : result.getMessage().equals(messageLanguage.getMessage()) ?
                HttpStatus.NOT_FOUND : HttpStatus.CONFLICT).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<Result> deleteTheme(@PathVariable Integer id) {
        Result result = themeService.deleteTheme(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<Result> editTheme(@PathVariable Integer id, @Valid @RequestBody ThemeDTO themeDTO) {
        Result result = themeService.editTheme(id, themeDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : result.getMessage().equals(messageLanguage.getMessage()) ||
                result.getMessage().equals(messageTheme.getMessage()) ? HttpStatus.NOT_FOUND : HttpStatus.CONFLICT).body(result);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
