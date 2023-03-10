package com.cedup.projetolitterae.backend.services;

import com.cedup.projetolitterae.backend.entities.Endereco;
import com.cedup.projetolitterae.backend.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    public Endereco pesquisarPorId(Integer id){
        return (repository.findById(id)).orElse(null);
    }

    @Transactional
    public Endereco cadastrarEndereco(Endereco endereco){
        endereco.setId(null);
        return repository.save(endereco);
    }

    public Endereco alterarEndereco(Endereco novoEndereco){
        return repository.save(novoEndereco);
    }

    public void excluirEndereco(Integer id){
        repository.deleteById(id);
    }
}
