package com.gitter.socialapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "code")
public class CodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="publication_id")
    private PublicationEntity publication;

    private String bucket;

    @Enumerated(EnumType.STRING)
    private TypeCode typeCode;

    public void setPublication(PublicationEntity publication) {
        this.publication = publication;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setTypeCode(TypeCode typeCode) {
        this.typeCode = typeCode;
    }
}
