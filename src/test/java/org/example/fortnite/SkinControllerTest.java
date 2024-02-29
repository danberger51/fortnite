package org.example.fortnite;

import org.example.fortnite.controllers.Controller.SkinController;
import org.example.fortnite.controllers.Services.SkinService;
import org.example.fortnite.controllers.Services.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.TimeZone;

import static org.example.fortnite.TestDataUtil.getTestSkins;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SkinController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SkinControllerTest {

    private static final String JSON_ALL_Skins = "[{\"id\":1, \"name\": \"Skin1\", \"seltenheit\": \"Episch\"}, " +
            "{\"id\":2, \"name\": \"Skin2\", \"seltenheit\": \"Episch\"}, " +
            "{\"id\":3, \"name\": \"Skin3\", \"seltenheit\": \"Episch\"}]";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkinService skinService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void prepare(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void checkGetAll()throws Exception{
        // gibt alle Filme aus, sobald findAll im gemockten MovieService aufgerufen wird
        doReturn(getTestSkins()).when(skinService).findAllSkins();

        mockMvc.perform(get("/skins"))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_ALL_Skins));
    }

    @Test
    public void checkNewSkin() throws Exception {
        mockMvc.perform(post("/skins/insert")
                        // der Inhalt in unserem Body entspricht einem JSON
                        .contentType("application/json")
                        // ein neues skin-Objekt wird als JSON in den Body gegeben und mitgeschickt
                        .content("{\"name\": \"Skin99\", \"rarity\": \"Episch\"}"))
                // wir erwarten den Status 201
                .andExpect(status().isOk());
    }

    @Test
    public void checkDelete() throws Exception {
        mockMvc.perform(delete("/skins/1"))
                .andExpect(status().isOk());

        Mockito.verify(skinService).deleteById(eq(1));
    }


}
