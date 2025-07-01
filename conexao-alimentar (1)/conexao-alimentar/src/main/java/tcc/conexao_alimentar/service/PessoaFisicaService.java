package tcc.conexao_alimentar.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.PessoaFisicaCadastroDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.mapper.PessoaFisicaMapper;
import tcc.conexao_alimentar.model.PessoaFisicaModel;
import tcc.conexao_alimentar.repository.PessoaFisicaRepository;

@Service
@RequiredArgsConstructor
public class PessoaFisicaService {

    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final PasswordEncoder passwordEncoder;

    public void cadastrar(PessoaFisicaCadastroDTO dto) {
        PessoaFisicaModel model = PessoaFisicaMapper.toEntity(dto);

        model.setSenha(passwordEncoder.encode(dto.getSenha()));

        model.setTipoUsuario(TipoUsuario.PESSOA_FISICA);
        model.setAtivo(false);

        pessoaFisicaRepository.save(model);
    }
    

    

}


