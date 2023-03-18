package gestiondeventas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
        frame.setSize(800, 500);
    
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
    
        panel = new JPanel(new BorderLayout());
    
        JLabel searchLabel = new JLabel("Search by product name:");
        searchLabel.setFont(buttonFont);
    
        addSale = new JButton("Add sale");
        addSale.addActionListener(this);
        addSale.setFont(buttonFont);
    
        addAnotherSale = new JButton("Add another sale");
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
        
                // Update the customRenderer with the filteredProducts list
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
        gbc.gridy = 1;
        buttonPanel.add(success, gbc);
    
        panel.add(buttonPanel, BorderLayout.NORTH);
    
        String[] columnNames = {"ID", "Name", "Description", "Price", "Stock", "Minimum Stock Alert"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    
        fillTable(tableModel, productsList); // Pass both tableModel and productsList as parameters
    
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
    

    public static class CustomMultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
        private List<Products> products;
    
        public CustomMultiLineTableCellRenderer(List<Products> products) {
            this.products = products;
            setLineWrap(true);
            setWrapStyleWord(true);
        }
    
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((String) value);
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
    
            Products product = products.get(row);
            if (product.getStock() <= product.getMin_stock_alert()) {
                setBackground(Color.RED);
            } else {
                setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            }
    
            setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
    
            return this;
        }
    }
    

    public static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        private List<Products> products;
    
        public CustomTableCellRenderer(List<Products> products) {
            this.products = products;
        }
    
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            Products product = products.get(row);
    
            if (product.getStock() <= product.getMin_stock_alert()) {
                c.setBackground(Color.RED);
            } else {
                c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            }
            c.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
    
            return c;
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
