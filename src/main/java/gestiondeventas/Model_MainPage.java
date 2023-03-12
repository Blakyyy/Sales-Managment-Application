package gestiondeventas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Model_MainPage {
    private static String url = "jdbc:mysql://localhost:3306/?user=root";
    private static String admin = "root";
    private static String passkey = "6751221T";

    public static boolean addToSales(String name, Double precio, int CantidadVendida, String FechaDeVenta, int id_users){
        try {
            Connection connection = DriverManager.getConnection(url, admin, passkey);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO GestionDeVentas.ventas (nombre, precio, cantidadVendida, fechaDeVenta, id_users) VALUES ( ? , ? , ? , ?, ?);");
            statement.setString(1, name);
            statement.setDouble(2, precio);
            statement.setInt(3, CantidadVendida);
            statement.setString(4, FechaDeVenta);
            statement.setInt(5, id_users);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getUserId(String username){
        int userId = -1;
        try {
            Connection connection = DriverManager.getConnection(url, admin, passkey);
            PreparedStatement statement = connection.prepareStatement("select idusers from GestionDeVentas.users WHERE username = ?; ");
            statement.setString(1, username);
            ResultSet r1 = statement.executeQuery();
            r1.next();
            userId = r1.getInt("idusers");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }
}