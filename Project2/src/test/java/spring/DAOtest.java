package spring;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;


@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = { ApplicationConfig.class })
@WebAppConfiguration(value="/Project2/bin/src/main/webapp/WEB-INF/applicationContext.xml")
class DAOtest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test testAddPost Not Found")
	void testAddPost() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Test testGetUserbyID Not Found")
	void testGetUserbyID() {
		fail("Not yet implemented");
		
	}

	@Test
	@DisplayName("Test testAddComment Not Found")
	void testAddComment() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Test testIsliked Not Found")
	void testIsliked() {
		fail("Not yet implemented");
	}

}
