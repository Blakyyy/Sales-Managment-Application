package gestiondeventas.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class Model_YourSales {
    private static String url = "your-url-for-mysql-database-here";
    private static String admin = "your-admin-for-mysql-database-here";
    private static String passkey = "your-password-for-mysql-database-here";

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

    public static boolean deleteSale(int idVentasForEachUser, int userId){
        try(Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            // Delete the sale with the given ID
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM GestionDeVentas.ventas WHERE idVentasForEachUser = ? AND id_users = ?");
            deleteStatement.setInt(1, idVentasForEachUser);
            deleteStatement.setInt(2, userId);
            deleteStatement.executeUpdate();
            
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE GestionDeVentas.ventas SET idVentasForEachUser = idVentasForEachUser - 1 WHERE idVentasForEachUser > ? AND id_users = ?");
            updateStatement.setInt(1, idVentasForEachUser);
            updateStatement.setInt(2, userId);
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

    public static List<Sales> searchForProductByName(int userId, String userInput){
        List<Sales> salesList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.ventas WHERE nombre LIKE ? AND id_users = ?;");
            statement.setString(1, userInput + "%");
            statement.setInt(2, userId);
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                Sales sale = new Sales(r1.getInt("idVentasForEachUser"), r1.getString("nombre"), r1.getDouble("precio"), r1.getInt("cantidadVendida"), r1.getString("fechaDeVenta") );
                salesList.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesList;
    }

    
    
}