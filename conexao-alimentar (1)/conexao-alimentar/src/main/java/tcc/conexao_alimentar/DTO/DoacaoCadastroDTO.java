package tcc.conexao_alimentar.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class DoacaoCadastroDTO {

    private String descricao;
    private LocalDate dataCadastro;
    private String categoria;
    private Integer quantidade;
    private String localizacao;




}
