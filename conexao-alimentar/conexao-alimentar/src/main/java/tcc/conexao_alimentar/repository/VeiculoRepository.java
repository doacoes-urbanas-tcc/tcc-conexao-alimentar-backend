package tcc.conexao_alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.VeiculoModel;

public interface VeiculoRepository extends JpaRepository<VeiculoModel,Long> {

}
