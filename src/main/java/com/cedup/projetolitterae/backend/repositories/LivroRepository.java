package com.cedup.projetolitterae.backend.repositories;

import com.cedup.projetolitterae.backend.entities.Livro;
import com.cedup.projetolitterae.backend.entities.LivroBiblioteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {

    @Query(value = "select l.* from livro l, livro_biblioteca lb where l.nome like ?1 and lb.id_biblioteca = ?2 group by id"
            , nativeQuery = true)
    List<Livro> findLivroNome(String value, Long idBiblioteca);

    @Query(value = "select l.* from livro l, livro_biblioteca lb where l.autor like ?1 and lb.id_biblioteca = ?2 group by id"
            , nativeQuery = true)
    List<Livro> findLivroAutor(String value, Long idBiblioteca);

    @Query(value = "select l.* from livro l, livro_biblioteca lb where l.isdb like ?1 and lb.id_biblioteca = ?2 group by id"
            , nativeQuery = true)
    List<Livro> findLivroIsdb(String value, Long idBiblioteca);

    @Query(value = "select l.* from livro l, livro_biblioteca lb where l.editora like ?1 and lb.id_biblioteca = ?2 group by id"
            , nativeQuery = true)
    List<Livro> findLivroEditora(String value, Long idBiblioteca);

    @Query(value = "select l.* from livro l, livro_biblioteca lb where l.classificacao_etaria like ?1 and lb.id_biblioteca = ?2 group by id"
            , nativeQuery = true)
    List<Livro> findLivroClassificacaoEtaria(String value, Long idBiblioteca);

    @Query(value = "select l.* from livro l " +
            "inner join livro_biblioteca lb on lb.id_biblioteca = :idBiblioteca and lb.id_livro = l.id " +
            "inner join livro_genero lg  on lg.livro_id = l.id and lg.genero = :value"
            , nativeQuery = true)
    List<Livro> findLivroGenero(@Param("value") Integer value, @Param("idBiblioteca") Long idBiblioteca);

    @Query(value = "select * from livro l " +
            "inner join livro_biblioteca lb " +
            " on lb.id_livro = l.id " +
            " and lb.id_biblioteca = :id " +
            "where l.isdb like %:isdb%", nativeQuery = true)
    List<Livro> buscarLivroPorIsdb(@Param("id") Long id, @Param("isdb") String isdb);

    @Query(value = "select * from livro l " +
            "inner join livro_biblioteca lb " +
            " on lb.id_livro = l.id " +
            " and lb.id_biblioteca = :id " +
            "where l.nome like %:nome%", nativeQuery = true)
    List<Livro> buscarLivroPorNome(@Param("id") Long id, @Param("nome") String nome);

    @Query(value = "select * from livro l " +
            "inner join livro_biblioteca lb " +
            " on lb.id_livro = l.id " +
            " and lb.id_biblioteca = :id " +
            "where l.autor like %:autor%", nativeQuery = true)
    List<Livro> buscarLivroPorAutor(@Param("id") Long id, @Param("autor") String autor);

}
