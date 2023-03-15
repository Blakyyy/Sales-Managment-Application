package gestiondeventas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class Model_MainPage {
    private static String url = "jdbc:mysql://localhost:3306/?user=root";
    private static String admin = "root";
    private static String passkey = "6751221T";

    public static boolean addToSales(String name, Double precio, int CantidadVendida, String FechaDeVenta, int id_users, int idVentasForEachUser){
        try(Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO GestionDeVentas.ventas (nombre, precio, cantidadVendida, fechaDeVenta, id_users, idVentasForEachUser) VALUES ( ? , ? , ? , ?, ? , ? );");
            statement.setString(1, name);
            statement.setDouble(2, precio);
            statement.setInt(3, CantidadVendida);
            statement.setString(4, FechaDeVenta);
            statement.setInt(5, id_users);
            statement.setInt(6, idVentasForEachUser);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int maxNumForIdVentasEachUser(int userId){
        int maxId = 0;
        try(Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(idVentasForEachUser) FROM GestionDeVentas.ventas WHERE id_users = ?;");
            statement.setInt(1, userId);
            ResultSet r1 = statement.executeQuery();
            while(r1.next() && r1 != null){
                maxId = r1.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }

    public static int getUserId(String username){
        int userId = -1;
        try(Connection connection = DriverManager.getConnection(url, admin, passkey)){
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

    public static List<Sales> getSalesObject(int userId){
        List<Sales> saleList = new ArrayList<Sales>();
        try(Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.ventas WHERE id_users = ?;");
            statement.setInt(1, userId);
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                Sales sale = new Sales(r1.getInt("idVentasForEachUser"), r1.getString("nombre"), r1.getDouble("precio"), r1.getInt("cantidadVendida"), r1.getString("fechaDeVenta"));
                saleList.add(sale);
            }
        } catch (Exception e) {
            
        }
        return saleList;
    }

    public static boolean deleteSale(int idVentasForEachUser){
        try(Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            // Delete the sale with the given ID
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM GestionDeVentas.ventas WHERE idVentasForEachUser = ?");
            deleteStatement.setInt(1, idVentasForEachUser);
            deleteStatement.executeUpdate();
            
            // Update the IDs of the remaining sales
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE GestionDeVentas.ventas SET idVentasForEachUser = idVentasForEachUser - 1 WHERE idVentasForEachUser > ?");
            updateStatement.setInt(1, idVentasForEachUser);
            updateStatement.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Sales> sortById(int userId, String order){
        PreparedStatement statement;
        List<Sales> sales = new ArrayList<Sales>();
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            if(order.equals("ASC")){
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.ventas WHERE id_users = ? ORDER BY idVentasForEachUser ASC;");
            }
            else{
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.ventas WHERE id_users = ? ORDER BY idVentasForEachUser DESC;");
            }
            statement.setInt(1, userId);
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                Sales sale = new Sales(r1.getInt("idVentasForEachUser"), r1.getString("nombre"), r1.getDouble("precio"), r1.getInt("cantidadVendida"), r1.getString("fechaDeVenta"));
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    public static List<Sales> sortByPrice(int userId, String order){
        PreparedStatement statement;
        List<Sales> sales = new ArrayList<Sales>();
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            if(order.equals("ASC")){
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.ventas WHERE id_users = ? ORDER BY precio ASC;");
            }
            else{
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.ventas WHERE id_users = ? ORDER BY precio DESC;");
            }
            statement.setInt(1, userId);
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                Sales sale = new Sales(r1.getInt("idVentasForEachUser"), r1.getString("nombre"), r1.getDouble("precio"), r1.getInt("cantidadVendida"), r1.getString("fechaDeVenta"));
                System.out.printf("%d %s %.2f %d %s\n", sale.getIdVentasForEachUser(), sale.getNameOfProduct(), sale.getPrice(), sale.getAmountOfSale(), sale.getFechaDeVenta());
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    public static List<Sales> sortByQuanityOfSales(int userId, String order){
        PreparedStatement statement;
        List<Sales> sales = new ArrayList<Sales>();
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            if(order.equals("ASC")){
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.ventas WHERE id_users = ? ORDER BY cantidadVendida ASC;");
            }
            else{
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.ventas WHERE id_users = ? ORDER BY cantidadVendida DESC;");
            }
            statement.setInt(1, userId);
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                Sales sale = new Sales(r1.getInt("idVentasForEachUser"), r1.getString("nombre"), r1.getDouble("precio"), r1.getInt("cantidadVendida"), r1.getString("fechaDeVenta"));
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    public static List<Sales> getInfoVentasTable(){
        List<Sales> salesUpdated = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.ventas");
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                Sales sales = new Sales(r1.getInt("idVentasForEachUser"), r1.getString("nombre"), r1.getDouble("precio"), r1.getInt("cantidadVendida"), r1.getString("fechaDeVenta"));
                salesUpdated.add(sales);
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return salesUpdated;
    }
    
}