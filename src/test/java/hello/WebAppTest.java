package hello;

import org.hibernate.mapping.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.hamcrest.CoreMatchers.*;

import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

/**
 * Created by craigleclair on 2016-04-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class WebAppTest {

    private final String BASE_URL = "http://localhost:8080";

    private final String TEST_USER_NAME = "user";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        hello.File newFile = new hello.File(TEST_USER_NAME.hashCode(), "test_file_1");
        hello.File newFile2 = new hello.File(TEST_USER_NAME.hashCode(), "test_file_2");
        java.io.File file = new java.io.File(Application.ROOT + "/test_file_1");
        java.io.File file2 = new java.io.File(Application.ROOT + "/test_file_2");
        file.createNewFile();
        file2.createNewFile();

        fileRepository.save(newFile);
        fileRepository.save(newFile2);

    }

    @Test
    public void testRoot() throws Exception{
        this.mockMvc.perform(get("/")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk());
    }

    @Test
    public void testUnauthAccess() throws Exception {
        this.mockMvc.perform(get("/upload")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    public void testGetFiles() throws Exception {
        String content = this.mockMvc.perform(get("/upload")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(content.contains("test_file_1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAdmin() throws Exception {
        String content = this.mockMvc.perform(get("/upload")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(content.contains("Hey. You're an admin. You can adjust the max size(kb) of files allowed to be uploaded."));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testMaxSize() throws Exception {
        this.mockMvc.perform(post("/setting")
                .param("max_size", "256")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "Max File Size Updated. It is now 256kb."));
    }

    @Test
    @WithMockUser(username="user", roles={"user"})
    public void testUpload() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile("test", "", "multipart/form-data", "testteststestrst".getBytes());
        this.mockMvc.perform(fileUpload("/upload")
                .file("file", mockFile.getBytes())
                .param("name", "this_is_a_test")
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    public void testTooBigFile() throws Exception {
        Setting setting = settingRepository.getByKey("max_size");
        setting.setValue(0);
        settingRepository.save(setting);
        MockMultipartFile mockFile = new MockMultipartFile("test", "", "multipart/form-data", "testteststestrst".getBytes());
        this.mockMvc.perform(fileUpload("/upload")
                .file("file", mockFile.getBytes())
                .param("name", "this_is_a_test")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "File exceeds max file size. Sorry."));
    }

    @Test
    @WithMockUser
    public void testDownloadFile() throws Exception {
        this.mockMvc.perform(get("/file")
                .param("name", "test_file_1")
                .accept(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk());
    }



}
