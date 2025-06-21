package tcc.conexao_alimentar.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.VoluntarioCadastroDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.mapper.VoluntarioMapper;
import tcc.conexao_alimentar.model.VoluntarioModel;
import tcc.conexao_alimentar.repository.VoluntarioRepository;

@Service
@RequiredArgsConstructor
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void cadastrar(VoluntarioCadastroDTO dto) {
        VoluntarioModel model = VoluntarioMapper.toEntity(dto);
        model.setSenha(passwordEncoder.encode(dto.getSenha()));
        model.setTipoUsuario(TipoUsuario.VOLUNTARIO);
        model.setAtivo(false);
        voluntarioRepository.save(model);
    }

}
