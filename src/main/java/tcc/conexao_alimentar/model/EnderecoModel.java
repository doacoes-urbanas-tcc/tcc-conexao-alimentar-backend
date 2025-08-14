package tcc.conexao_alimentar.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoModel {

    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private Double latitude;
    private Double longitude;

}
