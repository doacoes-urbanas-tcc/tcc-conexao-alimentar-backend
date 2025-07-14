package tcc.conexao_alimentar.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.DoacaoRepository;
import tcc.conexao_alimentar.repository.UsuarioRepository;
import tcc.conexao_alimentar.DTO.DoacaoRequestDTO;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class DoacaoServiceTest {

    @Mock
    private DoacaoRepository doacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private DoacaoService doacaoService;

    private void mockAuth(String email) {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(email, null)
        );
    }

    @Test
    @DisplayName("Deve cadastrar doação com sucesso")
    void testCadastrarComSucesso() {
        DoacaoRequestDTO dto = new DoacaoRequestDTO(
            "Arroz", "QUILOGRAMA", 5.0, LocalDate.now().plusDays(2), "Doação de arroz", "GRAOS"
        );
        UsuarioModel doador = mock(UsuarioModel.class);
        when(usuarioRepository.findByEmail("teste@email.com"))
            .thenReturn(Optional.of(doador));
        mockAuth("teste@email.com");

        doacaoService.cadastrar(dto);

        verify(doacaoRepository, times(1)).save(any(DoacaoModel.class));
    }

    @Test
    @DisplayName("Deve lançar erro se nome do alimento for vazio")
    void testNomeAlimentoVazio() {
        DoacaoRequestDTO dto = new DoacaoRequestDTO("", "KG", 1.0, LocalDate.now().plusDays(1), "desc", "GRAOS");
        UsuarioModel doador = mock(UsuarioModel.class);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(doador));
        mockAuth("x@email.com");

        RegraDeNegocioException ex = assertThrows(
            RegraDeNegocioException.class,
            () -> doacaoService.cadastrar(dto)
        );
        assertTrue(ex.getMessage().contains("nome"));
    }
    
    @Test
    @DisplayName("Deve lançar erro se quantidade for menor ou igual a zero")
    void testQuantidadeZero() {
        DoacaoRequestDTO dto = new DoacaoRequestDTO("Arroz", "KG", 0.0, LocalDate.now().plusDays(1), "desc", "GRAOS");
        UsuarioModel doador = mock(UsuarioModel.class);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(doador));
        mockAuth("x@email.com");

        RegraDeNegocioException ex = assertThrows(
            RegraDeNegocioException.class,
            () -> doacaoService.cadastrar(dto)
        );
        assertTrue(ex.getMessage().contains("quantidade"));
    }

    @Test
    @DisplayName("Deve lançar erro se data de validade for anterior")
    void testDataValidadeAnterior() {
        tcc.conexao_alimentar.DTO.DoacaoRequestDTO dto = new tcc.conexao_alimentar.DTO.DoacaoRequestDTO("Arroz", "KG", 2.0, LocalDate.now().minusDays(1), "desc", "GRAOS");
        UsuarioModel doador = mock(UsuarioModel.class);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(doador));
        mockAuth("x@email.com");

        RegraDeNegocioException ex = assertThrows(
            RegraDeNegocioException.class,
            () -> doacaoService.cadastrar(dto)
        );
        assertTrue(ex.getMessage().contains("validade"));
    }

    
    @Test
    @DisplayName("Deve listar todas as doações")
    void testListarTodas() {
        when(doacaoRepository.findAll()).thenReturn(Collections.emptyList());
        List<?> lista = doacaoService.listarTodas();
        assertNotNull(lista);
    }

    @Test
    @DisplayName("Deve buscar por ID com sucesso")
    void testBuscarPorId() {
        DoacaoModel doacao = new DoacaoModel();
        doacao.setId(1L);
        doacao.setNomeAlimento("Arroz");
        doacao.setDescricao("desc");
        doacao.setDataCadastro(java.time.LocalDateTime.now());
        doacao.setDataExpiracao(java.time.LocalDateTime.now().plusHours(48));
        doacao.setQuantidade(2.0);
        doacao.setDataValidade(LocalDate.now().plusDays(1));
        doacao.setCategoria(tcc.conexao_alimentar.enums.CategoriaAlimento.GRAOS);
        doacao.setStatus(tcc.conexao_alimentar.enums.StatusDoacao.PENDENTE);

        when(doacaoRepository.findById(1L)).thenReturn(Optional.of(doacao));
        var resp = doacaoService.buscarPorId(1L);
        assertEquals("Arroz", resp.getNomeAlimento());
    }
    
    @Test
    @DisplayName("Deve lançar erro ao buscar por ID inexistente")
    void testBuscarPorIdInexistente() {
        when(doacaoRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RegraDeNegocioException.class,
            () -> doacaoService.buscarPorId(999L)
        );
    }






}