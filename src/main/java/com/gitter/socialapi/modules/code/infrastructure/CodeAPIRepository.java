package com.gitter.socialapi.modules.code.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CodeAPIRepository {
    private final String codeApiURL;

    @Autowired
    public CodeAPIRepository( @Value("${application.url}") String codeApiURL) {
        this.codeApiURL = codeApiURL;
    }
}
