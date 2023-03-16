package gestiondeventas;

public class Products {

    private int idProductsForEachUser;
    private String name;
    private String description;
    private Double precio;
    private int stock;
    private int idUser;
    private int min_stock_alert;
   


    public int getMin_stock_alert() {
        return min_stock_alert;
    }

    public Products(int idProductsForEachUser, String name, String description, Double precio, int stock, int idUser, int min_stock_alert) {
        this.idProductsForEachUser = idProductsForEachUser;
        this.name = name;
        this.description = description;
        this.precio = precio;
        this.stock = stock;
        this.idUser = idUser;
        this.min_stock_alert = min_stock_alert;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public Double getPrecio() {
        return precio;
    }


    public int getStock() {
        return stock;
    }


    public int getIdUser() {
        return idUser;
    }


    public int getIdProductsForEachUser() {
        return idProductsForEachUser;
    }
}
