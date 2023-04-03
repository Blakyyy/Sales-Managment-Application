package gestiondeventas;

import org.junit.Test;

import gestiondeventas.Models.Model_AddSale;

import static org.junit.Assert.*;

public class Model_AddSaleTest {
    @Test
    public void testCheckForComa() {
        assertEquals("1.23", Model_AddSale.checkForComa("1,23"));
        assertEquals("1.0", Model_AddSale.checkForComa("1,0"));
        assertEquals("5", Model_AddSale.checkForComa("5"));
    }
    
    @Test
    public void testCheckForInt() {
        assertTrue(Model_AddSale.checkForInt("123"));
        assertTrue(Model_AddSale.checkForInt("-456"));
        assertFalse(Model_AddSale.checkForInt("1.23"));
        assertFalse(Model_AddSale.checkForInt("12a"));
    }
    
    @Test
    public void testCheckForDouble() {
        assertTrue(Model_AddSale.checkForDouble("1.23"));
        assertTrue(Model_AddSale.checkForDouble("-4.56"));
        assertFalse(Model_AddSale.checkForDouble("123"));
        assertFalse(Model_AddSale.checkForDouble("12a"));
    }
    
    @Test
    public void testCheckForDate() {
        assertEquals("2022-12-31", Model_AddSale.checkForDate("2022/12/31"));
        assertEquals("2000-02-29", Model_AddSale.checkForDate("2000-02-29"));
        assertEquals("1999-04-30", Model_AddSale.checkForDate("1999-04-30"));
        assertEquals("1998-06-15", Model_AddSale.checkForDate("1998-06-15"));
        assertEquals("1997-08-01", Model_AddSale.checkForDate("1997-08-01"));
        assertEquals("1996-10-05", Model_AddSale.checkForDate("1996-10-05"));
        assertEquals("1995-12-25", Model_AddSale.checkForDate("1995-12-25"));
        assertEquals("InvalidDateFormat", Model_AddSale.checkForDate("1995-13-25"));
        assertEquals("InvalidDateFormat", Model_AddSale.checkForDate("1995/12/25"));
        assertEquals("InvalidDateFormat", Model_AddSale.checkForDate("not a date"));
    }
}
