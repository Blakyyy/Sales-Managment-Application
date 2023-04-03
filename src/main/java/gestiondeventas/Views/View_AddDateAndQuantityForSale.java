package gestiondeventas.Views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import gestiondeventas.Models.Model_AddSale;
import gestiondeventas.Models.Model_YourProducts;
import gestiondeventas.Models.Model_YourSales;

public class View_AddDateAndQuantityForSale implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JTextField numberOfProductsSoldField;
    private JTextField dateOfSaleField;
    private JButton submitButton, goBack;
    private JLabel numberOfProductsSoldLabel, dateOfSaleLabel, successLabel;


    public View_AddDateAndQuantityForSale() {
        frame = new JFrame("Add Sale Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 200);
    
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
    
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
    
        numberOfProductsSoldLabel = new JLabel("Number of products sold:");
        numberOfProductsSoldLabel.setFont(labelFont);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(numberOfProductsSoldLabel, gridBagConstraints);
    
        numberOfProductsSoldField = new JTextField(20);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(numberOfProductsSoldField, gridBagConstraints);
    
        dateOfSaleLabel = new JLabel("Date of sale(YYYY-MM-DD):");
        dateOfSaleLabel.setFont(labelFont);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(dateOfSaleLabel, gridBagConstraints);
    
        dateOfSaleField = new JTextField(20);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(dateOfSaleField, gridBagConstraints);
    
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setFont(labelFont);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(submitButton, gridBagConstraints);
    
        goBack = new JButton("←");
        goBack.addActionListener(this);
        goBack.setFont(labelFont);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(goBack, gridBagConstraints);
    
        successLabel = new JLabel("");
        successLabel.setFont(labelFont);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(successLabel, gridBagConstraints);
    
        frame.add(panel);
        frame.setVisible(true);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        int id_ProductosForEachUser = View_AddSaleFromProducts.getIdValue();
        int userId = Model_YourSales.getUserId(View_Login.getUsernameText());
        int id_ventasForEachUser = Model_YourSales.maxNumForIdVentasEachUser(userId) + 1;
        if(e.getSource() == submitButton){
            if(dateOfSaleField.getText().isEmpty() || numberOfProductsSoldField.getText().isEmpty()){
                successLabel.setText("Both fields are required");
            }
            else{
                String correctNumberOfProducts = Model_AddSale.checkForComa(numberOfProductsSoldField.getText());
            if(Model_AddSale.checkForInt(correctNumberOfProducts)){
                if(!Model_AddSale.checkForDate(dateOfSaleField.getText()).equals("InvalidDateFormat")){
                    Model_YourProducts.updateProductTable(Integer.parseInt(numberOfProductsSoldField.getText()), userId, id_ProductosForEachUser);
                    Model_YourSales.addToSales(Model_YourProducts.getNameOfProduct(userId, id_ProductosForEachUser), Model_YourProducts.getPrecio(userId, id_ProductosForEachUser) , Integer.parseInt(numberOfProductsSoldField.getText()), dateOfSaleField.getText(), userId, id_ventasForEachUser);
                    successLabel.setText("Sale added successfully");
                }
                else{
                    successLabel.setText("Invalid date format");
                }
            }
            else{
                successLabel.setText("Invalid number of products format");
            }
        }
            }
        else if(e.getSource() == goBack){
            frame.dispose();
            new View_AddSaleFromProducts(Model_YourProducts.getInfoProductsTable(userId));
        }
            
    }
}
