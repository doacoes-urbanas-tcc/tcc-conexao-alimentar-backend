package tcc.conexao_alimentar.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.RelatorioDoacaoDTO;
import tcc.conexao_alimentar.DTO.RelatorioDoacaoStatusDTO;
import tcc.conexao_alimentar.DTO.RelatorioDoacoesPorDoadorDTO;
import tcc.conexao_alimentar.DTO.RelatorioDoacoesPorReceptorDTO;
import tcc.conexao_alimentar.DTO.RelatorioEfetividadeDTO;
import tcc.conexao_alimentar.DTO.RelatorioQuantidadeTotalDTO;
import tcc.conexao_alimentar.enums.StatusDoacao;
import tcc.conexao_alimentar.model.ComercioModel;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.OngModel;
import tcc.conexao_alimentar.model.ProdutorRuralModel;
import tcc.conexao_alimentar.model.ReservaModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.DoacaoRepository;


@Service
@RequiredArgsConstructor
public class RelatorioService {
    private final DoacaoRepository doacaoRepository;

     public List<RelatorioDoacaoDTO> gerarRelatorioMensal(int ano, int mes) {
        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());

        List<DoacaoModel> doacoesConcluidas = doacaoRepository.findDoacoesConcluidasNoPeriodo(
            StatusDoacao.CONCLUIDA,
            inicio.atStartOfDay(),
            fim.atTime(23, 59, 59)
        );

