package tcc.conexao_alimentar.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcc.conexao_alimentar.enums.SetorAtuacao;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voluntario")
public class VoluntarioModel extends UsuarioModel{
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;
    @Column(name = "documento_comprovante", nullable = false)
    private String documentoComprovante;
    @Column(name = "seto_atuacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private SetorAtuacao setorAtuacao;

    @OneToOne(mappedBy = "voluntario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private VeiculoModel veiculo;


}
