package com.example.appcoding_bat.controller;

import com.example.appcoding_bat.entity.Language;
import com.example.appcoding_bat.models.Result;
import com.example.appcoding_bat.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/language")
public class LanguageController {

    @Autowired
    LanguageService languageService;

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
}
