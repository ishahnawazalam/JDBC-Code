import java.sql.*;
import static java.lang.Class.forName;

public class DemoJdbc {

    private static String URL = "jdbc:postgresql://localhost:5432/JDBCDemoReddy";
    private static String USER = "postgres";
    private static String PASSWORD = "pgAdmin@MySQL9";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        try{
            // 1. Load and Register JDBC Driver ->This is optional(U can comment it). This throw an ClassNotFoundException
            // Class.forName("org.postgresql.Driver");

            // 2. Creating Connection -> This throw SQL exception
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("DB Connection established Successfully");

            // 3. Creating Statement
//            Statement st = con.createStatement();

//=============== 4.Executing statement(// Fetching all records for particular ID) ===================
            // String query = "select * from student where sid = 1";
            // ResultSet rs  = st.executeQuery(query);

            // 5. Process the result( // Fetching all records for particular ID ka Result)

            // System.out.println(rs.next()); // Just check ke koi row exist karti hai ki nhi // true
//            Note : The cursor of rs.next() method is just before the first row or data
            // fetching single row by id : fetch it using both i.e., column name(in DB) and index of column number(start with 1)
//            while(rs.next()){
//                System.out.println(rs.getInt("sid"));
//                System.out.println(rs.getString("sname"));
//                System.out.println(rs.getInt("marks"));
//                System.out.println(rs.getInt(1));
//                System.out.println(rs.getString(2));
//                System.out.println(rs.getInt(3));
//            }
//======================================================================================================

//====================== 4.Executing statement(// Fetching all records) =========================
            // String query = "select * from student";
            // ResultSet rs  = st.executeQuery(query);

            // 5. Process the result(// Fetching all records)
            // fetching all records(printing all column for each row
//             while(rs.next()){
//                System.out.print(rs.getInt("sid") + "-");
//                System.out.print(rs.getString("sname") + "-");
//                System.out.println(rs.getInt("marks"));
//              }
//======================================================================================================

//========================= 4.Executing statement(Insert single Record) ==========================
//            String query = "insert into student values(4,'John',65)";
//            boolean status  = st.execute(query);

            // 5. Process the result(Insert single record ka)
//             System.out.println(status);
//======================================================================================================

//====================== 4.Executing statement(// Update records) ==================
//            String query = "update student set sname ='Sushil' where sid = 4";
//            boolean status  = st.execute(query);

            // 5. Process the result(//Update ka)
//            System.out.println(status);
//======================================================================================================

//======================== 4.Executing statement(Delete single records) ===============
//            String query = "delete from student where sid = 4";
//            boolean status  = st.execute(query);

            // 5. Process the result(//Delete single record ka)
//            System.out.println(status);
//======================================================================================================

// ================================= PREPARED STATEMENT ==================================
            // 3.CREATING STATEMENT/Executing Statement(Insert single records)
            int sid = 101;
            String sname = "Aaisha";
            int marks  = 66;
            String query = "insert into student values(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1,sid);
            pstmt.setString(2,sname);
            pstmt.setInt(3,marks);

            pstmt.execute();



            // 6. Close the connection
//            rs.close();
//            st.close();
            con.close();
            System.out.println("Connection closed");



        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}
