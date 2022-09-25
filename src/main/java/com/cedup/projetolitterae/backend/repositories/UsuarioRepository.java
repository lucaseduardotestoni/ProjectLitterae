package com.cedup.projetolitterae.backend.repositories;

import com.cedup.projetolitterae.backend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findUsuarioByBibliotecaId(Long id);
}
