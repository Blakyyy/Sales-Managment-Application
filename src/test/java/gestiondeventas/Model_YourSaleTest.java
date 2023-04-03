package gestiondeventas;

import org.junit.jupiter.api.Test;
import gestiondeventas.Models.Model_YourSales;
import gestiondeventas.Models.Sales;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Model_YourSalesTest {

    @Test
    void addToSales() {
        // Add a new sale
        boolean result = Model_YourSales.addToSales("product1", 12.99, 5, "2022-03-31", 1, 1);
        assertTrue(result);

        // Try to add the same sale again
        result = Model_YourSales.addToSales("product1", 12.99, 5, "2022-03-31", 1, 1);
        assertFalse(result);
    }

    @Test
    void maxNumForIdVentasEachUser() {
        int maxId = Model_YourSales.maxNumForIdVentasEachUser(1);
        assertTrue(maxId >= 0);
    }

    @Test
    void getUserId() {
        int userId = Model_YourSales.getUserId("user1");
        assertEquals(1, userId);

        userId = Model_YourSales.getUserId("non-existent-user");
        assertEquals(-1, userId);
    }

    @Test
    void getSalesObject() {
        List<Sales> saleList = Model_YourSales.getSalesObject(1);
        assertNotNull(saleList);
    }

    @Test
    void deleteSale() {
        boolean result = Model_YourSales.deleteSale(1, 1);
        assertTrue(result);

        result = Model_YourSales.deleteSale(1, 1);
        assertFalse(result);
    }

    @Test
    void sortById() {
        List<Sales> salesList = Model_YourSales.sortById(1, "ASC");
        assertNotNull(salesList);

        salesList = Model_YourSales.sortById(1, "DESC");
        assertNotNull(salesList);
    }

    @Test
    void sortByPrice() {
        List<Sales> salesList = Model_YourSales.sortByPrice(1, "ASC");
        assertNotNull(salesList);

        salesList = Model_YourSales.sortByPrice(1, "DESC");
        assertNotNull(salesList);
    }

    @Test
    void sortByQuanityOfSales() {
        List<Sales> salesList = Model_YourSales.sortByQuanityOfSales(1, "ASC");
        assertNotNull(salesList);

        salesList = Model_YourSales.sortByQuanityOfSales(1, "DESC");
        assertNotNull(salesList);
    }

    @Test
    void getInfoVentasTable() {
        List<Sales> salesList = Model_YourSales.getInfoVentasTable();
        assertNotNull(salesList);
    }

    @Test
    void searchForProductByName() {
        List<Sales> salesList = Model_YourSales.searchForProductByName(1, "product");
        assertNotNull(salesList);
    }
}

