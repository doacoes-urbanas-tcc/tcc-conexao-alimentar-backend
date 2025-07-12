package tcc.conexao_alimentar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.UsuarioModel;

public interface UsuarioRepository  extends JpaRepository<UsuarioModel,Long>{

    Optional<UsuarioModel> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UsuarioModel> findByAtivoFalse();

    List<UsuarioModel> findByAtivo(boolean ativo);
}
