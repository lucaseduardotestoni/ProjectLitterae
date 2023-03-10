package com.cedup.projetolitterae.backend.resources;

import com.cedup.projetolitterae.backend.dto.LocacaoDto;
import com.cedup.projetolitterae.backend.entities.LivroBiblioteca;
import com.cedup.projetolitterae.backend.entities.Locacao;
import com.cedup.projetolitterae.backend.services.LocacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/locacao")
public class LocacaoResource {

    @Autowired
    LocacaoService locacaoService;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Locacao> pesquisarPorId(@PathVariable int id){
        Locacao locacao = locacaoService.pesquisarPorId(id);
        return ResponseEntity.ok().body(locacao);
    }

    @RequestMapping(value = "/campo",method = RequestMethod.GET)
    public ResponseEntity<List<Locacao>> pesquisarPorCampo(@Param("id") Long id, @Param("campo") String campo){
        List<Locacao> locacoes = locacaoService.pesquisaCampos(campo, id);
        return ResponseEntity.ok().body(locacoes);
    }

    @RequestMapping(value = "/campo/pendentes",method = RequestMethod.GET)
    public ResponseEntity<List<Locacao>> pesquisarPorCampoPendentes(@Param("id") Long id, @Param("campo") String campo){
        List<Locacao> locacoes = locacaoService.pesquisaCamposPendentes(campo, id);
        return ResponseEntity.ok().body(locacoes);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseEntity<Boolean> pesquisarUsuarioJaLocouLivro(@Param("idUsuario") Long idUsuario, @Param("idLivro") Integer idLivro){
        Boolean jaLocou = locacaoService.usuarioJaLocouLivro(idUsuario, idLivro);
        return ResponseEntity.ok().body(jaLocou);
    }

    @RequestMapping(value = "/usuario/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<Locacao>> pesquisarPorUsuarioId(@PathVariable Long id){
        List<Locacao> locacoes = locacaoService.pesquisarPorUsuario(id);
        return ResponseEntity.ok().body(locacoes);
    }

    @RequestMapping(value = "/biblioteca/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<Locacao>> pesquisarPorBibliotecaId(@PathVariable Long id){
        List<Locacao> locacoes = locacaoService.pesquisarPorBiblioteca(id);
        return ResponseEntity.ok().body(locacoes);
    }

    @RequestMapping(value = "/pendentes/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<Locacao>> locacoesPendentes(@PathVariable Long id){
        List<Locacao> locacoes = locacaoService.locacoesPendentes(id);
        return ResponseEntity.ok().body(locacoes);
    }

    @RequestMapping(value = "/livro/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<Locacao>> pesquisarPorLivroId(@PathVariable Integer id){
        List<Locacao> locacoes = locacaoService.pesquisarPorLivro(id);
        return ResponseEntity.ok().body(locacoes);
    }

    @RequestMapping(value = "/ultimas-locacoes/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<Locacao>> ultimasLocacoes(@PathVariable Long id){
        List<Locacao> locacoes = locacaoService.ultimasLocacoes(id);
        return ResponseEntity.ok().body(locacoes);
    }

    @RequestMapping(value = "/cadastrar",method = RequestMethod.POST)
    public ResponseEntity<Locacao> cadastrar(@Validated @RequestBody LocacaoDto locacao){
        Locacao locacaoCadastrado = locacaoService.locarLivro(locacao);
        return ResponseEntity.ok().body(locacaoCadastrado);
    }

    @RequestMapping(value = "/alterar",method = RequestMethod.PUT)
    public ResponseEntity<Locacao> alterar(@Validated @RequestBody LocacaoDto locacao){
        Locacao locacaoAlterado = locacaoService.alterarLocacao(locacao);
        return ResponseEntity.ok().body(locacaoAlterado);
    }

    @RequestMapping(value = "/excluir/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        locacaoService.excluirLocacao(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/devolver/{id}",method = RequestMethod.POST)
    public ResponseEntity<Void> devolver(@PathVariable Integer id){
        locacaoService.devolver(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/multa/{id}",method = RequestMethod.GET)
    public ResponseEntity<Double> pesquisaMulta(@PathVariable Integer id){
        Double multa = locacaoService.pesquisaMulta(id);
        return ResponseEntity.ok().body(multa);
    }

    @RequestMapping(value = "/zerar-pendencia/{id}",method = RequestMethod.POST)
    public ResponseEntity<Locacao> zerarMulta(@PathVariable Integer id){
        Locacao locacao = locacaoService.zerarPendencia(id);
        return ResponseEntity.ok().body(locacao);
    }
}
