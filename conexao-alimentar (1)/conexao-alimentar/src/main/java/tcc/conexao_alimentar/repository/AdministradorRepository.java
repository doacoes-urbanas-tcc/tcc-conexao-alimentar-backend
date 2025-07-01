package tcc.conexao_alimentar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.AdministradorModel;

public interface AdministradorRepository extends JpaRepository<AdministradorModel,Long>{
     Optional<AdministradorModel> findByEmail(String email);

}
