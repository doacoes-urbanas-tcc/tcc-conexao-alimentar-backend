package tcc.conexao_alimentar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.RespostaTaskModel;
import tcc.conexao_alimentar.model.UsuarioModel;

public interface RespostaTaskTiRepository  extends JpaRepository<RespostaTaskModel, Long> {
    List<RespostaTaskModel> findByTaskTiId(Long taskId);
    boolean existsByTaskTiIdAndVoluntarioId(Long taskId, Long voluntarioId);
    List<RespostaTaskModel> findByVoluntarioId(Long voluntarioId);
    List<RespostaTaskModel> findByVoluntario(UsuarioModel voluntario);

}
