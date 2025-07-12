package tcc.conexao_alimentar.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "veiculo")
public class VeiculoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "placa", nullable = false, unique = true)
    private String placa;
    @Column(name = "modelo", nullable = false)
    private String modelo;
    @Column(name = "cor", nullable = false)
    private String cor;
    @Column(name = "capacidade_carga", nullable = false)
    private String capacidadeCarga;

    @OneToOne
    @JoinColumn(name = "voluntario_id")
    @JsonBackReference
    private VoluntarioModel voluntario;

}
