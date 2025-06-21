package tcc.conexao_alimentar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.PessoaFisicaModel;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisicaModel,Long> {

    Optional<PessoaFisicaModel> findByEmail(String email);
    boolean existsByEmail(String email); 

}
