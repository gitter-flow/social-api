package com.gitter.socialapi.kernel.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateGenerator {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    
    public String now() {
        LocalDateTime now = LocalDateTime.now();
        return now.toString();
    } 
}
