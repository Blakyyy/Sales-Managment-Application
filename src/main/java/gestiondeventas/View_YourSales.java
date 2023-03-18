package gestiondeventas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Font;


public class View_YourSales implements ActionListener{
    private static JFrame frame;
    private static JPanel panel;
    private static JButton addSale, deleteSale, goBack;
    private static JLabel successLabel;
    private static JTable table;
    private JTextField searchBar;
    private DefaultTableModel model;
    private static List<Sales> salesList = Model_YourSales.getSalesObject(Model_YourSales.getUserId(View_Login.getUsernameText()));
    private static int selectedRow = -1;

    public View_YourSales(List<Sales> sale) {
        frame = new JFrame("Your sales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,500 );
        
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);

        panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        
        addSale = new JButton("Add sale");
        addSale.addActionListener(this);
        addSale.setFont(buttonFont);

        deleteSale = new JButton("Delete sale");
        deleteSale.addActionListener(this);
        deleteSale.setFont(buttonFont);

        successLabel = new JLabel("");
        successLabel.setFont(buttonFont);
        successLabel.setHorizontalAlignment(JLabel.CENTER);

        goBack = new JButton("←");
        goBack.addActionListener(this);
        goBack.setFont(buttonFont);

        String[] columnNames = {"Id", "Name", "Price", "Quantity", "Date"};
        Object[][] data = new Object[sale.size()][5];
        for (int i = 0; i < sale.size(); i++) {
            data[i][0] = sale.get(i).getIdVentasForEachUser();
            data[i][1] = sale.get(i).getNameOfProduct();
            data[i][2] = sale.get(i).getPrice();
            data[i][3] = sale.get(i).getAmountOfSale();
            data[i][4] = sale.get(i).getFechaDeVenta();
        }
        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    
        table = new JTable(model);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow = table.getSelectedRow();
            }
        });

        searchBar = new JTextField(20);
        searchBar.setFont(buttonFont);
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTable();
            }
        
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTable();
            }
        
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTable();
            }
        
            private void updateTable() {
                String userInput = searchBar.getText();
                List<Sales> filteredSales = Model_YourSales.searchForProductByName(Model_YourSales.getUserId(View_Login.getUsernameText()), userInput);
                model.setRowCount(0);
                fillTable(model, filteredSales);
        
            }
        });
       

    
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
        buttonPanel.add(addSale);
        buttonPanel.add(deleteSale);
        buttonPanel.add(goBack);
        
        JMenuItem idAscItem = new JMenuItem("Id(↑)");
        idAscItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Sales> salesId = Model_YourSales.sortById(Model_YourSales.getUserId(View_Login.getUsernameText()), "ASC");
                frame.dispose();
                new View_YourSales(salesId);
            }
        });
        JMenuItem idDescItem = new JMenuItem("Id(↓)");
        idDescItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Sales> salesId = Model_YourSales.sortById(Model_YourSales.getUserId(View_Login.getUsernameText()), "DESC");
                frame.dispose();
                new View_YourSales(salesId);
            }
        });
        JMenuItem priceAscItem = new JMenuItem("Price(↑)");
        priceAscItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Sales> salesPrice = Model_YourSales.sortByPrice(Model_YourSales.getUserId(View_Login.getUsernameText()), "ASC");
                frame.dispose();
                new View_YourSales(salesPrice);
            }
        });
        JMenuItem priceDescItem = new JMenuItem("Price(↓)");
        priceDescItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Sales> salesPrice = Model_YourSales.sortByPrice(Model_YourSales.getUserId(View_Login.getUsernameText()), "DESC");
                frame.dispose();
                new View_YourSales(salesPrice);
            }
        });
        JMenuItem amountAscItem = new JMenuItem("Amount of Sale(↑)");
        amountAscItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Sales> salesQuantity = Model_YourSales.sortByQuanityOfSales((Model_YourSales.getUserId(View_Login.getUsernameText())), "ASC");
                frame.dispose();
                new View_YourSales(salesQuantity);
            }
        });
        JMenuItem amountDescItem = new JMenuItem("Amount of Sale(↓)");
        amountDescItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Sales> salesQuantity = Model_YourSales.sortByQuanityOfSales(Model_YourSales.getUserId(View_Login.getUsernameText()), "DESC");
                frame.dispose();
                new View_YourSales(salesQuantity);
            }
    });

    JPanel successPanel = new JPanel(new BorderLayout());
    successPanel.add(successLabel, BorderLayout.CENTER);
    
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

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    buttonPanel.add(menuBar, gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    buttonPanel.add(searchBar, gbc);
    
    gbc.gridx = 2;
    gbc.gridy = 0;
    buttonPanel.add(addSale, gbc);

    gbc.gridx = 3;
    gbc.gridy = 0;
    buttonPanel.add(deleteSale, gbc);

    gbc.gridx = 4;
    gbc.gridy = 0;
    buttonPanel.add(goBack, gbc);

    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    buttonPanel.add(successLabel, gbc);

    panel.add(buttonPanel, BorderLayout.NORTH);
    panel.add(successPanel, BorderLayout.SOUTH);

    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    frame.add(panel);
    frame.setVisible(true);
}

private void fillTable(DefaultTableModel tableModel, List<Sales> salesList) {
    for (Sales sale :salesList ) {
        Object[] rowData = {sale.getIdVentasForEachUser(), sale.getNameOfProduct(), sale.getPrice(), sale.getAmountOfSale(), sale.getFechaDeVenta()};
        tableModel.addRow(rowData);
    }
}
    
    

@Override
public void actionPerformed(ActionEvent e) {
    if(e.getSource() == addSale){
        frame.dispose();
        new View_AddSaleFromProducts(Model_YourProducts.getInfoProductsTable(Model_YourSales.getUserId(View_Login.getUsernameText())));
    }
    else if(e.getSource() == deleteSale){
        int selectedRow = table.getSelectedRow();
        if(selectedRow != -1){
            int idValue = (int) table.getValueAt(selectedRow, 0); 
            Model_YourSales.deleteSale(idValue, Model_YourSales.getUserId(View_Login.getUsernameText()));
            frame.dispose();
            new View_YourSales(Model_YourSales.getInfoVentasTable());
        }
        else{
            successLabel.setText("Please select a row before proceeding");
        }
    }
    else if(e.getSource() == goBack){
        frame.dispose();
        new View_MainPage();
    }
}   

    public static List<Sales> getSalesList() {
        return salesList;
    }


}


