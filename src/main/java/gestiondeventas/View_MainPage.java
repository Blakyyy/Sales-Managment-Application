package gestiondeventas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class View_MainPage implements ActionListener{
    private static JFrame frame;
    private static JPanel panel;
    private static JButton addSale;

    public View_MainPage(){
        frame = new JFrame("Your sales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(300, 300, 300, 300);

        panel = new JPanel();
        panel.setLayout(null);

        addSale = new JButton("Add sale");
        addSale.setBounds(10, 2, 100, 30);
        addSale.addActionListener(this);

        panel.add(addSale);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addSale){
            frame.dispose();
            View_AddSaleButton vb = new View_AddSaleButton();
        }
    }

}


