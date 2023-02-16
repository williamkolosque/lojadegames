package com.kolosque.loja_de_games.controller;


import com.kolosque.loja_de_games.model.Categorias;
import com.kolosque.loja_de_games.repository.CategoriasRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriasController {

    @Autowired
    private CategoriasRepository categoriasRepository;

    @GetMapping
    public ResponseEntity<List<Categorias>> getAll(){
        return ResponseEntity.ok(categoriasRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorias> getById(@PathVariable Long id){
        return categoriasRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Categorias>> getByTipo(@PathVariable String marca){
        return ResponseEntity.ok(categoriasRepository
                .findAllByMarcaContainingIgnoreCase(marca));
    }

    @PostMapping
    public ResponseEntity<Categorias> post(@Valid @RequestBody Categorias categorias){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriasRepository.save(categorias));
    }

    @PutMapping
    public ResponseEntity<Categorias> put(@Valid @RequestBody Categorias categorias){
        return categoriasRepository.findById(categorias.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(categoriasRepository.save(categorias)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Categorias> categorias = categoriasRepository.findById(id);

        if(categorias.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        categoriasRepository.deleteById(id);
    }

}
