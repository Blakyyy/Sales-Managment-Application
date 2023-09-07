package gestiondeventas.Views;

import gestiondeventas.Models.Model_YourSales;

import java.awt.event.ActionEvent;

public class View_EditMinStockAlert extends View_EditName {

    public View_EditMinStockAlert(int idOfProduct, String frameTitle, String labelS) {
        super(idOfProduct, frameTitle, labelS);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int userId = Model_YourSales.getUserId(View_Login.getUsernameText());
        if(e.getSource() == super.submit){

        }
    }
}
