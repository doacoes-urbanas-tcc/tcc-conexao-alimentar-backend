package tcc.conexao_alimentar.security;

import tcc.conexao_alimentar.model.UsuarioModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
public class SecurityUtils {

    public static UsuarioModel getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            return userDetails.getUsuario();
        }

        throw new RuntimeException("Usuário não autenticado.");
    }


}
