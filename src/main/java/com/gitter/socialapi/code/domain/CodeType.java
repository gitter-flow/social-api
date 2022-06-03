package com.gitter.socialapi.code.domain;

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
}
