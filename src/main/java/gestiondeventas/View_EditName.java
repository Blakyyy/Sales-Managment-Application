package gestiondeventas;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class View_EditName implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JButton submit, goback;
    private JLabel insertNewName, succes;
    private JTextField newName;
    private int idOFProduct;

    public View_EditName(int idOfProduct){
        frame = new JFrame("Edit name");
        frame.setBounds(200, 200, 400, 130);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        panel = new JPanel();
        panel.setBounds(200, 200, 300, 300);
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);

        insertNewName = new JLabel("Insert new name: ");
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
