package com.example.appcoding_bat.controller;

import com.example.appcoding_bat.entity.Language;
import com.example.appcoding_bat.enums.ElementNotFound;
import com.example.appcoding_bat.models.LanguageDTO;
import com.example.appcoding_bat.models.Result;
import com.example.appcoding_bat.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/language")
public class LanguageController {

    @Autowired
    LanguageService languageService;

    ElementNotFound messageLanguage = ElementNotFound.LANGUAGE;

    @GetMapping
    public HttpEntity<List<Language>> getAllLanguages() {
        List<Language> allLanguages = languageService.getAllLanguages();
        return ResponseEntity.status(HttpStatus.OK).body(allLanguages);
    }

    @GetMapping("/{id}")
    public HttpEntity<Result> getLanguageById(@PathVariable Integer id) {
        Result languageById = languageService.getLanguageById(id);
        return ResponseEntity.status(languageById.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(languageById);
    }

    @PostMapping
    public HttpEntity<Result> addLanguage(@Valid @RequestBody LanguageDTO languageDTO) {
        Result result = languageService.addLanguage(languageDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<Result> editLanguage(@PathVariable Integer id, @Valid @RequestBody LanguageDTO languageDTO) {
        Result result = languageService.editLanguage(id, languageDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : result.getMessage().equals(messageLanguage.getMessage()) ?
                HttpStatus.NOT_FOUND : HttpStatus.CONFLICT).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<Result> deleteLanguage(@PathVariable Integer id) {
        Result result = languageService.deleteLanguage(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }
}
