package com.gitter.socialapi.service;
import com.gitter.socialapi.model.CommentaryEntity;
import com.gitter.socialapi.model.PublicationEntity;
import com.gitter.socialapi.model.UserEntity;
import com.gitter.socialapi.repository.CommentaryRepository;
import com.gitter.socialapi.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;

    @Autowired
    public PublicationService(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }


    public void addPublication(PublicationEntity publication){
        publicationRepository.save(publication);
    }

    public List<PublicationEntity> getPublications(){
        return publicationRepository.findAll();
    }

    public void updatePublication(PublicationEntity publication){
        Optional<PublicationEntity> publicationFound = publicationRepository.findById(publication.getId());
        if (publicationFound.isPresent()) {
            publicationFound.get().setPreviousPublicationId(publication.getId());
            publicationFound.get().setUser(publication.getUser());
            publicationFound.get().setCode(publication.getCode());
            publicationFound.get().setType_code_id(publication.getType_code_id());
            List<UserEntity> usersLiked = publicationFound.get().getLikedBy();
            usersLiked.forEach(user -> usersLiked.add(user));
            publicationFound.get().setLikedBy(usersLiked);
        }
        else {
            throw new NullPointerException("Publication not found");
        }
    }

    public void deletePublication(Long id){
        Optional<PublicationEntity> publicationFound = publicationRepository.findById(id);
        if(publicationFound.isPresent()){
            publicationRepository.delete(publicationFound.get());
        }
        else {
            throw new NullPointerException("Publication not found");
        }
    }
}
