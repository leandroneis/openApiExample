package com.exemple.openAi.controller;

import com.exemple.openAi.model.Pessoa;
import com.exemple.openAi.repository.PessoaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "Pessoas", description = "Operações relacionadas a pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

//    @PostMapping
//    @Operation(summary = "Criar uma nova pessoa", description = "Cadastra uma nova pessoa no sistema")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Pessoa criada com sucesso",
//                    content = @Content(schema = @Schema(implementation = Pessoa.class))),
//            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
//    })
//    public Pessoa createPessoa(@RequestBody Pessoa pessoa) {
//        return pessoaRepository.save(pessoa);
//    }
@PostMapping
@Operation(summary = "Criar uma nova pessoa", description = "Cadastra uma nova pessoa no sistema")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa criada com sucesso",
                content = @Content(schema = @Schema(implementation = Pessoa.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
})
@io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(
                examples = @ExampleObject(
                        name = "PessoaExemplo",
                        value = """
                {
                  "nome": "João Silva",
                  "email": "joao@email.com",
                  "idade": 30
                }"""
                )
        )
)
public Pessoa createPessoa(@RequestBody Pessoa pessoa) {
    return pessoaRepository.save(pessoa);
}

    @GetMapping("/{id}")
    @Operation(summary = "Obter pessoa por ID", description = "Recupera uma pessoa específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada",
                    content = @Content(schema = @Schema(implementation = Pessoa.class))),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<Pessoa> getPessoaById(
            @Parameter(description = "ID da pessoa a ser recuperada", example = "1", required = true)
            @PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        return pessoa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todas as pessoas", description = "Recupera todas as pessoas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de pessoas retornada com sucesso",content = @Content(schema = @Schema(implementation = Pessoa.class)))
    public List<Pessoa> getAllPessoas() {
        return pessoaRepository.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pessoa", description = "Atualiza os dados de uma pessoa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = Pessoa.class))),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Pessoa> updatePessoa(
            @Parameter(description = "ID da pessoa a ser atualizada", example = "1", required = true)
            @PathVariable Long id,
            @RequestBody Pessoa pessoaDetails) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isPresent()) {
            Pessoa updatedPessoa = pessoa.get();
            updatedPessoa.setNome(pessoaDetails.getNome());
            updatedPessoa.setEmail(pessoaDetails.getEmail());
            updatedPessoa.setIdade(pessoaDetails.getIdade());
            return ResponseEntity.ok(pessoaRepository.save(updatedPessoa));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pessoa", description = "Remove uma pessoa do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pessoa excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<Void> deletePessoa(
            @Parameter(description = "ID da pessoa a ser excluída", example = "1", required = true)
            @PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isPresent()) {
            pessoaRepository.delete(pessoa.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}