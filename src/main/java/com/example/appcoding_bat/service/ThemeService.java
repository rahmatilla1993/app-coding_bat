package com.example.appcoding_bat.service;

import com.example.appcoding_bat.entity.Language;
import com.example.appcoding_bat.entity.Theme;
import com.example.appcoding_bat.enums.ElementNotFound;
import com.example.appcoding_bat.models.Result;
import com.example.appcoding_bat.models.ThemeDTO;
import com.example.appcoding_bat.repository.LanguageRepository;
import com.example.appcoding_bat.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.el.ELContextEvent;
import java.util.*;

@Service
public class ThemeService {

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    LanguageRepository languageRepository;

    ElementNotFound messageTheme = ElementNotFound.THEME;
    ElementNotFound messageLanguage = ElementNotFound.LANGUAGE;

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    public Result getThemeById(Integer id) {
        Optional<Theme> optionalTheme = themeRepository.findById(id);
        return optionalTheme.map(theme -> new Result(true, theme)).orElseGet(() -> new Result(messageTheme.getMessage(), false));
    }

    public List<Result> getAllThemesByLanguageId(Integer language_id) {
        List<Result> results = new ArrayList<>();
        Optional<Language> optionalLanguage = languageRepository.findById(language_id);
        if (optionalLanguage.isPresent()) {
            List<Theme> themes = themeRepository.getAllByLanguageId(language_id);
            for (Theme theme : themes) {
                Result result = new Result(true, theme);
                results.add(result);
            }
            return results;
        }
        Result result = new Result(messageLanguage.getMessage(), false);
        results.add(result);
        return results;
    }

    private Result addingTheme(ThemeDTO themeDTO, boolean create, boolean edit, Integer id) {
        Theme theme = new Theme();
        if (create && themeRepository.existsByName(themeDTO.getName()) ||
                edit && themeRepository.existsByIdIsNotAndName(id, themeDTO.getName())) {
            return new Result("Bunday mavzu bor", false);
        }
        Set<Language> languages = new HashSet<>();
        for (Integer language_id : themeDTO.getLanguages()) {
            Optional<Language> optionalLanguage = languageRepository.findById(language_id);
            if (optionalLanguage.isPresent()) {
                Language language = optionalLanguage.get();
                languages.add(language);
            } else
                return new Result(messageLanguage.getMessage(), false);
        }
        theme.setLanguages(languages);
        theme.setName(themeDTO.getName());
        return new Result(true, theme);
    }

    public Result addTheme(ThemeDTO themeDTO) {
        Result result = addingTheme(themeDTO, true, false, null);
        if (result.isSuccess()) {
            Theme theme = (Theme) result.getObject();
            themeRepository.save(theme);
            return new Result("Mavzu saqlandi", true);
        }
        return result;
    }

    public Result deleteTheme(Integer id) {
        Optional<Theme> optionalTheme = themeRepository.findById(id);
        if (optionalTheme.isPresent()) {
            themeRepository.delete(optionalTheme.get());
            return new Result("Mavzu o'chirildi", true);
        }
        return new Result(messageTheme.getMessage(), false);
    }

    public Result editTheme(Integer id, ThemeDTO themeDTO) {
        Optional<Theme> optionalTheme = themeRepository.findById(id);
        if (optionalTheme.isPresent()) {
            Result result = addingTheme(themeDTO, false, true, id);
            if (result.isSuccess()) {
                Theme editTheme = optionalTheme.get();
                Theme theme = (Theme) result.getObject();
                editTheme.setName(theme.getName());
                editTheme.setLanguages(theme.getLanguages());
                themeRepository.save(editTheme);
                return new Result("Mavzu o'zgartirildi", true);
            }
            return result;
        }
        return new Result(messageTheme.getMessage(), false);
    }
}
