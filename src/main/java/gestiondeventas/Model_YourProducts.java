package gestiondeventas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class Model_YourProducts {
    private static String url = "jdbc:mysql://localhost:3306/?user=root";
    private static String admin = "root";
    private static String passkey = "6751221T";

    public static List<Products> getInfoProductsTable(int userId){
        List<Products> productsUpdated = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.productos WHERE id_user = ?;");
            statement.setInt(1, userId);
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                Products products = new Products(r1.getInt("id_productosForEachUser"), r1.getString("nombre"), r1.getString("descripcion"), r1.getDouble("precio"), r1.getInt("stock"), r1.getInt("id_user"), r1.getInt("min_stock_alert"));
                productsUpdated.add(products);
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return productsUpdated;
    }

    public static boolean checkForNameLength(String nameOfProduct){
        if(nameOfProduct.split("").length <= 60){
            return true;
        }
        return false;
    }

    public static boolean checkForDescriptionLength(String description){
        if(description.split("").length <= 200){
            return true;
        }
        return false;
    }
    
    public static boolean insertIntoProductsTable(String name, String description, double price, int stock, int min_stock, int user_Id, int id_productosForEachUser){
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO GestionDeVentas.productos (nombre, descripcion, precio, stock, id_user, id_productosForEachUser, min_stock_alert) VALUES (?, ?, ?, ?, ?, ?, ?);");
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setDouble(3, price);
            statement.setInt(4, stock);
            statement.setInt(5, user_Id);
            statement.setInt(6, id_productosForEachUser);
            statement.setInt(7, min_stock);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int maxNumForIdVentasEachUser(int userId){
        int maxId = 0;
        try(Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(id_productosForEachUser) FROM GestionDeVentas.productos WHERE id_user = ?;");
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

    public static boolean deleteProduct(int productId, int userId){
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM GestionDeVentas.productos WHERE id_productosForEachUser = ? AND id_user = ?;");
            statement.setInt(1, productId);
            statement.setInt(2, userId);
            statement.executeUpdate();

            PreparedStatement updateStatement = connection.prepareStatement("UPDATE GestionDeVentas.productos SET id_ProductosForEachUser = id_ProductosForEachUser - 1 WHERE id_ProductosForEachUser > ? AND id_user = ?");
            updateStatement.setInt(1, productId);
            updateStatement.setInt(2, userId);
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) { 
            e.printStackTrace();
        }
        return false;
    }


    public static List<Products> sortById(int userId, String order){
        PreparedStatement statement;
        List<Products> products = new ArrayList<Products>();
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            if(order.equals("ASC")){
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.productos WHERE id_user = ? ORDER BY id_productosForEachUser ASC;");
            }
            else{
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.productos WHERE id_user = ? ORDER BY id_productosForEachUser DESC;");
            }
            statement.setInt(1, userId);
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                Products product = new Products(r1.getInt("id_productosForEachUser"), r1.getString("nombre"), r1.getString("descripcion"), r1.getDouble("precio"), r1.getInt("stock"), userId, r1.getInt("min_stock_alert"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static List<Products> sortByPrice(int userId, String order){
        PreparedStatement statement;
        List<Products> products = new ArrayList<Products>();
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            if(order.equals("ASC")){
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.productos WHERE id_user = ? ORDER BY precio ASC;");
            }
            else{
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.productos WHERE id_user = ? ORDER BY precio DESC;");
            }
            statement.setInt(1, userId);
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                Products product = new Products(r1.getInt("id_productosForEachUser"), r1.getString("nombre"), r1.getString("descripcion"), r1.getDouble("precio"), r1.getInt("stock"), userId, r1.getInt("min_stock_alert"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static List<Products> sortByStock(int userId, String order){
        PreparedStatement statement;
        List<Products> products = new ArrayList<Products>();
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            if(order.equals("ASC")){
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.productos WHERE id_user = ? ORDER BY stock ASC;");
            }
            else{
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.productos WHERE id_user = ? ORDER BY stock DESC;");
            }
            statement.setInt(1, userId);
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                Products product = new Products(r1.getInt("id_productosForEachUser"), r1.getString("nombre"), r1.getString("descripcion"), r1.getDouble("precio"), r1.getInt("stock"), userId, r1.getInt("min_stock_alert"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static List<Products> sortByMinStockAlert(int userId, String order){
        PreparedStatement statement;
        List<Products> products = new ArrayList<Products>();
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            if(order.equals("ASC")){
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.productos WHERE id_user = ? ORDER BY min_stock_alert ASC;");
            }
            else{
                statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.productos WHERE id_user = ? ORDER BY min_stocK_alert DESC;");
            }
            statement.setInt(1, userId);
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                Products product = new Products(r1.getInt("id_productosForEachUser"), r1.getString("nombre"), r1.getString("descripcion"), r1.getDouble("precio"), r1.getInt("stock"), userId, r1.getInt("min_stock_alert"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static List<Products> searchForProductByName(int userId, String userInput){
        List<Products> productsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM GestionDeVentas.productos WHERE nombre LIKE ? AND id_user = ?;");
            statement.setString(1, userInput + "%");
            statement.setInt(2, userId);
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                Products product = new Products(r1.getInt("id_productosForEachUser"), r1.getString("nombre"), r1.getString("descripcion"), r1.getDouble("precio"), r1.getInt("stock"), userId , r1.getInt("min_stock_alert"));
                productsList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;
    }

    public static String getNameOfProduct(int userId, int id_ProductosForEachUser){
        String nombre = "";
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("SELECT nombre FROM GestionDeVentas.productos WHERE id_user = ? AND id_productosForEachUser = ?; ");
            statement.setInt(1, userId);
            statement.setInt(2, id_ProductosForEachUser);
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                nombre = r1.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }

    public static Double getPrecio(int userId, int id_ProductosForEachUser){
        Double precio = 0.0;
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("SELECT precio FROM GestionDeVentas.productos WHERE id_user = ? AND id_productosForEachUser = ?; ");
            statement.setInt(1, userId);
            statement.setInt(2, id_ProductosForEachUser);
            ResultSet r1 = statement.executeQuery();
            while(r1.next()){
                precio = r1.getDouble("precio");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return precio;

    }

    public static boolean updateProductTable(int stock, int user_Id, int id_ProductosForEachUser){
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("UPDATE GestionDeVentas.productos SET stock = stock - ? WHERE id_user = ? AND id_productosForEachUser = ?;");
            statement.setInt(1, stock);
            statement.setInt(2, user_Id);
            statement.setInt(3, id_ProductosForEachUser);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateProductStock(int stock, int userId, int id_ProductosForEachUser){
        try(Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("UPDATE gestiondeventas.productos SET stock = ? WHERE id_user = ? AND id_ProductosForEachUser = ?");
            statement.setInt(1, stock);
            statement.setInt(2, userId);
            statement.setInt(3, id_ProductosForEachUser);
            statement.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateProductName(String newName, int userId, int id_ProductosForEachUser){
        try (Connection connection = DriverManager.getConnection(url, admin, passkey)) {
            PreparedStatement statement = connection.prepareStatement("UPDATE GestionDeVentas.productos SET nombre = ? WHERE id_user = ? AND id_ProductosForEachUser = ?");
            statement.setString(1, newName);
            statement.setInt(2, userId);
            statement.setInt(3, id_ProductosForEachUser);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    

    
}
