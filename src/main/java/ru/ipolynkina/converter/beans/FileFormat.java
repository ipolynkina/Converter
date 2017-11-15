package ru.ipolynkina.converter.beans;

import java.util.ArrayList;
import java.util.List;

public class FileFormat {

    private List<String> formats;

    public FileFormat(List<String> formats) {
        this.formats = formats;
    }

    public List<String> getFormats() {
        return formats;
    }

    public String getFormatByIndex(int index) {
        if((index >= 0) && (index <= formats.size())) {
            return formats.get(index);
        }
        return "";
    }

    public void addAllFormats(List<String> f) {
        formats.addAll(f);
    }

    public List<String> excludeFormat(String format) {
        List<String> newFormats = new ArrayList<>();
        for(String f : formats) {
            if(!f.equals(format)) {
                newFormats.add(f);
            }
        }
        return newFormats;
    }

    public void clear() {
        formats.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(String format : formats) {
            sb.append(format);
            sb.append(", ");
        }
        return sb.toString();
    }
}