        return doacoesConcluidas.stream()
            .map(doacao -> {
                ReservaModel reserva = doacao.getReserva();

                String nomeOng = "-";
                String cnpjOng = "-";

                if (reserva != null && reserva.getReceptor() != null) {
                    UsuarioModel receptor = reserva.getReceptor();
                    nomeOng = receptor.getNome();

                    if (receptor instanceof OngModel ong) {
                        cnpjOng = ong.getCnpj();
                    } else if (receptor instanceof ComercioModel comercio) {
                        cnpjOng = comercio.getCnpj();
                    } else if (receptor instanceof ProdutorRuralModel rural) {
                        cnpjOng = rural.getNumeroRegistroRural();
                    }
                }

                UsuarioModel doador = doacao.getDoador();
                String cnpjDoador = "-";

                if (doador instanceof ComercioModel comercio) {
                    cnpjDoador = comercio.getCnpj();
                } else if (doador instanceof OngModel ong) {
                    cnpjDoador = ong.getCnpj();
                } else if (doador instanceof ProdutorRuralModel rural) {
                    cnpjDoador = rural.getNumeroRegistroRural();
                }

                LocalDate dataConclusao = doacao.getDataConclusao() != null
                    ? doacao.getDataConclusao().toLocalDate()
                    : doacao.getDataCadastro().toLocalDate();

                return new RelatorioDoacaoDTO(
                    doacao.getId(),
                    doacao.getNomeAlimento(),
                    doacao.getUnidadeMedida().name(),
                    doacao.getQuantidade(),
                    dataConclusao,
                    doacao.getCategoria().name(),
                    doador.getNome(),
                    cnpjDoador,
                    nomeOng,
                    cnpjOng
                );
            })
            .collect(Collectors.toList());
    }

    public List<RelatorioDoacaoStatusDTO> gerarRelatorioPendentesOuExpiradas() {
    List<DoacaoModel> doacoes = doacaoRepository.findAll().stream()
        .filter(d -> d.getStatus() == StatusDoacao.PENDENTE || d.getDataExpiracao().isBefore(OffsetDateTime.now()))
        .collect(Collectors.toList());

    return doacoes.stream().map(doacao -> {
        UsuarioModel doador = doacao.getDoador();
        String cnpj = "-";
        if (doador instanceof ComercioModel) {
            cnpj = ((ComercioModel) doador).getCnpj();
        } else if (doador instanceof OngModel) {
            cnpj = ((OngModel) doador).getCnpj();
        } else if (doador instanceof ProdutorRuralModel) {
            cnpj = ((ProdutorRuralModel) doador).getNumeroRegistroRural();
        }

        return new RelatorioDoacaoStatusDTO(
            doacao.getId(),
            doacao.getNomeAlimento(),
            doacao.getCategoria().name(),
            doacao.getStatus(),
            doacao.getDataExpiracao().toLocalDate(),
            doador.getNome(),
            cnpj
        );
    }).collect(Collectors.toList());
    }

    public List<RelatorioQuantidadeTotalDTO> gerarRelatorioQuantidadeTotal() {
    return doacaoRepository.findAll().stream()
        .filter(d -> d.getStatus() == StatusDoacao.CONCLUIDA)
        .collect(Collectors.groupingBy(
            d -> d.getCategoria().name() + "|" + d.getUnidadeMedida().name(),
            Collectors.summingDouble(DoacaoModel::getQuantidade)
        ))
        .entrySet().stream()
        .map(e -> {
            String[] parts = e.getKey().split("\\|");
            String categoria = parts[0];
            String unidade = parts[1];
            return new RelatorioQuantidadeTotalDTO(categoria, e.getValue(), unidade);
        })
        .collect(Collectors.toList());
    }
    public List<RelatorioDoacoesPorDoadorDTO> gerarRelatorioPorDoador() {
    return doacaoRepository.findAll().stream()
        .filter(d -> d.getStatus() == StatusDoacao.CONCLUIDA)
        .collect(Collectors.groupingBy(
            d -> {
                UsuarioModel doador = d.getDoador();
                String nome = doador.getNome();
                String cnpj = "-";

                if (doador instanceof ComercioModel comercio) cnpj = comercio.getCnpj();
                else if (doador instanceof OngModel ong) cnpj = ong.getCnpj();
                else if (doador instanceof ProdutorRuralModel rural) cnpj = rural.getNumeroRegistroRural();

                return nome + "_" + cnpj + "_" + d.getCategoria().name() + "_" + d.getUnidadeMedida().name();
            },
            Collectors.summingDouble(DoacaoModel::getQuantidade)
        ))
        .entrySet().stream()
        .map(entry -> {
            String[] parts = entry.getKey().split("_");
            return new RelatorioDoacoesPorDoadorDTO(
                parts[0], parts[1], parts[2], entry.getValue(), parts[3]
            );
        })
        .collect(Collectors.toList());
    }

    public List<RelatorioDoacoesPorReceptorDTO> gerarRelatorioPorReceptor() {
    return doacaoRepository.findAll().stream()
        .filter(d -> d.getStatus() == StatusDoacao.CONCLUIDA && d.getReserva() != null)
        .collect(Collectors.groupingBy(
            d -> {
                UsuarioModel receptor = d.getReserva().getReceptor();
                String nome = receptor.getNome();
                String cnpj = "-";

                if (receptor instanceof ComercioModel comercio) cnpj = comercio.getCnpj();
                else if (receptor instanceof OngModel ong) cnpj = ong.getCnpj();
                else if (receptor instanceof ProdutorRuralModel rural) cnpj = rural.getNumeroRegistroRural();

                return nome + "_" + cnpj + "_" + d.getCategoria().name() + "_" + d.getUnidadeMedida().name();
            },
            Collectors.summingDouble(DoacaoModel::getQuantidade)
        ))
        .entrySet().stream()
        .map(entry -> {
            String[] parts = entry.getKey().split("_");
            return new RelatorioDoacoesPorReceptorDTO(
                parts[0], parts[1], parts[2], entry.getValue(), parts[3]
            );
        })
        .collect(Collectors.toList());
    }

    public List<RelatorioEfetividadeDTO> gerarRelatorioEfetividade() {
    List<DoacaoModel> todas = doacaoRepository.findAll();

    return todas.stream()
        .collect(Collectors.groupingBy(
            d -> d.getCategoria().name(),
            Collectors.collectingAndThen(Collectors.toList(), lista -> {
                long concluidas = lista.stream().filter(d -> d.getStatus() == StatusDoacao.CONCLUIDA).count();
                long total = lista.size();
                double percentual = total == 0 ? 0.0 : ((double) concluidas / total) * 100;
                return new RelatorioEfetividadeDTO(lista.get(0).getCategoria().name(), total, concluidas, percentual);
            })
        ))
        .values().stream().collect(Collectors.toList());
}


    







}
