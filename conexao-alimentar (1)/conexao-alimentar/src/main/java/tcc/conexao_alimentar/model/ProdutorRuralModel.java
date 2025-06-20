package tcc.conexao_alimentar.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProdutorRuralModel extends UsuarioModel{

    private String numeroRegistroRural;


}
