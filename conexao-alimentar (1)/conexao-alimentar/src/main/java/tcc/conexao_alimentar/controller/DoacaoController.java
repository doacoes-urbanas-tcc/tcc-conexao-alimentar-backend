package tcc.conexao_alimentar.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tcc.conexao_alimentar.DTO.DoacaoRequestDTO;
import tcc.conexao_alimentar.DTO.DoacaoResponseDTO;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.model.ReservaModel;
import tcc.conexao_alimentar.repository.ReservaRepository;
import tcc.conexao_alimentar.service.DoacaoService;
import tcc.conexao_alimentar.service.FileUploadService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doacoes")
@RequiredArgsConstructor
@Tag(name = "Doações", description = "Endpoints para gerenciamento de doações")
public class DoacaoController {

    private final DoacaoService service;
    private final FileUploadService fileUploadService;
    private final ReservaRepository reservaRepository;
    
    @Operation(summary = "Cadastrar nova doação",description = "Permite que um usuário do tipo COMERCIO, PRODUTOR_RURAL ou PESSOA_FISICA cadastre uma nova doação.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Doação cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarDoacao(
    @RequestPart("dto") DoacaoRequestDTO dto,@RequestPart("file") MultipartFile file) throws IOException {
    if (file.isEmpty()) {
        throw new RegraDeNegocioException("Imagem é obrigatória.");
    }

    String url = fileUploadService.salvarArquivo(file, "doacoes");
    dto.setUrlImagem(url); 

    service.cadastrar(dto,file);

    return ResponseEntity.ok("Doação cadastrada com sucesso!");
    }


    @Operation(summary = "Listar todas as doações",description = "Retorna uma lista de todas as doações disponíveis. Somente ONG, VOLUNTÁRIO ou ADMIN podem acessar.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de doações retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @GetMapping
    @PreAuthorize("hasRole('ONG') or hasRole('VOLUNTARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<DoacaoResponseDTO>> listarTodas() {
        List<DoacaoResponseDTO> lista = service.listarTodas();
        return ResponseEntity.ok(lista);
    }

    
    @Operation(summary = "Listar doação por id",description = "Retorna a doação buscada. Somente ONG, VOLUNTÁRIO ou ADMIN podem acessar.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Doação retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ONG') or hasRole('VOLUNTARIO') or hasRole('ADMIN')")
    public ResponseEntity<DoacaoResponseDTO> buscarPorId(@PathVariable Long id) {
    DoacaoResponseDTO dto = service.buscarPorId(id);
    return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Deleta doação por id",description = "Deleta a doação selecionada. Somente COMERCIO, PRODUTOR_RURAL, PESSOA_FISICA e ADMIN podem acessar.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Doação removida com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),

    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COMERCIO') or hasRole('PRODUTOR_RURAL') or hasRole('PESSOA_FISICA') or hasRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.removerDoacao(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Valida o qrCode",description = "Endpoint para que os doadores consigam validar o QR Code quando a ONG chegar para retirar a doação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "QR Code validado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),

    })
    @PostMapping("/validar-qr/{doacaoId}")
    @PreAuthorize("hasRole('COMERCIO') or hasRole('PRODUTOR_RURAL')")
    public ResponseEntity<Map<String, Long>> validarQrCode(@PathVariable Long doacaoId) {
    service.validarQrCode(doacaoId);
    ReservaModel reserva = reservaRepository.findByDoacaoId(doacaoId)
        .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada para essa doação."));

    Map<String, Long> response = new HashMap<>();
    response.put("idReserva", reserva.getId());
    return ResponseEntity.ok(response);
   }

    @Operation(summary = "Listar doações do doador logado", description = "Retorna uma lista das doações cadastradas pelo doador logado.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
    @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
   })
    @GetMapping("/minhas-doacoes")
    @PreAuthorize("hasRole('COMERCIO') or hasRole('PRODUTOR_RURAL') or hasRole('PESSOA_FISICA')")
    public ResponseEntity<List<DoacaoResponseDTO>> listarMinhasDoacoes() {
    List<DoacaoResponseDTO> minhasDoacoes = service.listarDoacoesDoDoador();
    return ResponseEntity.ok(minhasDoacoes);
    }




    
}


    

