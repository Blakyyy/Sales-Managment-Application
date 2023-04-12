package gestiondeventas.Views;

import java.awt.event.ActionEvent;
import gestiondeventas.Models.Model_YourProducts;
import gestiondeventas.Models.Model_YourSales;

public class View_EditDescription extends View_EditName{

    public View_EditDescription(int idOfProduct, String frameTitle, String labelS) {
        super(idOfProduct, frameTitle, labelS);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int userID = Model_YourSales.getUserId(View_Login.getUsernameText());
        if (e.getSource() == super.submit){
            if(super.newName.getText().length() > 200){
                succes.setText("The description is too long");
            }
            else if(super.newName.getText().length() == 0){
                succes.setText("The description is obligatory");
            }
            else{
                boolean confirm = Model_YourProducts.updateDescription(super.newName.getText(), userID, super.idOFProduct );
                if(confirm == true){
                    succes.setText("The product description was updated correctly");
                }
                else{
                    succes.setText("An error has ocurred");
                }
            }
        }
        else{
            super.frame.dispose();
            new View_YourProducts(Model_YourProducts.getInfoProductsTable(userID));
        }
    }
    
    


    
}


