package tcc.conexao_alimentar.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tcc.conexao_alimentar.enums.StatusDoacao;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.UsuarioModel;

public interface DoacaoRepository extends JpaRepository<DoacaoModel,Long> {
    List<DoacaoModel> findByStatus(StatusDoacao status);
    List<DoacaoModel> findByDoador(UsuarioModel doador);
    long countByStatus(StatusDoacao status);
    @Query("SELECT d FROM DoacaoModel d WHERE d.status = 'CONCLUIDA' AND d.dataCadastro BETWEEN :inicio AND :fim")
    List<DoacaoModel> findDoacoesConcluidasNoPeriodo(@Param("inicio") LocalDateTime inicio,@Param("fim") LocalDateTime fim);


}
