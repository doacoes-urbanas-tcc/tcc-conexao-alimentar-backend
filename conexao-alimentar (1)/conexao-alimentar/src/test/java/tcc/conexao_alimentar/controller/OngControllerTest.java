package tcc.conexao_alimentar.controller;

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

import tcc.conexao_alimentar.DTO.EnderecoDTO;
import tcc.conexao_alimentar.DTO.OngRequestDTO;
import tcc.conexao_alimentar.service.OngService;

public class OngControllerTest {

    @InjectMocks
    private OngController ongController;

    @Mock
    private OngService ongService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ongController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCadastrarOng() throws Exception {
        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "12345-678",
                "Rua das Flores",
                "456",
                "Centro",
                "Rio de Janeiro",
                "RJ",
                -22.9068,
                -43.1729
        );

        OngRequestDTO dto = new OngRequestDTO(
                "ONG Amigos do Bem",
                "contato@amigosdobem.org",
                "senha123",
                "21999999999",
                enderecoDTO,
                "12.345.678/0001-90",
                "ONG dedicada a ajudar comunidades carentes"
        );

        String json = objectMapper.writeValueAsString(dto);

        doNothing().when(ongService).cadastrar(any(OngRequestDTO.class));

        mockMvc.perform(post("/ong/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("ONG cadastrada com sucesso! Aguarde aprovação."));

        verify(ongService, times(1)).cadastrar(any(OngRequestDTO.class));
    }

}
