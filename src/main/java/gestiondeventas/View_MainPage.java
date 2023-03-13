package gestiondeventas;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class View_MainPage implements ActionListener{
    private static JFrame frame;
    private static JPanel panel;
    private static JButton addSale, deleteSale;
    private static JTable table;
    private static List<Sales> salesList;
    private static int selectedRow = -1;

    public View_MainPage() {
        salesList = Model_MainPage.getSalesObject(Model_MainPage.getUserId(View_Login.getUsernameText()));
        frame = new JFrame("Your sales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(300, 300, 600, 400);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        String[] columnNames = {"Id", "Name", "Price", "Quantity", "Date"};
        Object[][] data = new Object[salesList.size()][5];
        for (int i = 0; i < salesList.size(); i++) {
            data[i][0] = salesList.get(i).getIdVentasForEachUser();
            data[i][1] = salesList.get(i).getNameOfProduct();
            data[i][2] = salesList.get(i).getPrice();
            data[i][3] = salesList.get(i).getAmountOfSale();
            data[i][4] = salesList.get(i).getFechaDeVenta();
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow = table.getSelectedRow();
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addSale = new JButton("Add sale");
        addSale.addActionListener(this);
        buttonPanel.add(addSale);

        deleteSale = new JButton("Delete sale");
        deleteSale.addActionListener(this);
        buttonPanel.add(deleteSale);

        panel.add(buttonPanel, BorderLayout.NORTH);

        frame.add(panel);
        frame.setVisible(true);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addSale){
            frame.dispose();
            new View_AddSaleButton();
        }
        else if(e.getSource() == deleteSale){
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1){
                frame.dispose();
                Model_MainPage.deleteSale(selectedRow);
                new View_MainPage();
            }
        }
    }

}


