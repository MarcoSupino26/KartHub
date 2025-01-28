import org.junit.jupiter.api.Test;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void testGetInstance() {
        try {
            DBConnection dbConnection = DBConnection.getInstance();
            assertNotNull(dbConnection, "DB connection instance should be not null");
        } catch (SQLException e) {
            fail("Exception while creating connection istance: " + e.getMessage());
        }
    }

    @Test
    void testGetConnection() {
        try {
            DBConnection dbConnection = DBConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            assertNotNull(connection, "DB connection should be not null");
            assertTrue(connection.isValid(2), "DB connection should be valid");

        } catch (SQLException e) {
            fail("Exception while connecting to DB: " + e.getMessage());
        }
    }
}
