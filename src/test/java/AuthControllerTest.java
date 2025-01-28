import beans.LoginBean;
import controllers.AuthController;
import exceptions.UserNotFoundException;
import models.user.UserDao;
import models.user.User;
import models.user.Owner;
import utils.session.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    private AuthController authController;

    @BeforeEach
    void setUp() {
        // Inizializza il controller
        authController = new AuthController();
    }

    @Test
    void testAuthUser_SuccessfulLogin() {

        LoginBean loginBean = new LoginBean();
        loginBean.setUsername("Marco");
        loginBean.setPassword("psw1");

        boolean result = authController.authUser(loginBean);

        assertTrue(result, "Login successful");
        assertNotNull(SessionManager.getInstance().getLoggedUser(), "User should be logged in");
        assertEquals("Marco", SessionManager.getInstance().getLoggedUser().getUsername(), "Username should be Marco");
    }
}
