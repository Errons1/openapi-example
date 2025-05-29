import {useEffect, useState} from "react";
import {type PetDTO, servers} from "../generated";

const petsApi = servers.default.petsApi;

export function Pets() {

    const [pets, setPets] = useState<PetDTO[]>([])

    useEffect(() => {
        petsApi.listPets({queryParams: {limit: 10}}).then(result => {
            setPets(result)
            console.log("Pets" + result);
        })
    }, [])

    console.log(pets)

    return (
        <>
            <div>Hello World with lots of pets!</div>
            {pets.map(p => {
                return <div>{p.name}</div>
            })}
        </>)
}
