package com.gitter.socialapi.code;

import com.gitter.socialapi.publication.PublicationEntity;
import com.gitter.socialapi.publication.TypeCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
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

    @ElementCollection
    @CollectionTable(name="code_versions", joinColumns=@JoinColumn(name="code_id"))
    @Column(name="version")
    public List<String> versions =  new ArrayList<>();
}
