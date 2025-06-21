package tcc.conexao_alimentar.service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tcc.conexao_alimentar.DTO.OngCadastroDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.mapper.OngMapper;
import tcc.conexao_alimentar.model.OngModel;
import tcc.conexao_alimentar.repository.OngRepository;

@Service
@RequiredArgsConstructor
public class OngService {

    private final OngRepository ongRepository;
    private final PasswordEncoder passwordEncoder;

    public void cadastrar(OngCadastroDTO dto) {
        OngModel model = OngMapper.toEntity(dto);
        model.setSenha(passwordEncoder.encode(dto.getSenha()));
        model.setTipoUsuario(TipoUsuario.ONG);
        model.setAtivo(false);
        ongRepository.save(model);
    }

}
