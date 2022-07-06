package com.gitter.socialapi.modules.code.domain;

import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.InvalidCodeTypeException;

public enum CodeType {
    C("c"),
    PYTHON("python"),
    SHELL("shell");
    
    private String text;
    CodeType(String text) {
        this.text = text;
    }
    public String getText() {
        return this.text;
    }
    public static CodeType fromString(String text) throws InvalidParameterException {
        for (CodeType b : CodeType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw InvalidCodeTypeException.withValue(text);
    }
    
    public static String extension(CodeType codeType) {
        switch (codeType) {
            case C -> {
                return "c";
            }
            case PYTHON -> {
                return "py";
            }
            case SHELL -> {
                return "sh";
            }
            default -> {
                return "";
            }
        }
    }
}
