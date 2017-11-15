package ru.ipolynkina.converter.beans;

import java.util.ArrayList;
import java.util.List;

public class Language {

    private List<String> languages = new ArrayList<>();

    public Language(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public String getLanguageByIndex(int index) {
        return languages.get(index);
    }
}
