package tcc.conexao_alimentar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produtor_rural")
public class ProdutorRuralModel extends UsuarioModel{

    private String numeroRegistroRural;


}
