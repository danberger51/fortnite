package org.example.fortnite.controllers.Services;

import org.example.fortnite.controllers.Repositories.WeaponRepository;
import org.example.fortnite.models.Weapon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class WeaponService {
    private final WeaponRepository weaponRepository;
    @Autowired
    public WeaponService(WeaponRepository weaponRepository) {
        this.weaponRepository = weaponRepository;
    }
    public Weapon findWeaponById(Integer id) {
        return weaponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
    }
    public Weapon findWeaponByName(String name) {
        return weaponRepository.findByName(name);
    }
    public List<Weapon> findWeaponByTyp(String typ) {
        return weaponRepository.findByTyp(typ);
    }
    public Iterable<Weapon> findAllWeapons() {
        return weaponRepository.findAll();
    }
    public void insertWeapon(Weapon weapon){
        weaponRepository.save(weapon);
    }
    public void updateWeapon(Weapon weapon){
        weaponRepository.save(weapon);
    }
    public void deleteById(Integer id){
        weaponRepository.deleteById(id);

    }

}
