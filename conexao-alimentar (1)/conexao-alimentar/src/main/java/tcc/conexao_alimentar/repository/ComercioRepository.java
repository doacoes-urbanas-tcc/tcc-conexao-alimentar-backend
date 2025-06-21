package tcc.conexao_alimentar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.ComercioModel;

public interface ComercioRepository  extends JpaRepository<ComercioModel,Long>{

    Optional<ComercioModel> findByEmail(String email);
    boolean existsByEmail(String email); 


}
