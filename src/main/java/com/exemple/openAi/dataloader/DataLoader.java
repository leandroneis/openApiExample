package com.exemple.openAi.dataloader;

import com.exemple.openAi.model.Pessoa;
import com.exemple.openAi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public void run(String... args) throws Exception {
        String[] nomes = {"João", "Maria", "Carlos", "Ana", "Pedro", "Paula", "José", "Mariana", "Rafael", "Fernanda"};
        String[] sobrenomes = {"Silva", "Oliveira", "Souza", "Costa", "Santos", "Pereira", "Ferreira", "Almeida", "Nascimento", "Lima"};

        for (int i = 0; i < 100; i++) {
            String nome = nomes[i % nomes.length];
            String sobrenome = sobrenomes[i % sobrenomes.length];
            String email = nome.toLowerCase() + "_" + sobrenome.toLowerCase() + "@example.com";
            int idade = (i % 100) + 11;
            pessoaRepository.save(new Pessoa(null, nome + " " + sobrenome, email, idade));
        }
    }
}