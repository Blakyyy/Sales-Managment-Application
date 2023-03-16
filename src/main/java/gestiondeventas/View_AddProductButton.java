package gestiondeventas;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View_AddProductButton implements ActionListener{
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel name, description, price, stock, success, minStock;
    private static JTextField nameText, descriptionText, priceText, stockText, minStockText;
    private static JButton confirm, goBack;

    public View_AddProductButton(){
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(500, 300));
    
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
    
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
    
        name = new JLabel("Name of the product:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(name, constraints);
    
        nameText = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(nameText, constraints);
    
        description = new JLabel("Description:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(description, constraints);
    
        descriptionText = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(descriptionText, constraints);
    
        price = new JLabel("Price:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(price, constraints);
    
        priceText = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(priceText, constraints);
    
        stock = new JLabel("Stock:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(stock, constraints);
    
        stockText = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(stockText, constraints);
    
        minStock = new JLabel("Minimum Stock Value Alert:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(minStock, constraints);
    
        minStockText = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(minStockText, constraints);
    
        success = new JLabel("");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        panel.add(success, constraints);
    
        confirm = new JButton("Confirm");
        confirm.addActionListener(this);
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        panel.add(confirm, constraints);
    
        goBack = new JButton("<-");
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        panel.add(goBack, constraints);
        goBack.addActionListener(this);
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm){
            if (nameText.getText().isEmpty() || descriptionText.getText().isEmpty() || priceText.getText().isEmpty() || stockText.getText().isEmpty() || minStockText.getText().isEmpty()) {
                success.setText("All fields are required");
            }
            else{
                if(Model_YourProducts.checkForNameLength(nameText.getText())){
                    if(Model_YourProducts.checkForDescriptionLength(descriptionText.getText())){
                        if(Model_AddSale.checkForDouble(priceText.getText())){
                            if(Model_AddSale.checkForInt(stockText.getText())){
                                if(Model_AddSale.checkForInt(minStockText.getText())){
                                int maxIdForEachProduct = Model_YourProducts.maxNumForIdVentasEachUser(Model_YourSales.getUserId(View_Login.getUsernameText())) + 1;
                                int idUser = Model_YourSales.getUserId(View_Login.getUsernameText());
                                Model_YourProducts.insertIntoProductsTable(nameText.getText(), descriptionText.getText(), Double.parseDouble(priceText.getText()), Integer.parseInt(stockText.getText()), Integer.parseInt(minStockText.getText()), idUser, maxIdForEachProduct);
                                success.setText("The product was added succesfully");
                            }
                            else{
                                success.setText("Invalid Minimun Stock Value Input");
                            }
                            
                        }
                        else{
                            success.setText("Invalid stock nput");
                        }
                    }
                    else{
                        success.setText("Invalid price input");
                    }
                }
                else{
                    success.setText("The product description is too long");
                }
           }
            else{
            success.setText("The product name is too long");
           }
        }
    }
    if(e.getSource() == goBack){
        frame.dispose();
        int idUser = Model_YourSales.getUserId(View_Login.getUsernameText());
        new View_YourProducts(Model_YourProducts.getInfoProductsTable(idUser));
    }
}

}
