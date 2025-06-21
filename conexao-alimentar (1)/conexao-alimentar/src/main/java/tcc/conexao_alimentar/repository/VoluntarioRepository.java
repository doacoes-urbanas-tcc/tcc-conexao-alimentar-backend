package tcc.conexao_alimentar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.VoluntarioModel;

public interface VoluntarioRepository extends JpaRepository<VoluntarioModel,Long>{

    
    Optional<VoluntarioModel> findByEmail(String email);
    boolean existsByEmail(String email); 
    List<VoluntarioModel> findByAtivoFalse();



}
