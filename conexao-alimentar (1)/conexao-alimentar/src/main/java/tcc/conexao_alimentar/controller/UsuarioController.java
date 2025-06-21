package tcc.conexao_alimentar.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.model.ComercioModel;
import tcc.conexao_alimentar.model.OngModel;
import tcc.conexao_alimentar.model.PessoaFisicaModel;
import tcc.conexao_alimentar.model.ProdutorRuralModel;
import tcc.conexao_alimentar.model.VoluntarioModel;
import tcc.conexao_alimentar.repository.ComercioRepository;
import tcc.conexao_alimentar.repository.OngRepository;
import tcc.conexao_alimentar.repository.PessoaFisicaRepository;
import tcc.conexao_alimentar.repository.ProdutorRuralRepository;
import tcc.conexao_alimentar.repository.VoluntarioRepository;

@RestController
@RequestMapping("/admin/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final PessoaFisicaRepository pfRepo;
    private final VoluntarioRepository volRepo;
    private final ComercioRepository comRepo;
    private final ProdutorRuralRepository prRepo;
    private final OngRepository ongRepo;

    @GetMapping("/pendentes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarPendentes() {
        Map<String, Object> pendentes = new HashMap<>();
        pendentes.put("pessoaFisica", pfRepo.findByAtivoFalse());
        pendentes.put("voluntarios", volRepo.findByAtivoFalse());
        pendentes.put("comercios", comRepo.findByAtivoFalse());
        pendentes.put("produtoresRurais", prRepo.findByAtivoFalse());
        pendentes.put("ongs", ongRepo.findByAtivoFalse());
        return ResponseEntity.ok(pendentes);
    }
    @PatchMapping("/aprovar/{tipo}/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> aprovarUsuario(@PathVariable String tipo,@PathVariable Long id) {
    switch (tipo.toLowerCase()) {
        case "pessoa-fisica" -> {
            PessoaFisicaModel pf = pfRepo.findById(id).orElse(null);
            if (pf == null) return ResponseEntity.notFound().build();
            pf.setAtivo(true);
            pfRepo.save(pf);
            return ResponseEntity.ok("Pessoa Física aprovada!");
        }
        case "voluntario" -> {
            VoluntarioModel vol = volRepo.findById(id).orElse(null);
            if (vol == null) return ResponseEntity.notFound().build();
            vol.setAtivo(true);
            volRepo.save(vol);
            return ResponseEntity.ok("Voluntário aprovado!");
        }
        case "comercio" -> {
            ComercioModel com = comRepo.findById(id).orElse(null);
            if (com == null) return ResponseEntity.notFound().build();
            com.setAtivo(true);
            comRepo.save(com);
            return ResponseEntity.ok("Comércio aprovado!");
        }
        case "produtor-rural" -> {
            ProdutorRuralModel pr = prRepo.findById(id).orElse(null);
            if (pr == null) return ResponseEntity.notFound().build();
            pr.setAtivo(true);
            prRepo.save(pr);
            return ResponseEntity.ok("Produtor Rural aprovado!");
        }
        case "ong" -> {
            OngModel ong = ongRepo.findById(id).orElse(null);
            if (ong == null) return ResponseEntity.notFound().build();
            ong.setAtivo(true);
            ongRepo.save(ong);
            return ResponseEntity.ok("ONG aprovada!");
        }
        default -> {
            return ResponseEntity.badRequest().body("Tipo de usuário inválido.");
        }
    }
}


}
