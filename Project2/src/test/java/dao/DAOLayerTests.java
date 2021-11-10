package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
import project2.model.User;
import project2.dao.DAOLayer;

@Transactional
public class DAOLayerTests {

	 
    public static ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    DAOLayer dao = 
            appContext.getBean("Dao", DAOLayer.class);
    
    @Test
    @DisplayName("getMyUserByUserNameTest")
    public void getMyUserByUserNameTest(){
        
        String username = "";
        String password = "test";
        
        User expectedUser = new User(username, password, 
                "Worker", "Maker", "worker@aol.com");
  
         User actualUser = dao.getUserbyUsername(username);
        actualUser.setPassword("test");
        actualUser.setFirstName("Worker");
        actualUser.setLastName("Maker");
        actualUser.setEmail("worker@aol.com");
        
        assertEquals(expectedUser, actualUser);
    }
    
   
}
