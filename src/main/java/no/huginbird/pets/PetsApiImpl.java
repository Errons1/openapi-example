package no.huginbird.pets;

import no.huginbird.generated.api.PetsApi;
import no.huginbird.generated.models.PetDTO;

import java.util.List;

public class PetsApiImpl implements PetsApi {
    @Override
    public void createPets(PetDTO petDTO) {

    }

    @Override
    public List<PetDTO> listPets(Integer limit) {
        return List.of(new PetDTO(1, "Pondus"));
    }

    @Override
    public PetDTO showPetById(String petId) {
        return null;
    }
}
