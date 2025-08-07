package tcc.conexao_alimentar.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Query("SELECT COUNT(d) FROM DoacaoModel d WHERE d.usuario.id = :usuarioId")
    int contarDoacoesPorUsuario(@Param("usuarioId") Long usuarioId);

    @Query("SELECT COUNT(DISTINCT d.reserva.ong.id) FROM DoacaoModel d WHERE d.usuario.id = :usuarioId AND d.reserva IS NOT NULL")
    int contarOngsBeneficiadas(@Param("usuarioId") Long usuarioId);

    @Query("SELECT AVG(a.nota) FROM AvaliacaoModel a WHERE a.avaliado.id = :usuarioId")
    Double calcularMediaAvaliacoes(@Param("usuarioId") Long usuarioId);

    @Query("SELECT d FROM DoacaoModel d WHERE d.usuario.id = :usuarioId ORDER BY d.data DESC LIMIT 1")
    Optional<DoacaoModel> buscarUltimaDoacao(@Param("usuarioId") Long usuarioId);


}
