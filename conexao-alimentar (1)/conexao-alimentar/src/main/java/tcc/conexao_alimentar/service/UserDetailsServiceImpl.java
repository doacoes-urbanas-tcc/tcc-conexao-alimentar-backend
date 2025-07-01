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

      private final AdministradorRepository adminRepo;
    private final UsuarioRepository usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Procurando email: " + email);

        return adminRepo.findByEmail(email)
            .map(UserDetailsImpl::new)
            .or(() -> usuarioRepo.findByEmail(email).map(UserDetailsImpl::new))
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }
    



}
