package mci.softwareengineering2.group2.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import mci.softwareengineering2.group2.data.User;

/**
 * Class to test the user type
 * @since 17.05.2024
 * @version 1
 */
public class UserTest {


    private User user;

    @BeforeEach
    public void buildObjects(){
        this.user = new User();
        
    }

    @Test
    public void correctUser(){
        user.setUsername("test");
        user.setHashedPassword(user.encodePassword("test"));

        assertEquals(true,user.isUserCorrect());
    }

    @Test
    public void failtUser(){

        user.setUsername("test");
        user.setEmail("test@aon.at");

        assertNotEquals(true,user.isUserCorrect());
    }

    @Test
    public void encoderTest(){

        user.setUsername("test");
    
        String encodetPassword = new BCryptPasswordEncoder().encode("test");
        user.setHashedPassword(encodetPassword);

        assertEquals(true,user.checkPassword("test"));
    }
}