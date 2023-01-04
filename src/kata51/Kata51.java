package kata51;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.PreparedStatement;
public class Kata51 {
    public static void main(String[] args) {
        MailListReader read=new MailListReader("emails.txt");
        List<String> lista=read.read();
        Connection conexion=connect("Kata5.db");
        selectfromdb("SELECT * FROM PEOPLE",conexion);
        String sql = """
                     CREATE TABLE IF NOT EXISTS direcc_email (
                      id integer PRIMARY KEY AUTOINCREMENT,
                      direccion text NOT NULL);""";
        createtable(sql,conexion);
        for (String line: lista) {
            insert(line,conexion);
        }
    }
    private static Connection connect(String bdurl) {
        String url = "jdbc:sqlite:"+bdurl;
        Connection conn = null;
        try {
        conn = DriverManager.getConnection(url);
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public static void selectfromdb(String select,Connection conexion){
        try (
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(select))
        {
            // Bucle sobre el conjunto de registros.
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                rs.getString("Name") + "\t" +
                rs.getString("Apellidos") + "\t" +
                rs.getString("Departamento") + "\t");
            }
        } 
        catch (SQLException e) {
        System.out.println(e.getMessage());
        }
    }
    public static void createtable(String sql,Connection conexion){
        
        try (
        Statement stmt = conexion.createStatement()) {
        // Se crea la nueva tabla
        stmt.execute(sql);
        System.out.println("Tabla creada");
        } 
        catch (SQLException e) {
        System.out.println(e.getMessage());
        }
    }
    public static void insert(String email,Connection conexion) {
        String sql = "INSERT INTO direcc_email(direccion) VALUES(?)";
        try (
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

