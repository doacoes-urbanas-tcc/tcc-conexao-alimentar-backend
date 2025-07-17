package tcc.conexao_alimentar.service;

import java.io.IOException;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final FileUploadService fileUploadService;

    public void cadastrarVeiculo(VeiculoRequestDTO dto, MultipartFile cnh) throws IOException {
    VoluntarioModel voluntario = voluntarioRepository.findById(dto.getVoluntarioId())
        .orElseThrow(() -> new RegraDeNegocioException("Voluntário não encontrado"));

    if (!voluntario.getSetorAtuacao().equals(SetorAtuacao.TRANSPORTE)) {
        throw new RegraDeNegocioException("Este voluntário não é do setor de transporte. Cadastro de veículo não permitido.");
    }

    if (voluntario.getVeiculo() != null) {
        throw new RegraDeNegocioException("Este voluntário já possui um veículo cadastrado.");
    }

    String caminhoCNH = fileUploadService.salvarArquivo(cnh, "cnhs");

    VeiculoModel veiculo = VeiculoMapper.toEntity(dto, voluntario);
    veiculo.setUrlCnh(caminhoCNH);

    voluntario.setVeiculo(veiculo);
    veiculoRepository.save(veiculo);
    }



}
