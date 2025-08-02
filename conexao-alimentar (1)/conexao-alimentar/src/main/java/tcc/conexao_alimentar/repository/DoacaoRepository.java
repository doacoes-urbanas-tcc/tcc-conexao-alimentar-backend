package tcc.conexao_alimentar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.enums.StatusDoacao;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.UsuarioModel;

public interface DoacaoRepository extends JpaRepository<DoacaoModel,Long> {
    List<DoacaoModel> findByStatus(StatusDoacao status);
    List<DoacaoModel> findByDoador(UsuarioModel doador);
    long countByStatus(StatusDoacao status);


}
