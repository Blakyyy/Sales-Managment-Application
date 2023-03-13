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



public class View_AddSaleButton implements ActionListener{
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel name, price, amount, date, success;
    private static JTextField nameText, priceText, amountText, dateText;
    private static JButton confirm, goBack;
    

    public View_AddSaleButton(){
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

        price = new JLabel("Price:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(price, constraints);

        priceText = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(priceText, constraints);

        amount = new JLabel("Sale quantity:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(amount, constraints);

        amountText = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(amountText, constraints);

        date = new JLabel("Date of sale (YYYY-MM-DD):");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(date, constraints);

        dateText = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(dateText, constraints);

        success = new JLabel("");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        panel.add(success, constraints);

        confirm = new JButton("Confirm");
        confirm.addActionListener(this);
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        panel.add(confirm, constraints);

        goBack = new JButton("<-");
        constraints.gridx = 0;
        constraints.gridy = 5;
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
        if(e.getSource() == confirm){
            String productName = nameText.getText();
            if(Model_AddSale.checkForDouble(priceText.getText())){
                double price = Double.parseDouble(priceText.getText());
                if(Model_AddSale.checkForInt(amountText.getText())){
                    int saleQuantity = Integer.parseInt(amountText.getText());
                    if(!Model_AddSale.checkForDate(dateText.getText()).equals("InvalidDateFormat")){
                        String dateOfSale = dateText.getText();
                        int idUser = Model_MainPage.getUserId(View_Login.getUsernameText());
                        int idVentasForEachUser = Model_MainPage.maxNumForIdVentasEachUser(idUser) + 1;
                        Model_MainPage.addToSales(productName, price, saleQuantity, dateOfSale, idUser, idVentasForEachUser);
                        success.setText("Product was added successfully");
                    }
                    else{
                        success.setText("The date is invalid format. Example: 2022-10-10");
                    }
                    
                }
                else{
                    success.setText("Please enter the quanity with numbers");
                }
            }
            else{
                success.setText("Please enter the price with number");
            }
            
            
        }
        else if(e.getSource() == goBack){
            frame.dispose();
            new View_MainPage();
        }
    }

    
    
    
}
