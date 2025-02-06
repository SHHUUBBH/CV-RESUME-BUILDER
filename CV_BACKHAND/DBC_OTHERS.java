import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBC_OTHERS {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/PERSONAL_INFO";
    private static final String USER = "root";
    private static final String PASSWORD = "DEMONFIREE_777";

    public boolean insertSummary(String summary) {
        String query = "INSERT INTO summary (description) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, summary);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertExperience(String detailedExperience) {
        String query = "INSERT INTO experience (details) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, detailedExperience);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertEducation(String schoolName, String duration, String educationExperience) {
        String query = "INSERT INTO education (school_name, duration, education_experience) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, schoolName);
            stmt.setString(2, duration);
            stmt.setString(3, educationExperience);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertSkills(String skill1, String skill2, String skill3, String skill4, String skill5) {
        String query = "INSERT INTO skills (skill1, skill2, skill3, skill4, skill5) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, skill1);
            stmt.setString(2, skill2);
            stmt.setString(3, skill3);
            stmt.setString(4, skill4);
            stmt.setString(5, skill5);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
