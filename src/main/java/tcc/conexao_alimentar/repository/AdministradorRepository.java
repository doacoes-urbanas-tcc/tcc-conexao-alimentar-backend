package tcc.conexao_alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.AdministradorModel;

public interface AdministradorRepository extends JpaRepository<AdministradorModel,Long> {

}
