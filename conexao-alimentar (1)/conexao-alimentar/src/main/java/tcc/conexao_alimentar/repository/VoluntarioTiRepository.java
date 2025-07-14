package tcc.conexao_alimentar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.VoluntarioTiModel;

public interface VoluntarioTiRepository  extends JpaRepository<VoluntarioTiModel,Long>{
    Optional<VoluntarioTiModel> findByVoluntarioId(Long voluntarioId);

}
