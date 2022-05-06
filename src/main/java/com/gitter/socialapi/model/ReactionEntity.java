package com.gitter.socialapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Reaction")
public class ReactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="publication_id")
    @Nullable
    private PublicationEntity publication ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="commentary_id")
    @Nullable
    private CommentaryEntity commentary ;
}
