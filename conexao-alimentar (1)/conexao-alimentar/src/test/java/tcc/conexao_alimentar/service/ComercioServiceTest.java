package tcc.conexao_alimentar.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import tcc.conexao_alimentar.DTO.ComercioRequestDTO;
import tcc.conexao_alimentar.DTO.EnderecoDTO;
import tcc.conexao_alimentar.controller.ComercioController;
import tcc.conexao_alimentar.enums.TipoComercio;


public class ComercioServiceTest {

    
    @InjectMocks
    private ComercioController comercioController;

    @Mock
    private ComercioService comercioService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(comercioController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCadastrarComercio() throws Exception {
        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "12345-678",
                "Rua das Flores",
                "123",
                "Centro",
                "São Paulo",
                "SP",
                -23.5505,
                -46.6333
        );

        ComercioRequestDTO dto = new ComercioRequestDTO(
                "Padaria do Zé",
                "ze@padaria.com",
                "123456",
                "99999-9999",
                enderecoDTO,
                "00.000.000/0001-00",
                "Padaria Zé",
                TipoComercio.PADARIA
        );

        String json = objectMapper.writeValueAsString(dto);

        doNothing().when(comercioService).cadastrar(any(ComercioRequestDTO.class));

        mockMvc.perform(post("/comercio/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Comércio cadastrado com sucesso! Aguarde aprovação."));

        verify(comercioService, times(1)).cadastrar(any(ComercioRequestDTO.class));
    }
}
