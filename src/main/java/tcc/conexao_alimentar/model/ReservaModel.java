package tcc.conexao_alimentar.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tcc.conexao_alimentar.enums.StatusReserva;

@Entity
@Getter
@Setter
@Table(name = "reserva")
public class ReservaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "doacao_id")
    private DoacaoModel doacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "receptor_id")
    private UsuarioModel receptor; 

    @Column(name = "data_reserva", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dataReserva;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusReserva status;

    @Column(name = "justificativa")
    private String justificativaCancelamento;

    @Column(name = "url_qrcode")
    private String urlQrCode;

    @Column(name = "avaliacao_doador")
    private Boolean avaliacaoFeitaPeloDoador = false;

    @Column(name = "avaliacao_receptor")
    private Boolean avaliacaoFeitaPeloReceptor = false;
    @Column(name = "data_hora_expiracao", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dataHoraExpiracao;

    


}
