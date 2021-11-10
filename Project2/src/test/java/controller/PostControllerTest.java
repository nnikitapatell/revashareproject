package controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import project2.controller.UserController;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@WebAppConfiguration
public class PostControllerTest {
	
    @Autowired
    protected WebApplicationContext webAppContext;
    private MockMvc mockMvc;

 
    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(webAppContext).build();
    }
    @Test
    public void webAppContextTest() throws Exception {
    	assertTrue(webAppContext.getServletContext() instanceof MockServletContext);
    }	
    @Test
    @DisplayName ("getRequestAllPostsTest")
    public void getRequestAllPostsTest() throws Exception {
        mockMvc.perform(get("/post?offset=0&limit=5")).andExpect(status().isOk());
    }	
    @Test
    @DisplayName ("getRequestCreatePostTest")
    public void postRequestCreatePostTest() throws Exception {
    	mockMvc.perform(get("/post")).andExpect(status().isOk());
    }	    
    
    @Test
    @DisplayName ("getRequestPostByIdTest")
    public void getRequestPostByIdTest() throws Exception {
    	mockMvc.perform(get("/post?id=0")).andExpect(status().isOk());
    }	
    
    
    @Test
    @DisplayName ("postRequestLikePostTest")
    public void postRequestLikePostTest() throws Exception {    
    	 mockMvc.perform(
         		post("/post/like?postId=0")
         	);
    }	
    
    @Test
    @DisplayName ("postRequestCommentOnPostTest")
    public void postRequestCommentOnPostTest() throws Exception {    
    	 mockMvc.perform(
         		post("/post/comment?postId=0")
         	);
    }

}