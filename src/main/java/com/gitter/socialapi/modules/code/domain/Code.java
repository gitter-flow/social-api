package com.gitter.socialapi.modules.code.domain;

import com.gitter.socialapi.modules.publication.domain.Publication;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "code")
public class Code {
    @Id
    @Column(name = "id", nullable = false)
    private String id = UUID.randomUUID().toString();

    @OneToOne
    @JoinColumn(name = "publication_id")
    @EqualsAndHashCode.Exclude
    private Publication publication;
    @Enumerated(EnumType.STRING)
    private CodeType codeType;

    @ElementCollection
    @CollectionTable(
            name = "versions",
            joinColumns = @JoinColumn(name="code_id")
    )
    private List<Version> versions = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    public Code(Publication publication,  CodeType codeType, List<Version> versions) {
        this.publication = publication;
        this.codeType = codeType;
        this.versions = versions;
    }
    public Code(Publication publication, CodeType codeType) {
        this.publication = publication;
        this.codeType = codeType;
    }
}
