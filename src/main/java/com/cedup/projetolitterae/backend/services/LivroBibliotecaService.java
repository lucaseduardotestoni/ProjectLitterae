package com.cedup.projetolitterae.backend.services;

import com.cedup.projetolitterae.backend.dto.LivroBibliotecaDto;
import com.cedup.projetolitterae.backend.entities.Biblioteca;
import com.cedup.projetolitterae.backend.entities.Livro;
import com.cedup.projetolitterae.backend.entities.LivroBiblioteca;
import com.cedup.projetolitterae.backend.entities.MensagemRetorno;
import com.cedup.projetolitterae.backend.exceptions.MensagemRetornoException;
import com.cedup.projetolitterae.backend.repositories.LivroBibliotecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LivroBibliotecaService {

    @Autowired
    private LivroBibliotecaRepository repository;
    @Autowired
    private LivroService livroService;
    @Autowired
    private BibliotecaService bibliotecaService;

    public LivroBiblioteca pesquisarPorId(Integer id){
        return (repository.findById(id)).orElse(null);
    }

    @Transactional
    public LivroBiblioteca cadastrarLivroBiblioteca(LivroBibliotecaDto livroBibliotecaDto){
        LivroBiblioteca livroBiblioteca = fromDto(livroBibliotecaDto);
        livroBiblioteca.setId(null);
        return repository.save(livroBiblioteca);
    }

    public LivroBiblioteca alterarLivroBiblioteca(LivroBibliotecaDto novoLivroBibliotecaDto){
        LivroBiblioteca livroBiblioteca = fromDto(novoLivroBibliotecaDto);
        return repository.save(livroBiblioteca);
    }

    public void excluirLivroBiblioteca(Integer id){
        repository.deleteById(id);
    }

    private LivroBiblioteca fromDto(LivroBibliotecaDto livroBibliotecaDto){
        Livro livro = livroService.pesquisarPorId(livroBibliotecaDto.getIdLivro());
        Biblioteca biblioteca = bibliotecaService.pesquisarPorId(livroBibliotecaDto.getIdBiblioteca());

        LivroBiblioteca livroBiblioteca = new LivroBiblioteca();
        livroBiblioteca.setId(livroBibliotecaDto.getId());
        livroBiblioteca.setLivro(livro != null ? livro : (Livro) lancaExcecaoDto("id livro"));
        livroBiblioteca.setBiblioteca(biblioteca != null ? biblioteca : (Biblioteca)  lancaExcecaoDto("id biblioteca"));
        livroBiblioteca.setQuantidadeEstoque(livroBibliotecaDto.getQtdEstoque());

        return livroBiblioteca;
    }

    private Object lancaExcecaoDto(String campo){
        throw new MensagemRetornoException(new MensagemRetorno("ERRO", "Campo "+ campo +" não existe."));
    }

}
