package tcc.conexao_alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.PessoaFisicaModel;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisicaModel,Long> {

    boolean existsByEmail(String email);


}
