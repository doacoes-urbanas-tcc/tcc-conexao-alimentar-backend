package tcc.conexao_alimentar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.TaskTiModel;

public interface TaskTiRepository extends JpaRepository<TaskTiModel, Long> {
    List<TaskTiModel> findByFechadaFalseOrderByDataCriacaoDesc();
    long countByFechadaFalse(); 
    long countByFechadaTrue();



}
