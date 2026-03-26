import java.sql.*;

public class TransactionDemo {

    private static String URL = "jdbc:mysql://localhost:3306/jdbc_db";
    private static String USER = "root";
    private static String PASSWORD = "Shaaz@123";

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);) {
            System.out.println("Connected to the Database");

            // After connecting to DB , add Transaction code
            // Turned OFF AUTO COMMIT -- NO AUTO SAVE (Auto save nhi hoga)
            conn.setAutoCommit(false);

            try{
            // INSERT INTO ORDERS AND ORDER_ITEM
            // Insertion ke baad OrderId generate krke dega
                int orderId = insertOrder(conn, 101,"Alice01",2000.0);
                insertOrderItem(conn,orderId,"Laptop01",1,2000.0);

                // MANUAL COMMIT
                conn.commit();
                System.out.println("Transaction commited successfully");
            }
            catch (ArithmeticException e) {
                e.printStackTrace();
                conn.rollback();
                System.out.println("Operation Rollback Successfully");
            }
            finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static int insertOrder(Connection conn, int customerId, String customerName, double price) {
        String sql = "Insert into orders(user_id,customer_name,total_amount) values(?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1,customerId);
            pstmt.setString(2,customerName);
            pstmt.setDouble(3,price);

            int rows = pstmt.executeUpdate();
            System.out.println("Inserted into orders:" + rows);

            try(ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()){
                    int orderId = rs.getInt(1);
                    System.out.println("ORDER ID: " + orderId);
                    return orderId;
                }
                else{
                    throw new SQLException("Order Id Not Generated");
                }
            }

        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
    }

    private static void insertOrderItem(Connection conn, int orderId, String productName, int quantity, double price) {
        String sql = "Insert into order_items(order_id,product_name,quantity,price) values(?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,orderId);
            pstmt.setString(2,productName);
            pstmt.setDouble(3,quantity);
            pstmt.setDouble(4,price);

            // Create exception to simulate Transaction
//            int x = 10/0;

            int rows = pstmt.executeUpdate();
            System.out.println("Inserted into Order Items:" + rows);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

// Set up:
/*
- Create Order and Order_Items table in DB
- order_id automatically generate ho rha hai to usko return krwana hai order insert hone ke baad , to uske liye ye flag pass krna para :: 'Statement.RETURN_GENERATED_KEYS'. Isse resultset milega and then usse key(order_id) nikal skte hai
- code run krke and data insert krke table mei  truncate kr dena hai table ko to simulate example
- suppose order_items mei insertion krte waqt koi exception(create fake exception int x = 10/0 during insertion) aa jaye suppose server down ho gya ya kuch bhi then order placed ho gyi hai and orderId generate ho gyi hai but order_items logs nhi hui,means rupya aa gya hai order ka but order placed nhi hua thk se i.e., kya items ya products ship krna hai wo pta nhi hai. We have incomplete information
- So to resolve this issue we should use Transaction. So jab tak dono table mei insert nhi hoga tab tak
- By default AutoCommit true hota hai means jo bhi operation kroge(insert,update) wo commit(save) ho jayega. Isiliye by default commit nhi karwana tha isiliye false kiye phle so that rollback ho ske without saving anything
- So order_items mei insert krte time koi exception aaye to catch wala block chale and rollback ho jaye code, otherwise successfully commit ho jaye then finally mei autoCommit ko on kar denge
- Hamlog manually commit ya rollback kar rhe(conn.rollback()) but Spring ye sab internally handle kar lega with help of annotation(@Transactional). Bas bta dena hai ye piece of code Transaction hai.

 */
