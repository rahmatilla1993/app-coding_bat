package com.example.appcoding_bat.service;

import com.example.appcoding_bat.entity.Language;
import com.example.appcoding_bat.enums.ElementNotFound;
import com.example.appcoding_bat.models.LanguageDTO;
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

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public Result getLanguageById(Integer id) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        return optionalLanguage.map(language -> new Result(true, language)).orElseGet(() -> new Result(messageLanguage.getMessage(), false));
    }

    private Result addingLanguage(LanguageDTO languageDTO, boolean create, boolean edit, Integer id) {
        Language language = new Language();
        if (create && languageRepository.existsByName(languageDTO.getName()) ||
                edit && languageRepository.existsByIdIsNotAndName(id, languageDTO.getName())) {
            return new Result("Bunday dasturlash tili bor", false);
        }
        language.setName(languageDTO.getName());
        return new Result(true, language);
    }

    public Result addLanguage(LanguageDTO languageDTO) {
        Result result = addingLanguage(languageDTO, true, false, null);
        if (result.isSuccess()) {
            Language language = (Language) result.getObject();
            languageRepository.save(language);
            return new Result("Dasturlash tili saqlandi", true);
        }
        return result;
    }

    public Result editLanguage(Integer id, LanguageDTO languageDTO) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (optionalLanguage.isPresent()) {
            Result result = addingLanguage(languageDTO, false, true, id);
            if (result.isSuccess()) {
                Language editLanguage = optionalLanguage.get();
                Language language = (Language) result.getObject();
                editLanguage.setName(language.getName());
                languageRepository.save(editLanguage);
                return new Result("Dasturlash tili o'zgartirildi", true);
            }
            return result;
        }
        return new Result(messageLanguage.getMessage(), false);
    }

    public Result deleteLanguage(Integer id){
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if(optionalLanguage.isPresent()){
            languageRepository.delete(optionalLanguage.get());
            return new Result("Dasturlash tili o'chirildi",true);
        }
        return new Result(messageLanguage.getMessage(), false);
    }
}
