package tcc.conexao_alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.UsuarioModel;

public interface VoluntarioRepository extends JpaRepository<UsuarioModel,Long>{

}
