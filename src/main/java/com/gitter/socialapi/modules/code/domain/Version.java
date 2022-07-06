package com.gitter.socialapi.modules.code.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Version {
    @Column(name = "code")
    private  String codeVersion;
    
    @Column(name = "output")
    private String outputVersion;
}
