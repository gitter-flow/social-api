package com.gitter.socialapi.modules.internal.exposition;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "/internal")
public class InternalController {
    @GetMapping("/healthcheck")
    public ResponseEntity<String> getHealthcheck() {
        return ResponseEntity.ok("alive");
    }
}
