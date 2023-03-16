package gestiondeventas;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View_MainPage extends JFrame implements ActionListener{
    private JFrame frame;
    private JPanel panel;
    private JButton yourProductsButton;
    private JButton yourSalesButton;
        
    public View_MainPage() {    
            // Initialize the frame object
        frame = new JFrame("Main Page");
            
            // Create a panel to hold the buttons
        panel = new JPanel(new GridBagLayout());
            
            // Create the "Your products" button and add it to the panel
        yourProductsButton = new JButton("Your products");
        yourProductsButton.addActionListener(this);
        yourProductsButton.setPreferredSize(new Dimension(150, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(yourProductsButton, gbc);
        
        // Create the "Your sales" button and add it to the panel
        yourSalesButton = new JButton("Your sales");
        yourSalesButton.addActionListener(this);
        yourSalesButton.setPreferredSize(new Dimension(150, 50));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(yourSalesButton, gbc);
            
            // Add the panel to the frame
        frame.add(panel);
            
            // Set the size, visibility, location, and close operation of the frame
        frame.setSize(500, 200);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == yourProductsButton){
            frame.dispose();
            new View_YourProducts(Model_YourProducts.getInfoProductsTable(Model_YourSales.getUserId(View_Login.getUsernameText())));
        }
        else{
            frame.dispose();
            new View_YourSales(Model_YourSales.getInfoVentasTable());
        }
        
    }
    
}
