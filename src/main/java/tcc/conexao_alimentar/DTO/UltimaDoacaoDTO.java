package main.java.tcc.conexao_alimentar.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UltimaDoacaoDTO {
    private String data;
    private String itens;
    private String destino;
    private String status;

}
