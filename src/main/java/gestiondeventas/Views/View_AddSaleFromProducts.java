package gestiondeventas.Views;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import gestiondeventas.Models.Model_YourProducts;
import gestiondeventas.Models.Model_YourSales;
import gestiondeventas.Models.Products;
import gestiondeventas.Models.CustomMultiLineTableCellRenderer;
import gestiondeventas.Models.CustomTableCellRenderer;


public class View_AddSaleFromProducts implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JTable table;
    private JLabel success;
    private JButton addSale, goBack, addAnotherSale;
    private JTextField searchBar;
    private DefaultTableModel tableModel;
    private int selectedRow;
    private static int idValue;

    public View_AddSaleFromProducts(List<Products> productsList) {
        frame = new JFrame("Choose the product you have sold");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
    
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
    
        panel = new JPanel(new BorderLayout());
    
        JLabel searchLabel = new JLabel("Search by product name:");
        searchLabel.setFont(buttonFont);
    
        addSale = new JButton("Add Inventory Sale");
        addSale.addActionListener(this);
        addSale.setFont(buttonFont);
    
        addAnotherSale = new JButton("Add Non-Inventory Sale");
        addAnotherSale.addActionListener(this);
        addAnotherSale.setFont(buttonFont);
    
        goBack = new JButton("←");
        goBack.addActionListener(this);
        addSale.setFont(buttonFont);
    
        success = new JLabel("");
    
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
                List<Products> filteredProducts = Model_YourProducts.searchForProductByName(Model_YourSales.getUserId(View_Login.getUsernameText()), userInput);
                tableModel.setRowCount(0);
                fillTable(tableModel, filteredProducts);
        
                CustomTableCellRenderer customRenderer = new CustomTableCellRenderer(filteredProducts);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
                }
            }
        });
        
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(searchLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(searchBar, gbc);
    
        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonPanel.add(addSale, gbc);
    
        gbc.gridx = 3;
        gbc.gridy = 0;
        buttonPanel.add(addAnotherSale, gbc);
    
        gbc.gridx = 4;
        gbc.gridy = 0;
        buttonPanel.add(goBack, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 2;
        buttonPanel.add(success, gbc);
    
        panel.add(buttonPanel, BorderLayout.NORTH);
    
        String[] columnNames = {"ID", "Name", "Description", "Price", "Stock", "Minimum Stock Alert"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    
        fillTable(tableModel, productsList); 
        table = new JTable(tableModel);
    
        CustomTableCellRenderer customRenderer = new CustomTableCellRenderer(productsList);
        CustomMultiLineTableCellRenderer customMultiLineRenderer = new CustomMultiLineTableCellRenderer(productsList);
    
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (i == 2) {
                table.getColumnModel().getColumn(i).setCellRenderer(customMultiLineRenderer);
            } else {
                table.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
    
        frame.add(panel);
        frame.setVisible(true);
    }
    

    private void fillTable(DefaultTableModel tableModel, List<Products> productsList) {
        for (Products product : productsList) {
            Object[] rowData = {product.getIdProductsForEachUser(), product.getName(), product.getDescription(), product.getPrecio(), product.getStock(), product.getMin_stock_alert()};
            tableModel.addRow(rowData);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addSale){
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1){
                success.setText("You need to choose the row to continue");
            }
            else{
                int idColumnIndex = table.getColumnModel().getColumnIndex("ID");
                idValue = (int) table.getValueAt(selectedRow, idColumnIndex);
                frame.dispose();
                new View_AddDateAndQuantityForSale();
            }

        }
        else if(e.getSource() == addAnotherSale){
            frame.dispose();
            new View_AddSaleButton();
        }
        else if(e.getSource() == goBack){
            frame.dispose();
            new View_YourSales(Model_YourSales.getInfoVentasTable());
        }
    }

    public static int getIdValue() {
        return idValue;
    }
}
