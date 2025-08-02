package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.PessoaFisicaRequestDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaResponseDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.model.PessoaFisicaModel;

public class PessoaFisicaMapper {

  public static PessoaFisicaModel toEntity(PessoaFisicaRequestDTO dto) {
        PessoaFisicaModel pf = new PessoaFisicaModel();
        pf.setNome(dto.getNome());
        pf.setEmail(dto.getEmail());
        pf.setSenha(dto.getSenha());
        pf.setTelefone(dto.getTelefone());
        pf.setCpf(dto.getCpf());
        pf.setDocumentoComprovante(dto.getDocumentoComprovante());
        pf.setEndereco(EnderecoMapper.toEntity(dto.getEndereco()));
        pf.setTipoUsuario(TipoUsuario.PESSOA_FISICA);
        pf.setFotoUrl(dto.getFotoUrl());
        return pf;
    }

    public static PessoaFisicaResponseDTO toResponse(PessoaFisicaModel pf) {
        return new PessoaFisicaResponseDTO(
            pf.getId(), 
            pf.getNome(), 
            pf.getEmail(), 
            pf.getTelefone(),
            pf.getCpf(), 
            pf.getDocumentoComprovante(),
            EnderecoMapper.toDTO(pf.getEndereco()), 
            pf.getTipoUsuario(),
            pf.getFotoUrl(),
            pf.getJustificativaReprovacao(),
            pf.isAtivo()
        );
    }



}
