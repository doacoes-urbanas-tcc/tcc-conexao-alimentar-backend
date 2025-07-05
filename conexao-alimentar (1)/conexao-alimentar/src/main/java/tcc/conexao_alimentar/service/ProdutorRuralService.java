package tcc.conexao_alimentar.service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tcc.conexao_alimentar.DTO.ProdutorRuralRequestDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.mapper.ProdutorRuralMapper;
import tcc.conexao_alimentar.model.ProdutorRuralModel;
import tcc.conexao_alimentar.repository.ProdutorRuralRepository;

@Service
@RequiredArgsConstructor
public class ProdutorRuralService {

    private final ProdutorRuralRepository produtorRuralRepository;
    private final PasswordEncoder passwordEncoder;

    public void cadastrar(ProdutorRuralRequestDTO dto) {
        ProdutorRuralModel model = ProdutorRuralMapper.toEntity(dto);
        model.setSenha(passwordEncoder.encode(dto.getSenha()));
        model.setTipoUsuario(TipoUsuario.PRODUTOR_RURAL);
        model.setAtivo(false);
        produtorRuralRepository.save(model);
    }

}
