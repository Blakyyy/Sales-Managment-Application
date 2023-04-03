package gestiondeventas;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import gestiondeventas.Models.Model_YourProducts;
import gestiondeventas.Models.Products;

import java.util.List;

public class Model_YourProductsTest {
    @Test
    public void testGetInfoProductsTable() {
        // Arrange
        int userId = 1;

        // Act
        List<Products> products = Model_YourProducts.getInfoProductsTable(userId);

        // Assert
        Assertions.assertNotNull(products);
        Assertions.assertTrue(products.size() > 0);
    }

    @Test
    public void testCheckForNameLength() {
        // Arrange
        String productName = "Some product name that is longer than 60 characters";

        // Act
        boolean result = Model_YourProducts.checkForNameLength(productName);

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testCheckForDescriptionLength() {
        // Arrange
        String productDescription = "Some product description that is longer than 200 characters";

        // Act
        boolean result = Model_YourProducts.checkForDescriptionLength(productDescription);

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testInsertIntoProductsTable() {
        // Arrange
        String name = "New Product";
        String description = "A new product description";
        double price = 9.99;
        int stock = 10;
        int min_stock = 2;
        int user_Id = 1;
        int id_productosForEachUser = 1;

        // Act
        boolean result = Model_YourProducts.insertIntoProductsTable(name, description, price, stock, min_stock, user_Id, id_productosForEachUser);

        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void testMaxNumForIdVentasEachUser() {
        // Arrange
        int userId = 1;

        // Act
        int maxId = Model_YourProducts.maxNumForIdVentasEachUser(userId);

        // Assert
        Assertions.assertTrue(maxId >= 0);
    }

    @Test
    public void testDeleteProduct() {
        // Arrange
        int productId = 1;
        int userId = 1;

        // Act
        boolean result = Model_YourProducts.deleteProduct(productId, userId);

        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void testSortById() {
        // Arrange
        int userId = 1;
        String order = "ASC";

        // Act
        List<Products> products = Model_YourProducts.sortById(userId, order);

        // Assert
        Assertions.assertNotNull(products);
        Assertions.assertTrue(products.size() > 0);
    }

    @Test
    public void testSortByPrice() {
        // Arrange
        int userId = 1;
        String order = "ASC";

        // Act
        List<Products> products = Model_YourProducts.sortByPrice(userId, order);

        // Assert
        Assertions.assertNotNull(products);
        Assertions.assertTrue(products.size() > 0);
    }

    @Test
    public void testSortByStock() {
        // Arrange
        int userId = 1;
        String order = "ASC";

        // Act
        List<Products> products = Model_YourProducts.sortByStock(userId, order);

        // Assert
        Assertions.assertNotNull(products);
        Assertions.assertTrue(products.size() > 0);
    }

    @Test
    void testSortByMinStockAlert() {
        // given
        int userId = 1;
        String orderAsc = "ASC";
        String orderDesc = "DESC";
        
        // when
        List<Products> sortedAsc = Model_YourProducts.sortByMinStockAlert(userId, orderAsc);
        List<Products> sortedDesc = Model_YourProducts.sortByMinStockAlert(userId, orderDesc);
        
        // then
        Assertions.assertEquals(3, sortedAsc.size());
        Assertions.assertEquals(3, sortedDesc.size());
        Assertions.assertEquals(10, sortedAsc.get(0).getMin_stock_alert());
        Assertions.assertEquals(5, sortedDesc.get(0).getMin_stock_alert());
    }

    @Test
    void testSearchForProductByName() {
        // given
        int userId = 1;
        String userInput = "prod";
        
        // when
        List<Products> products = Model_YourProducts.searchForProductByName(userId, userInput);
        
        // then
        Assertions.assertEquals(2, products.size());
        Assertions.assertEquals("Product 1", products.get(0).getName());
    }

    @Test
    void testGetNameOfProduct() {
        // given
        int userId = 1;
        int id_ProductosForEachUser = 1;
        
        // when
        String nombre = Model_YourProducts.getNameOfProduct(userId, id_ProductosForEachUser);
        
        // then
        Assertions.assertEquals("Product 1", nombre);
    }

    @Test
    void testGetPrecio() {
        // given
        int userId = 1;
        int id_ProductosForEachUser = 1;
        
        // when
        Double precio = Model_YourProducts.getPrecio(userId, id_ProductosForEachUser);
        
        // then
        Assertions.assertEquals(10.0, precio);
    }

    @Test
    void testUpdateProductTable() {
        // given
        int stock = 2;
        int user_Id = 1;
        int id_ProductosForEachUser = 1;
        
        // when
        boolean result = Model_YourProducts.updateProductTable(stock, user_Id, id_ProductosForEachUser);
        
        // then
        Assertions.assertTrue(result);
    }

    @Test
    void testUpdateProductStock() {
        // given
        int stock = 5;
        int userId = 1;
        int id_ProductosForEachUser = 1;
        
        // when
        boolean result = Model_YourProducts.updateProductStock(stock, userId, id_ProductosForEachUser);
        
        // then
        Assertions.assertTrue(result);
    }

    @Test
    void testUpdateProductName() {
        // given
        String newName = "New Product Name";
        int userId = 1;
        int id_ProductosForEachUser = 1;
        
        // when
        boolean result = Model_YourProducts.updateProductName(newName, userId, id_ProductosForEachUser);
        
        // then
        Assertions.assertTrue(result);
    }
}
