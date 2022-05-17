package com.gitter.socialapi.service;
import com.gitter.socialapi.model.CodeEntity;
import com.gitter.socialapi.model.PublicationEntity;
import com.gitter.socialapi.model.UserEntity;
import com.gitter.socialapi.payload.request.CreatePublicationRequest;
import com.gitter.socialapi.payload.request.EditContentPublicationRequest;
import com.gitter.socialapi.payload.request.EditLikePublicationRequest;
import com.gitter.socialapi.repository.CodeRepository;
import com.gitter.socialapi.repository.PublicationRepository;
import com.gitter.socialapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;

    @Autowired
    public PublicationService(PublicationRepository publicationRepository, UserRepository userRepository, CodeRepository codeRepository) {
        this.publicationRepository = publicationRepository;
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
    }


    public void addPublication(CreatePublicationRequest createContentPublicationRequest){
        PublicationEntity publicationEntity = new PublicationEntity();
        Optional<UserEntity> userFound = userRepository.findById(Long.valueOf(createContentPublicationRequest.getUserId()));
        Optional<PublicationEntity> publicationFound = publicationRepository.findById(Long.valueOf(createContentPublicationRequest.getPublicationId()));
        Optional<CodeEntity> codeFound = codeRepository.findById(Long.valueOf(createContentPublicationRequest.getCodeId()));
        publicationEntity.setUser(userFound.get());
        publicationEntity.setPublicationEntity(publicationFound.get());
        publicationEntity.setContent(createContentPublicationRequest.getContent());
        publicationEntity.setDisable(false);
        publicationEntity.setCode(codeFound.get());
        publicationEntity.setLikedBy(createContentPublicationRequest.getLikedBy());
        publicationRepository.save(publicationEntity);
    }

    public List<PublicationEntity> getPublications(){

        return publicationRepository.findAll();
    }

    public void updatePublication(PublicationEntity publication){
        Optional<PublicationEntity> publicationFound = publicationRepository.findById(publication.getId());
        if(publicationFound.get().getDisable()==true){
            throw new NullPointerException("Publication disable");
        }
        if (publicationFound.isPresent()) {
            publicationFound.get().setUser(publication.getUser());
            publicationFound.get().setPublicationEntity(publication.getPublicationEntity());
            publicationFound.get().setContent(publication.getContent());
            publicationFound.get().setCode(publication.getCode());
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
            publicationFound.get().setDisable(true);
            publicationRepository.save(publicationFound.get());
        }
        else {
            throw new NullPointerException("Publication not found");
        }
    }

    public void updateContentPublication(EditContentPublicationRequest contentPublicationRequest) {
        Optional<PublicationEntity> publicationFound = publicationRepository.findById(Long.parseLong(contentPublicationRequest.getId()));
        if (publicationFound.isPresent()) {
            publicationFound.get().setContent(contentPublicationRequest.getContent());
            publicationRepository.save(publicationFound.get());
        }
        else {
            throw new NullPointerException("Publication not found");
        }
    }

    public void likePublication(EditLikePublicationRequest editLikePublicationRequest) {
        Optional<PublicationEntity> publicationFound = publicationRepository.findById(Long.parseLong(editLikePublicationRequest.getPublicationId()));
        if (publicationFound.isPresent()) {
            List<UserEntity> likedBy = publicationFound.get().getLikedBy();
            Optional<UserEntity> userFound = userRepository.findById(Long.parseLong(editLikePublicationRequest.getUserId()));
            if(userFound.isPresent()){
                likedBy.add(userFound.get());
                publicationRepository.save(publicationFound.get());
            }else {
                throw new NullPointerException("User not found");
            }
        }
        else {
            throw new NullPointerException("Publication not found");
        }
    }

    public void unlikePublication(EditLikePublicationRequest editLikePublicationRequest) {
        Optional<PublicationEntity> publicationFound = publicationRepository.findById(Long.parseLong(editLikePublicationRequest.getPublicationId()));
        if (publicationFound.isPresent()) {
            List<UserEntity> likedBy = publicationFound.get().getLikedBy();
            Optional<UserEntity> userFound = userRepository.findById(Long.parseLong(editLikePublicationRequest.getUserId()));
            if(userFound.isPresent()){
                likedBy.remove(userFound.get());
                publicationRepository.save(publicationFound.get());
            }else {
                throw new NullPointerException("User not found");
            }
        }
        else {
            throw new NullPointerException("Publication not found");
        }
    }
}
