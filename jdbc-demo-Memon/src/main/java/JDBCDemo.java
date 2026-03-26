import javax.xml.transform.Result;
import java.sql.*;

public class JDBCDemo {

    // kiske saath aap connection krna chahte ho
    private static String URL = "jdbc:mysql://localhost:3306/jdbc_db";
    private static String USER = "root";
    private static String PASSWORD = "Shaaz@123";

    public static void main(String[] args) {
        // using try with resources
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);) {
            System.out.println("Connected to the Database");
//          insertStudent(conn, "Alice", "alice@gmail.com");
            updateStudent(conn,1,"Bob","bob@gmail.com");
            selectStudents(conn);
            deleteStudent(conn,1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // creating method for inserting student using Statement Interface
    static void insertStudent(Connection conn, String name, String email) {
        String sql = "INSERT INTO student (name,email) VALUES ('" + name + "', '" + email + "')";
        try (Statement stmt = conn.createStatement()) {
            int rows = stmt.executeUpdate(sql);
            System.out.println("INSERTED :" + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method for Read or fetch
    private static void selectStudents(Connection conn){
        String sql = "Select * from student";
        try(Statement stmt = conn.createStatement();){
            ResultSet resultSet = stmt.executeQuery(sql);
            System.out.println("Student List: ");
            while (resultSet.next()){
                int id  = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                System.out.println(id + " : " + name + " : " + email );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method for Update
    private static void updateStudent(Connection conn, int id, String name, String email){
        String sql = "UPDATE student SET name = '" + name + "', email = '" + email + "' WHERE id = " + id;
        // UPDATE student SET name = 'John', email = 'john@gmail.com' WHERE id = 10;
        try (Statement stmt = conn.createStatement()) {
            int rows = stmt.executeUpdate(sql);
            System.out.println("UPDATED: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method for Delete
    private static void deleteStudent(Connection conn, int id){
        String sql = "DELETE FROM student WHERE id = " + id ;
        try (Statement stmt = conn.createStatement()) {
            int rows = stmt.executeUpdate(sql);
            System.out.println("Deleted: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

/*
- Naive way:
Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Connected to the Database");
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try {
                conn.close();
                System.out.println("Connection closed");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
*/
