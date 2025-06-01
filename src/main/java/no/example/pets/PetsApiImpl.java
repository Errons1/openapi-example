package no.example.pets;

import no.example.generated.api.PetsApi;
import no.example.generated.models.PetDTO;

import java.util.ArrayList;
import java.util.List;

public class PetsApiImpl implements PetsApi {
    List<PetDTO> pets;

    public PetsApiImpl() {
        this.pets = new ArrayList<>();
        pets.add(new PetDTO(0, "Pondus"));
        pets.add(new PetDTO(1, "Bruno"));
        pets.add(new PetDTO(2, "Kikki"));
        pets.add(new PetDTO(3, "Luna"));
    }

    @Override
    public PetDTO createPets(PetDTO petDTO) {
        petDTO.setId(pets.size());
        pets.add(petDTO);
        return petDTO;
    }

    @Override
    public void deletePetById(Integer petId) {
        pets = pets.stream().filter(p -> !p.getId().equals(petId)).toList();

    }

    @Override
    public List<PetDTO> listPets(Integer limit) {
        return pets.stream().limit(limit).toList();
    }
}
