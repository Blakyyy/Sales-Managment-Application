package gestiondeventas;

public class Sales {
    
    private int idVentasForEachUser;
    private String nameOfProduct;
    private double price;
    private int amountOfSale;
    private String fechaDeVenta;
    
    
    public Sales(int idVentasForEachUser, String nameOfProduct, double price, int amountOfSale, String fechaDeVenta) {
        this.idVentasForEachUser = idVentasForEachUser;
        this.nameOfProduct = nameOfProduct;
        this.price = price;
        this.amountOfSale = amountOfSale;
        this.fechaDeVenta = fechaDeVenta;
        
    }
    public String getNameOfProduct() {
        return nameOfProduct;
    }
    public double getPrice() {
        return price;
    }
    public int getAmountOfSale() {
        return amountOfSale;
    }
    public String getFechaDeVenta() {
        return fechaDeVenta;
    }
    public int getIdVentasForEachUser() {
        return idVentasForEachUser;
    }
   
    
    
    
    
}
