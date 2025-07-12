package tcc.conexao_alimentar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
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
@Table(name = "comercio")
public class ComercioModel extends UsuarioModel {
    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;
    @Column(name = "nome_fantasia", nullable = false)
    private String nomeFantasia;
    @Column(name = "tipo_comercio", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoComercio tipoComercio;
    

}
