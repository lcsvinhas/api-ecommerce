package org.serratec.backend.service;

import org.serratec.backend.entity.Categoria;
import org.serratec.backend.exception.CategoriaException;
import org.serratec.backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> listar() {
        return repository.findAll();
    }

    public Categoria inserir(Categoria categoria) {
        Optional<Categoria> c = repository.findByCategoria(categoria.getCategoria());
        if (c.isPresent()) {
            throw new CategoriaException("Categoria já cadastrada!");
        }
        return repository.save(categoria);
    }

    public Categoria atualizar(Categoria categoria) {
        Categoria c = repository.findById(categoria.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria inexistente!"));
        c.setCategoria(categoria.getCategoria());
        return repository.save(c);
    }

    public void deletar(Long id) {
        Categoria categoria = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria inexistente!"));
        repository.delete(categoria);
    }
}
