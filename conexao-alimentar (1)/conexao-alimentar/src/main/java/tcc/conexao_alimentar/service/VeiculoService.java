package tcc.conexao_alimentar.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.VeiculoRequestDTO;
import tcc.conexao_alimentar.enums.SetorAtuacao;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.VeiculoMapper;
import tcc.conexao_alimentar.model.VeiculoModel;
import tcc.conexao_alimentar.model.VoluntarioModel;
import tcc.conexao_alimentar.repository.VeiculoRepository;
import tcc.conexao_alimentar.repository.VoluntarioRepository;

@RequiredArgsConstructor
@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final VoluntarioRepository voluntarioRepository;

    public void cadastrarVeiculo(VeiculoRequestDTO dto) {
        VoluntarioModel voluntario = voluntarioRepository.findById(dto.getVoluntarioId())
            .orElseThrow(() -> new RegraDeNegocioException("Voluntário não encontrado"));

        if (!voluntario.getSetorAtuacao().equals(SetorAtuacao.TRANSPORTE)) {
            throw new RegraDeNegocioException("Este voluntário não é do setor de transporte. Cadastro de veículo não permitido.");
        }

        if (voluntario.getVeiculo() != null) {
            throw new RegraDeNegocioException("Este voluntário já possui um veículo cadastrado.");
        }

        VeiculoModel veiculo = VeiculoMapper.toEntity(dto, voluntario);

        voluntario.setVeiculo(veiculo);
        veiculoRepository.save(veiculo);
    }

}
