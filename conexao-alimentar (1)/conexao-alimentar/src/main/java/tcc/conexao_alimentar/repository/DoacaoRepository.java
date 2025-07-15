package tcc.conexao_alimentar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.enums.StatusDoacao;
import tcc.conexao_alimentar.model.DoacaoModel;

public interface DoacaoRepository extends JpaRepository<DoacaoModel,Long> {
    List<DoacaoModel> findByStatus(StatusDoacao status);

}
