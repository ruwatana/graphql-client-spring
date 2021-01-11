package com.example.graphqlclientspring;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PokemonController {

    private final PokemonRepository pokemonRepository;

    public PokemonController(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @GetMapping("/pokemons")
    public ResponseEntity<List<PokemonResponseEntity>> kantoPokemons() {
        final var pokemons = pokemonRepository.fetchKantoPokemons()
                .stream()
                .map(pokemon -> new PokemonResponseEntity(pokemon.number(), pokemon.name(), pokemon.types()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(pokemons);
    }

    public static class PokemonResponseEntity {
        public final String number;
        public final String name;
        public final List<String> types;

        public PokemonResponseEntity(String number, String name, List<String> types) {
            this.number = number;
            this.name = name;
            this.types = types;
        }
    }
}
