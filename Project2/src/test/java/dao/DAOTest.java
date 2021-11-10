package dao;

import static org.hamcrest.CoreMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import project2.dao.DAOLayer;
import project2.model.Post;
import project2.model.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@WebAppConfiguration
class DAOTest {

    @Autowired
    protected WebApplicationContext webAppContext;
    private MockMvc mockMvc;
    private DAOLayer dao;
    
    @Autowired
	private SessionFactory sf;
    
	@BeforeEach
	void setUp() throws Exception {
		mockMvc = webAppContextSetup(webAppContext).build();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
    public void webAppContextTest() throws Exception {
    	assertTrue(webAppContext.getServletContext() instanceof MockServletContext);
    }

	@Test
	void testAddUser() {
		fail("Not yet implemented");
	}

	@Test
	void testCreatePost() {
		
		fail("Not yet implemented");
	}

	@Test
	void testAddPost() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllPosts() {
		fail("Not yet implemented");
	}

	@Test
	void testGetUserbyID() {
		fail("Not yet implemented");
	}

	@Test
	void testGetUserbyUsername() {
		fail("Not yet implemented");
	}

	@Test
	void testGetUserPosts() {
		fail("Not yet implemented");
	}

	@Test
	void testAddLike() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLike() {
		fail("Not yet implemented");
	}

	@Test
	void testSearchUserbyUsername() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	void testAddComment() {
		fail("Not yet implemented");
	}

	@Test
	void testGetComments() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLikeCount() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPostById() {
		fail("Not yet implemented");
	}

	@Test
	void testDelLike() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCommentCount() {
		fail("Not yet implemented");
	}

	@Test
	void testIsliked() {
		fail("Not yet implemented");
	}

}
