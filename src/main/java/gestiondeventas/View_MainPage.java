package gestiondeventas;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Font;


public class View_MainPage implements ActionListener{
    private static JFrame frame;
    private static JPanel panel;
    private static JButton addSale, deleteSale;
    private static JTable table;
    private static List<Sales> salesList = Model_MainPage.getSalesObject(Model_MainPage.getUserId(View_Login.getUsernameText()));
    private static int selectedRow = -1;

    public View_MainPage(List<Sales> sale) {
        frame = new JFrame("Your sales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(300, 300, 600, 400);
    
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
    
        String[] columnNames = {"Id", "Name", "Price", "Quantity", "Date"};
        Object[][] data = new Object[sale.size()][5];
        for (int i = 0; i < sale.size(); i++) {
            data[i][0] = sale.get(i).getIdVentasForEachUser();
            data[i][1] = sale.get(i).getNameOfProduct();
            data[i][2] = sale.get(i).getPrice();
            data[i][3] = sale.get(i).getAmountOfSale();
            data[i][4] = sale.get(i).getFechaDeVenta();
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
    
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setPreferredSize(new Dimension(600, 40));

        JMenuBar menuBar = new JMenuBar();
        Border roundedBorder = BorderFactory.createLineBorder(Color.BLACK, 2, false);
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 10, 5, 10);
        Border compoundBorder = BorderFactory.createCompoundBorder(roundedBorder, emptyBorder);
        menuBar.setBorder(compoundBorder);
        menuBar.setFont(buttonFont);
        menuBar.setOpaque(true);
        menuBar.setBackground(new Color(238, 238, 238));
        JMenu sortMenu = new JMenu("Sort by");
        sortMenu.setFont(buttonFont);
        buttonPanel.add(menuBar);
        
        addSale = new JButton("Add sale");
        addSale.setPreferredSize(new Dimension(150, 30));
        addSale.setFont(buttonFont);
        addSale.addActionListener(this);
        buttonPanel.add(addSale);
    
        deleteSale = new JButton("Delete sale");
        deleteSale.setPreferredSize(new Dimension(150, 30));
        deleteSale.setFont(buttonFont);
        deleteSale.addActionListener(this);
        buttonPanel.add(deleteSale);
        
        JMenuItem idAscItem = new JMenuItem("Id(↑)");
        idAscItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Sales> salesId = Model_MainPage.sortById(Model_MainPage.getUserId(View_Login.getUsernameText()), "ASC");
                frame.dispose();
                new View_MainPage(salesId);
            }
        });
        JMenuItem idDescItem = new JMenuItem("Id(↓)");
        idDescItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Sales> salesId = Model_MainPage.sortById(Model_MainPage.getUserId(View_Login.getUsernameText()), "DESC");
                frame.dispose();
                new View_MainPage(salesId);
            }
        });
        JMenuItem priceAscItem = new JMenuItem("Price(↑)");
        priceAscItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Sales> salesPrice = Model_MainPage.sortByPrice(Model_MainPage.getUserId(View_Login.getUsernameText()), "ASC");
                frame.dispose();
                new View_MainPage(salesPrice);
            }
        });
        JMenuItem priceDescItem = new JMenuItem("Price(↓)");
        priceDescItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Sales> salesPrice = Model_MainPage.sortByPrice(Model_MainPage.getUserId(View_Login.getUsernameText()), "DESC");
                frame.dispose();
                new View_MainPage(salesPrice);
            }
        });
        JMenuItem amountAscItem = new JMenuItem("Amount of Sale(↑)");
        amountAscItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Sales> salesQuantity = Model_MainPage.sortByQuanityOfSales((Model_MainPage.getUserId(View_Login.getUsernameText())), "ASC");
                frame.dispose();
                new View_MainPage(salesQuantity);
            }
        });
        JMenuItem amountDescItem = new JMenuItem("Amount of Sale(↓)");
        amountDescItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Sales> salesQuantity = Model_MainPage.sortByQuanityOfSales(Model_MainPage.getUserId(View_Login.getUsernameText()), "DESC");
                frame.dispose();
                new View_MainPage(salesQuantity);
            }
    });

    idAscItem.setFont(buttonFont);
    idDescItem.setFont(buttonFont);
    priceAscItem.setFont(buttonFont);
    priceDescItem.setFont(buttonFont);
    amountAscItem.setFont(buttonFont);
    amountDescItem.setFont(buttonFont);

    sortMenu.add(idAscItem);
    sortMenu.add(idDescItem);
    sortMenu.add(priceAscItem);
    sortMenu.add(priceDescItem);
    sortMenu.add(amountAscItem);
    sortMenu.add(amountDescItem);
    menuBar.add(sortMenu);

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
                Model_MainPage.deleteSale(selectedRow + 1);
                frame.dispose();
                new View_MainPage(salesList);
            }
        }
    }

    public static List<Sales> getSalesList() {
        return salesList;
    }


}


