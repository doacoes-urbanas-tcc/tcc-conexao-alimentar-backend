package tcc.conexao_alimentar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.RecuperarSenhaToken;

public interface RecuperarSenhaTokenRepository  extends JpaRepository<RecuperarSenhaToken, Long>{

    Optional<RecuperarSenhaToken> findByToken(String token);

}
