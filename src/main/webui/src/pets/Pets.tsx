import {useEffect, useState} from "react";
import {type PetDTO, servers} from "../generated";

const petsApi = servers.pets.petsApi;

export function Pets() {

    const [pets, setPets] = useState<PetDTO[]>([])
    const [petName, setPetName] = useState('')

    useEffect(() => {
        petsApi.listPets({queryParams: {limit: 10}}).then(result => {
            setPets(result)
        })
    }, [])

    function orderNewPet(petName: string) {
        petsApi.createPets({petDTO: {id: 0, name: petName}}).then(newPet => {
            setPets(prev => {
                return [...prev, newPet];
            })
        })
        alert("You ordered a new pet to the store!")
    }

    function sellPet(petId: number) {
        petsApi.deletePetById({pathParams: {petId}}).then(() => {
            setPets(prev => {
                return prev.filter(p => p.id !== petId);
            })
        })
        alert("You sold a pet!")
    }

    return (
        <>
            <h3>Click on pet to sell them</h3>
            {pets.map(p => {
                return (
                    <div onClick={() => sellPet(p.id)}>
                        {p.name}
                    </div>
                )
            })}
            <br/>

            <form onSubmit={(e) => {
                e.preventDefault();
                orderNewPet(petName);
                setPetName('');
            }}>
                <input
                    type="text"
                    value={petName}
                    onChange={(e) => setPetName(e.target.value)}
                    placeholder="Enter new pet name"
                />
                <button type="submit">Order Pet</button>
            </form>
        </>)
}
