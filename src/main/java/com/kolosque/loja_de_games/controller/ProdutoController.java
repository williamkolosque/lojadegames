package com.kolosque.loja_de_games.controller;

import com.kolosque.loja_de_games.model.Produto;
import com.kolosque.loja_de_games.repository.CategoriaRepository;
import com.kolosque.loja_de_games.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/")
    public ResponseEntity<List<Produto>> getAll(){
        return ResponseEntity.ok(produtoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Long id){
        return produtoRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<List<Produto>> getByAno(@PathVariable String ano){
        return ResponseEntity.ok(produtoRepository.findAllByAnoContainingIgnoreCase(ano));
    }

    @PostMapping
    public ResponseEntity<Produto> post(@Valid @RequestBody Produto produtos) {
      if(categoriaRepository.existsById(produtos.getCategorias().getId()))
          return ResponseEntity.status(HttpStatus.CREATED)
          .body(produtoRepository.save(produtos));

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Produto> put(@Valid @RequestBody Produto produtos){
        if (produtoRepository.existsById(produtos.getId())) {

            if (categoriaRepository.existsById(produtos.getCategorias().getId()))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(produtoRepository.save(produtos));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Produto> produtos = produtoRepository.findById(id);
        if (produtos.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        produtoRepository.deleteById(id);
    }


}
