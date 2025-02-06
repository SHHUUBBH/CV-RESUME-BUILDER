import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

class DBC_PERSONAL {
    private Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/PERSONAL_INFO";
        String user = "root";
        String password = "DEMONFIREE_777";
        return DriverManager.getConnection(url, user, password);
    }

    public boolean insertPersonalInfo(String firstName, String lastName, String profession, String address, String phone, String email, String dob, String linkedIn) {
        String sql = "INSERT INTO personal_info (first_name, last_name, profession, address, phone, email, dob, linkedin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, profession);
            stmt.setString(4, address);
            stmt.setString(5, phone);
            stmt.setString(6, email);
            stmt.setString(7, dob);
            stmt.setString(8, linkedIn);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


