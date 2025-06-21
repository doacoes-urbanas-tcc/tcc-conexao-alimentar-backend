package tcc.conexao_alimentar.service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tcc.conexao_alimentar.DTO.ComercioCadastroDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.mapper.ComercioMapper;
import tcc.conexao_alimentar.model.ComercioModel;
import tcc.conexao_alimentar.repository.ComercioRepository;

@Service
@RequiredArgsConstructor
public class ComercioService {

    private final ComercioRepository comercioRepository;
    private final PasswordEncoder passwordEncoder;

    public void cadastrar(ComercioCadastroDTO dto) {
        ComercioModel model = ComercioMapper.toEntity(dto);
        model.setSenha(passwordEncoder.encode(dto.getSenha()));
        model.setTipoUsuario(TipoUsuario.COMERCIO);
        model.setAtivo(false);
        comercioRepository.save(model);
    }

}
