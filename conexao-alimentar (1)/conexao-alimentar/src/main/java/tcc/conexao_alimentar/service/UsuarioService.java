package tcc.conexao_alimentar.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.ComercioCadastroDTO;
import tcc.conexao_alimentar.DTO.ComercioResponseDTO;
import tcc.conexao_alimentar.DTO.OngCadastroDTO;
import tcc.conexao_alimentar.DTO.OngResponseDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaCadastroDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaResponseDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralCadastroDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralResponseDTO;
import tcc.conexao_alimentar.DTO.VoluntarioCadastroDTO;
import tcc.conexao_alimentar.DTO.VoluntarioResponseDTO;
import tcc.conexao_alimentar.mapper.ComercioMapper;
import tcc.conexao_alimentar.mapper.OngMapper;
import tcc.conexao_alimentar.mapper.PessoaFisicaMapper;
import tcc.conexao_alimentar.mapper.ProdutorRuralMapper;
import tcc.conexao_alimentar.mapper.VoluntarioMapper;
import tcc.conexao_alimentar.model.ComercioModel;
import tcc.conexao_alimentar.model.OngModel;
import tcc.conexao_alimentar.model.PessoaFisicaModel;
import tcc.conexao_alimentar.model.ProdutorRuralModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.model.VoluntarioModel;
import tcc.conexao_alimentar.repository.ComercioRepository;
import tcc.conexao_alimentar.repository.OngRepository;
import tcc.conexao_alimentar.repository.PessoaFisicaRepository;
import tcc.conexao_alimentar.repository.ProdutorRuralRepository;
import tcc.conexao_alimentar.repository.UsuarioRepository;
import tcc.conexao_alimentar.repository.VoluntarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

      private final UsuarioRepository usuarioRepository;

    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final ComercioRepository comercioRepository;
    private final ProdutorRuralRepository produtorRuralRepository;
    private final OngRepository ongRepository;

    public PessoaFisicaResponseDTO cadastrarPessoaFisica(PessoaFisicaCadastroDTO dto) {
        validarEmail(dto.getEmail());
        PessoaFisicaModel pf = PessoaFisicaMapper.toEntity(dto);
        pf.setAtivo(true);
        return PessoaFisicaMapper.toResponse(pessoaFisicaRepository.save(pf));
    }

    public VoluntarioResponseDTO cadastrarVoluntario(VoluntarioCadastroDTO dto) {
        validarEmail(dto.getEmail());
        VoluntarioModel v = VoluntarioMapper.toEntity(dto);
        v.setAtivo(true);
        return VoluntarioMapper.toResponse(voluntarioRepository.save(v));
    }

    public ComercioResponseDTO cadastrarComercio(ComercioCadastroDTO dto) {
        validarEmail(dto.getEmail());
        ComercioModel c = ComercioMapper.toEntity(dto);
        c.setAtivo(true);
        return ComercioMapper.toResponse(comercioRepository.save(c));
    }

    public ProdutorRuralResponseDTO cadastrarProdutorRural(ProdutorRuralCadastroDTO dto) {
        validarEmail(dto.getEmail());
        ProdutorRuralModel pr = ProdutorRuralMapper.toEntity(dto);
        pr.setAtivo(true);
        return ProdutorRuralMapper.toResponse(produtorRuralRepository.save(pr));
    }

    public OngResponseDTO cadastrarOng(OngCadastroDTO dto) {
        validarEmail(dto.getEmail());
        OngModel ong = OngMapper.toEntity(dto);
        ong.setAtivo(false);
        return OngMapper.toResponse(ongRepository.save(ong));
    }

    public Optional<UsuarioModel> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    private void validarEmail(String email) {
        boolean jaExiste = usuarioRepository.existsByEmail(email);
        if (jaExiste) {
            throw new RuntimeException("E-mail já cadastrado.");
        }
    }
}
