package com.example.caderno.controller;

import com.example.caderno.controller.dto.NotaRequest;
import com.example.caderno.controller.dto.NotaResponse;
import com.example.caderno.dataprovider.entity.NotaEntity;
import com.example.caderno.dataprovider.repository.NotaRepository;
import com.example.caderno.dataprovider.repository.TagRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity encontraNotas(){
        notaRepository.save(new NotaEntity("Titulo", "Nota"));
        notaRepository.save(new NotaEntity("Titulo1", "Nota1"));
        notaRepository.save(new NotaEntity("Titulo2", "Nota2"));

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
    public NotaResponse salvaNota(@RequestBody NotaRequest notaRequest){
        //logica
        return new NotaResponse(notaRequest.getTitulo(), notaRequest.getNota());
    }
}
