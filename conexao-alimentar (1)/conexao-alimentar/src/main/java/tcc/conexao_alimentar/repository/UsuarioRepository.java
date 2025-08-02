package tcc.conexao_alimentar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.enums.StatusUsuario;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.model.UsuarioModel;

public interface UsuarioRepository  extends JpaRepository<UsuarioModel,Long>{

    Optional<UsuarioModel> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UsuarioModel> findByStatus(StatusUsuario status);

    List<UsuarioModel> findByStatusNot(StatusUsuario statusAtivo);

    long countByStatusAndTipoUsuarioNot(StatusUsuario statusAtivo, TipoUsuario tipoExcluido);

    long countByStatusNotAndTipoUsuarioNot(StatusUsuario statusAtivo, TipoUsuario tipoExcluido);

    List<UsuarioModel> findTop5ByStatusAndTipoUsuarioNotOrderByIdDesc(StatusUsuario status, TipoUsuario tipoExcluido);






}
