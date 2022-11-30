package com.example.caderno.controller;

import com.example.caderno.controller.dto.NotaRequest;
import com.example.caderno.controller.dto.NotaResponse;
import com.example.caderno.dataprovider.entity.NotaEntity;
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
    public ResponseEntity<List<NotaResponse>> encontraNotas(){

        List<NotaEntity> entityList = notaRepository.findAll();

        List<NotaResponse> responseList = new ArrayList<>();
        for (NotaEntity entity: entityList) {
            responseList.add(
                new NotaResponse(entity.getTitulo(), entity.getNota())
            );
        }

        return ResponseEntity.ok(responseList);
    }


    @PostMapping
    public ResponseEntity<NotaResponse> salvarNota(@RequestBody NotaRequest notaRequest){
        NotaEntity entity = notaRepository.save(new NotaEntity(notaRequest.getTitulo()
                , notaRequest.getNota()));

        return new ResponseEntity<NotaResponse>(
                new NotaResponse(entity.getTitulo(), entity.getNota()),
                HttpStatus.CREATED
        );
    }

    // /nota/1
    @GetMapping("/{id}")
    public ResponseEntity<NotaResponse> encontraNotaPorId(@PathVariable Long id){
        NotaEntity entity = notaRepository.findById(id).get();
        return new ResponseEntity<NotaResponse>(
                new NotaResponse(entity.getTitulo(), entity.getNota()),
                HttpStatus.OK
        );
    }
}
