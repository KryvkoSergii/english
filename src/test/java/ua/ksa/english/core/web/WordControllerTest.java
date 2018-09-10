package ua.ksa.english.core.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.ksa.english.core.dto.WordDTO;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class WordControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void create() throws Exception {
        WordDTO request = WordDTO.builder().english("test").translate("тест").build();
        mockMvc.perform(MockMvcRequestBuilders.post(WordController.URI, json(request))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attribute("english", request.getEnglish()))
                .andExpect(model().attribute("translate", request.getTranslate()));
    }

    @Test
    public void update() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void delete() {
    }

    private <T> String json(T target) throws JsonProcessingException {
        String json = mapper.writeValueAsString(target);
        log.info(json);
        return json;
    }
}