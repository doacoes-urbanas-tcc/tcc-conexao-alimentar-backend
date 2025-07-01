package tcc.conexao_alimentar.service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.*;

import tcc.conexao_alimentar.security.UserDetailsImpl;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

       private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Procurando email: " + email);

        UsuarioModel usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário com e-mail não encontrado: " + email));

        return new UserDetailsImpl(usuario);
    }




}
