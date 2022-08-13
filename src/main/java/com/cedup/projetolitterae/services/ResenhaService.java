package com.cedup.projetolitterae.services;

import com.cedup.projetolitterae.entities.Resenha;
import com.cedup.projetolitterae.repositories.EnderecoRepository;
import com.cedup.projetolitterae.repositories.ResenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ResenhaService {

    @Autowired
    private ResenhaRepository repository;

    public Resenha pesquisarPorId(Integer id){
        return (repository.findById(id)).get();
    }

    public List<Resenha> pesquisarResenhaPorIdLivro(Integer id){
        return repository.findResenhaByLivroId(id);
    }

    public List<Resenha> pesquisarResenhaPorIdUsuario(Integer id){
        return repository.findResenhaByUsuarioId(id);
    }

    @Transactional
    public Resenha cadastrarResenha(Resenha resenha){
        resenha.setId(null);
        return repository.save(resenha);
    }

    public Resenha alterarResenha(Resenha novoResenha){
        repository.save(novoResenha);
        return novoResenha;
    }

    public void excluirResenha(Integer id){
        repository.deleteById(id);
    }
}
