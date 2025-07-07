package tcc.conexao_alimentar.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import tcc.conexao_alimentar.DTO.DoacaoRequestDTO;
import tcc.conexao_alimentar.DTO.DoacaoResponseDTO;
import tcc.conexao_alimentar.service.DoacaoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class DoacaoControllerTest {

    @InjectMocks
    private DoacaoController controller;

    @Mock
    private DoacaoService service;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

    }
    @Test
    public void testCriarDoacao() throws Exception {
        DoacaoRequestDTO requestDTO = new DoacaoRequestDTO(
            "Arroz", "QUILOGRAMA", 5.0, LocalDate.now().plusDays(10), "Arroz integral", "CEREAIS"
        );

        String json = objectMapper.writeValueAsString(requestDTO);

        doNothing().when(service).cadastrar(any(DoacaoRequestDTO.class));

        mockMvc.perform(post("/doacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(content().string("Doação cadastrada com sucesso!"));

        verify(service, times(1)).cadastrar(any(DoacaoRequestDTO.class));
    }

    @Test
    public void testListarTodasDoacoes() throws Exception {
        List<DoacaoResponseDTO> lista = List.of(
            new DoacaoResponseDTO(1L, "Arroz", "KG", 5.0, LocalDate.now().plusDays(10), "Arroz integral",
                    LocalDateTime.now(), LocalDateTime.now().plusDays(10), "CEREAIS", null, "Joao", 10L)
        );

        when(service.listarTodas()).thenReturn(lista);

        mockMvc.perform(get("/doacoes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nomeAlimento").value("Arroz"))
            .andExpect(jsonPath("$[0].quantidade").value(5.0));

        verify(service, times(1)).listarTodas();
    }

}