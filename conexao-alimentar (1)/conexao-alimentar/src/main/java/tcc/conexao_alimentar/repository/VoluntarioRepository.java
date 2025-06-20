package tcc.conexao_alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.VoluntarioModel;

public interface VoluntarioRepository extends JpaRepository<VoluntarioModel,Long>{

    boolean existsByEmail(String email);


}
