package gestiondeventas;
import org.junit.Test;

import gestiondeventas.Models.Model_RegAndLog;

import static org.junit.Assert.*;

public class Model_RegAndLogTest {
    private static final String URL = "jdbc:mysql://localhost:3306/?user=root";
    private static final String ADMIN = "root";
    private static final String PASSKEY = "6751221T";
    
    @Test
    public void testUserAlreadyExists() {
        assertTrue(Model_RegAndLog.userAlreadyExists("admin", URL, ADMIN, PASSKEY));
        assertFalse(Model_RegAndLog.userAlreadyExists("nonexistentuser", URL, ADMIN, PASSKEY));
    }
    
    @Test
    public void testCheckForPassword() {
        assertTrue(Model_RegAndLog.checkForPassword("admin", "password", URL, ADMIN, PASSKEY));
        assertFalse(Model_RegAndLog.checkForPassword("admin", "wrongpassword", URL, ADMIN, PASSKEY));
        assertFalse(Model_RegAndLog.checkForPassword("nonexistentuser", "password", URL, ADMIN, PASSKEY));
    }
    
    @Test
    public void testSignUp() {
        Model_RegAndLog model = new Model_RegAndLog();
        int result1 = model.signUp(ADMIN, URL, PASSKEY, "newuser", "password", "newuser@example.com");
        assertEquals(1, result1);
        int result2 = model.signUp(ADMIN, URL, PASSKEY, "admin", "password", "newuser@example.com");
        assertEquals(-2, result2);
        int result3 = model.signUp(ADMIN, URL, PASSKEY, "newuser2", "password", "invalidemail");
        assertEquals(-1, result3);
    }
    
    @Test
    public void testTryToLogin() {
        Model_RegAndLog model = new Model_RegAndLog();
        assertTrue(model.tryToLogin("admin", "password", URL, ADMIN, PASSKEY));
        assertFalse(model.tryToLogin("admin", "wrongpassword", URL, ADMIN, PASSKEY));
        assertFalse(model.tryToLogin("nonexistentuser", "password", URL, ADMIN, PASSKEY));
    }
}
