package com.example.caderno.controller;

import com.example.caderno.controller.dto.NotaRequest;
import com.example.caderno.controller.dto.NotaResponse;
import com.example.caderno.controller.dto.TagRequest;
import com.example.caderno.controller.dto.TagResponse;
import com.example.caderno.dataprovider.entity.NotaEntity;
import com.example.caderno.dataprovider.entity.TagEntity;
import com.example.caderno.dataprovider.repository.NotaRepository;
import com.example.caderno.dataprovider.repository.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

// locahost:8080/nota
@RestController
@RequestMapping("/nota")
public class NotaController {

    private final NotaRepository notaRepository;
    private final TagRepository tagRepository;

    public NotaController(NotaRepository repository, TagRepository tagRepository) {
        this.notaRepository = repository;
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public ResponseEntity<List<NotaResponse>> encontraNotas() {
        List<NotaEntity> entityList = notaRepository.findAll();

        List<NotaResponse> responseList = new ArrayList<>();
        for (NotaEntity entity : entityList) {
            responseList.add(
                    new NotaResponse(entity.getTitulo(), entity.getNota())
            );
        }

        return ResponseEntity.ok(responseList);
    }


    @PostMapping
    public ResponseEntity<NotaResponse> salvarNota(@RequestBody NotaRequest notaRequest) {
        NotaEntity entity = notaRepository.save(new NotaEntity(notaRequest.getTitulo()
                , notaRequest.getNota()));

        return new ResponseEntity<NotaResponse>(
                new NotaResponse(entity.getTitulo(), entity.getNota()),
                HttpStatus.CREATED
        );
    }

    // /nota/1
    @GetMapping("/{id}")
    public ResponseEntity<NotaResponse> encontrarNotaPorId(@PathVariable Long id) {
        NotaEntity entity = notaRepository.findById(id).get();
        return new ResponseEntity<NotaResponse>(
                new NotaResponse(entity.getTitulo(), entity.getNota()),
                HttpStatus.OK
        );
    }

    // método de atualização de um Objeto
    // endpoint /nota/{id}
    // endpoint /nota/1 -> se refere a nota com id 1
    @PutMapping("/{id}")
    public ResponseEntity<NotaResponse> atualizarNotaPorId(
            @PathVariable Long id,
            @RequestBody NotaRequest request
    ) {
        NotaEntity entity = notaRepository.findById(id).get(); // acho a nota de id 1
        entity.setTitulo(request.getTitulo()); // atualizo o valor do titulo da nota 1
        entity.setNota(request.getNota()); // atualizo o valor do texto da nota 1
        notaRepository.save(entity); // salvo a atualização da nota 1

        return new ResponseEntity<NotaResponse>(
                // resposta rest, do tipo NotaRespose,
                // e o body da resposta é um objeto NotaRespose
//                new NotaResponse(entity.getTitulo(), entity.getNota()),

                NotaResponse.builder() // permite preencher os atributos do objeto sem ter que usar um construtor
                        .titulo(entity.getTitulo())
                        .nota(entity.getNota())
                        .build(),

                HttpStatus.OK // status ok
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarNotaPorId(
            @PathVariable Long id
    ) {
        notaRepository.deleteById(id); // deleta a nota de id {id}
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/tag")
    public ResponseEntity<TagResponse> adicionarTagPorNotaId(
            @PathVariable Long id,
            @RequestBody TagRequest request
    ) {
        NotaEntity notaEntity = notaRepository.findById(id).get(); // acho a nota de id

        TagEntity tagEntity = tagRepository.save(
                new TagEntity(request.getTag(), notaEntity) // temos que salvar tanto a tag quanto o objeto NotaEntity
        ); // Salvar uma TagEntity com os dados da requisição
        return ResponseEntity.ok(
                new TagResponse(
                        tagEntity.getTag(), tagEntity.getNotaEntity().getId()
                ));
    }

    @GetMapping("/{id}/tag")
    public ResponseEntity procurarTagPorNotaId(
            @PathVariable Long id
    ) {
        NotaEntity notaEntity = notaRepository.findById(id).get(); // acho a nota de id

        List<TagEntity> tagEntity = tagRepository.findAll();

        return ResponseEntity.ok(
                tagEntity);
    }

    // tagEntity - id, tag, (notaEntity - id, titulo, nota, dataCriacao)
}
