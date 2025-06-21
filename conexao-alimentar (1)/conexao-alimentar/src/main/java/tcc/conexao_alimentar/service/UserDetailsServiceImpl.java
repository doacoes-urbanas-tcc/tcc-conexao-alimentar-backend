package tcc.conexao_alimentar.service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tcc.conexao_alimentar.repository.*;

import tcc.conexao_alimentar.security.UserDetailsImpl;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PessoaFisicaRepository pfRepo;
    private final VoluntarioRepository volRepo;
    private final ComercioRepository comRepo;
    private final ProdutorRuralRepository prRepo;
    private final OngRepository ongRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Procurando email: " + email);
        return pfRepo.findByEmail(email).map(UserDetailsImpl::new)
            .or(() -> volRepo.findByEmail(email).map(UserDetailsImpl::new))
            .or(() -> comRepo.findByEmail(email).map(UserDetailsImpl::new))
            .or(() -> prRepo.findByEmail(email).map(UserDetailsImpl::new))
            .or(() -> ongRepo.findByEmail(email).map(UserDetailsImpl::new))
            .orElseThrow(() -> new UsernameNotFoundException("Usuário com e-mail não encontrado: " + email));
    }



}
