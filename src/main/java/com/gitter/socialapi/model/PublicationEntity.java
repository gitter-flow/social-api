package com.gitter.socialapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Publication")
public class PublicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="commentary_id")
    private UserEntity user;

    @Nullable
    private Long previousPublicationId;

    @Nullable
    private Long type_code_id;

    private String code;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private List<UserEntity> likedBy;


}
