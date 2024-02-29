package org.example.fortnite.controllers.Services;

import org.example.fortnite.controllers.Repositories.SkinRepository;
import org.example.fortnite.models.Skin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class SkinService {
    private final SkinRepository skinRepository;

    @Autowired
    public SkinService(SkinRepository skinRepository) {
        this.skinRepository = skinRepository;
    }

    public Skin findSkinById(Integer id) {
        return skinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Skin id was not found"));
    }

    public Iterable<Skin> findAllSkins() {
        return skinRepository.findAll();
    }

    public void insertSkin(Skin skin){
        skinRepository.save(skin);
    }

    public void updateSkin(Skin skin){
        skinRepository.save(skin);
    }

    public void deleteById(Integer id){
        skinRepository.deleteById(id);
    }

}
