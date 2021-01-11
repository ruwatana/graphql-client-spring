package com.example.graphqlclientspring;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.rx3.Rx3Apollo;
import graphqlpokemon.KantoPokemonsQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PokemonRepository {
    public List<KantoPokemonsQuery.Pokemon> fetchKantoPokemons() {
        final var apolloClient = ApolloClient.builder()
                .serverUrl("http://localhost:5000/graphql/endpoint")
                .build();

        final var query = KantoPokemonsQuery.builder().build();

        final var apolloQueryCall = apolloClient.query(query);

        // ブロッキング処理を簡単に書くため、Rx3Apollo を利用
        return Rx3Apollo.from(apolloQueryCall)
                .map(Response::getData)
                .map(KantoPokemonsQuery.Data::pokemons)
                .blockingFirst();
    }
}
