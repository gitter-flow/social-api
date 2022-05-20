package com.gitter.socialapi.publication.domain;

import com.gitter.socialapi.code.domain.Code;
import com.gitter.socialapi.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "publication")
@AllArgsConstructor
@NoArgsConstructor
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    private String content;
    
    @Column(name = "disabled")
    private Boolean disabled = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="code_id")
    @Nullable
    private Code code;

    @ManyToOne(fetch = FetchType.LAZY)
    private Publication sharedPublication;

    @ManyToOne(fetch = FetchType.LAZY)
    private Publication parentPublication;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> likedBy;
}
