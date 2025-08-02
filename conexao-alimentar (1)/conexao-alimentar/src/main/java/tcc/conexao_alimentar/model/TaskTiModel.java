package tcc.conexao_alimentar.model;

import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "task_ti")
public class TaskTiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String linkRepositorio; 
    private boolean fechada = false;

    @ElementCollection
    private List<String> tags;

    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "taskTi", cascade = jakarta.persistence.CascadeType.ALL)
    private List<RespostaTaskModel> respostas;

}
