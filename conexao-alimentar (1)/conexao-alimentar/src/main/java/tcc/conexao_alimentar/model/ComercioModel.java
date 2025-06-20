package tcc.conexao_alimentar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcc.conexao_alimentar.enums.TipoComercio;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ComercioModel extends UsuarioModel {

    private String cnpj;
    private String nomeFantasia;
    @Enumerated(EnumType.STRING)
    private TipoComercio tipoComercio;
    

}
