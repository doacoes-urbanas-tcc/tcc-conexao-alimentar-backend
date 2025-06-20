package tcc.conexao_alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.ComercioModel;

public interface ComercioRepository  extends JpaRepository<ComercioModel,Long>{

}
