package gestiondeventas.Views;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gestiondeventas.Models.Model_YourProducts;
import gestiondeventas.Models.Model_YourSales;



public class View_EditName implements ActionListener {
    protected JFrame frame;
    private JPanel panel;
    private GridBagConstraints gbc = new GridBagConstraints();
    protected JButton submit;
    protected JButton goback;
    protected JLabel insertNewName, succes;
    protected JTextField newName;
    protected int idOFProduct;

    public View_EditName(int idOfProduct, String frameTitle, String labelS){
        frame = new JFrame(frameTitle);
        frame.setBounds(200, 200, 400, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        panel = new JPanel();
        panel.setBounds(200, 200, 300, 300);
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);

        insertNewName = new JLabel(labelS);
        insertNewName.setFont(buttonFont);

        succes = new JLabel();
        succes.setFont(buttonFont);
        
        newName = new JTextField(20);

        submit = new JButton("Submit");
        submit.setFont(buttonFont);
        submit.addActionListener(this);

        goback = new JButton("←");
        goback.setFont(buttonFont);
        goback.addActionListener(this);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(insertNewName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(newName, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(submit, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(goback, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        panel.add(succes, gbc);

        this.idOFProduct = idOfProduct;

        frame.add(panel);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int maxCharAloud = 60;
        int userID = Model_YourSales.getUserId(View_Login.getUsernameText());
        if(e.getSource() == submit){
            if (newName.getText().length() <= maxCharAloud){
                Model_YourProducts.updateProductName(newName.getText(), userID, idOFProduct);
                succes.setText("The product name has been updated successfully");

            }
            else{
                succes.setText("The product name is too long");
            }
        }
        else{
            frame.dispose();
            new View_YourProducts(Model_YourProducts.getInfoProductsTable(userID));
        }
    }
}
