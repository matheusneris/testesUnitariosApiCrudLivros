package com.apicrudlivro.testes;

import com.apicrudlivro.testes.controllers.LivroController;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebMvcTest(LivroController.class)
@TestPropertySource(locations = "classpath:application.properties")
public abstract class ConfigTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Before
    public void prepare() throws Exception{
        this.mockMvc = webAppContextSetup(this.context).build();
    }

}
