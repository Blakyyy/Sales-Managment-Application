package gestiondeventas;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class View_EditStock implements ActionListener{
    private JFrame frame;
    private JPanel panel;
    private JTextField newStock;
    private JLabel insertStock;
    private JLabel succes;
    private JButton confirm;
    private JButton goBack;
    private int idOfColumnToUpdate;
    private GridBagConstraints gbc = new GridBagConstraints();

    public View_EditStock(int idValue){
        frame = new JFrame("Edit stock");
        frame.setBounds(400, 400, 290, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Font buttonFont = new Font("Arial", Font.PLAIN, 14);

        panel = new JPanel();
        panel.setBounds(400, 400, 400, 400);
        panel.setFont(buttonFont);
        
        succes = new JLabel();
        succes.setFont(buttonFont);

        insertStock = new JLabel("Insert new stock: ");
        insertStock.setFont(buttonFont);
        newStock = new JTextField(20);

        confirm = new JButton("Confirm");
        confirm.setFont(buttonFont);
        confirm.addActionListener(this);
        
        goBack = new JButton("←");
        goBack.setFont(buttonFont);
        goBack.addActionListener(this);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(insertStock, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(newStock, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(confirm, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(goBack, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        panel.add(succes, gbc);

        frame.add(panel);
        frame.setVisible(true);

        this.idOfColumnToUpdate = idValue;

        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == confirm){
            if(Model_AddSale.checkForInt(newStock.getText())){
                int newStockInt = Integer.parseInt(newStock.getText());
                int userID = Model_YourSales.getUserId(View_Login.getUsernameText());
                Model_YourProducts.updateProductStock(newStockInt, userID, idOfColumnToUpdate);
                succes.setText("The stock has been successfully changed");
            }
            else{
                succes.setText("Please write a number");
            }
        }
        else{
            frame.dispose();
            new View_YourProducts(Model_YourProducts.getInfoProductsTable(Model_YourSales.getUserId(View_Login.getUsernameText())));
        }
    }
}
