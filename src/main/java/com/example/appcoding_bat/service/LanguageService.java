package com.example.appcoding_bat.service;

import com.example.appcoding_bat.entity.Language;
import com.example.appcoding_bat.enums.ElementNotFound;
import com.example.appcoding_bat.models.Result;
import com.example.appcoding_bat.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    ElementNotFound messageLanguage = ElementNotFound.LANGUAGE;

    public List<Language> getAllLanguages(){
        return languageRepository.findAll();
    }

    public Result getLanguageById(Integer id){
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        return optionalLanguage.map(language -> new Result(true, language)).orElseGet(() -> new Result(messageLanguage.getMessage(), false));
    }
}
