package com.gitter.socialapi.modules.code.application;

import com.gitter.socialapi.kernel.exceptions.InvalidCodeTypeException;
import com.gitter.socialapi.modules.code.domain.CodeType;

public class CodeAPIMapper {
    public static String toAPICodeType(CodeType codeType) {
        if(codeType == CodeType.C) {
            return "c";
        } else if (codeType == CodeType.SHELL) {
            return "shell";
        } else if(codeType == CodeType.PYTHON) {
            return "python";
        } else {
            throw new IllegalArgumentException();
        }
    }
}
