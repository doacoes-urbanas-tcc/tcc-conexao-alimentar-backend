package tcc.conexao_alimentar.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import tcc.conexao_alimentar.DTO.DoacaoRequestDTO;
import tcc.conexao_alimentar.security.JwtAuthFilter;
import tcc.conexao_alimentar.service.DoacaoService;
import tcc.conexao_alimentar.service.JwtService;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DoacaoController.class)
public class DoacaoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoacaoService doacaoService;

    @MockBean
    private JwtAuthFilter JwtAuthFilter;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "fulano@teste.com", roles = {"COMERCIO"})
    @DisplayName("Deve cadastrar doação com sucesso (POST /doacoes)")
    void testCriarDoacao() throws Exception {
        DoacaoRequestDTO dto = new DoacaoRequestDTO(
            "Arroz", "KG", 5.0, LocalDate.now().plusDays(1), "Descrição", "GRAOS"
        );

        doNothing().when(doacaoService).cadastrar(any(DoacaoRequestDTO.class));

        mockMvc.perform(post("/doacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(content().string("Doação cadastrada com sucesso!"));
    }


}
