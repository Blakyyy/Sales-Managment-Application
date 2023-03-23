package gestiondeventas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.util.List;

public class View_YourProducts implements ActionListener{

    private JFrame frame;
    private JPanel panel;
    private JTable table;
    private JButton addProduct, deleteProduct, editStock, editName, goBack;
    private JTextField searchBar;
    private DefaultTableModel tableModel;
    private static JLabel successLabel;

    public View_YourProducts(List<Products> products) {
        frame = new JFrame("Your Inventory");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);

        Font buttonFont = new Font("Arial", Font.PLAIN, 14);

        panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        addProduct = new JButton("Add product");
        addProduct.addActionListener(this);
        addProduct.setFont(buttonFont);

        deleteProduct = new JButton("Delete product");
        deleteProduct.addActionListener(this);
        deleteProduct.setFont(buttonFont);

        editStock = new JButton("Edit stock");
        editStock.addActionListener(this);
        editStock.setFont(buttonFont);

        editName = new JButton("Edit name");
        editName.addActionListener(this);
        editName.setFont(buttonFont);

        goBack = new JButton("←");
        goBack.addActionListener(this);
        goBack.setFont(buttonFont);

        successLabel = new JLabel("");
        successLabel.setFont(buttonFont);
        successLabel.setHorizontalAlignment(JLabel.CENTER);

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

        JMenuItem idAscItem = new JMenuItem("Id(↑)");
        idAscItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Products> products = Model_YourProducts.sortById(Model_YourSales.getUserId(View_Login.getUsernameText()), "ASC");
                frame.dispose();
                new View_YourProducts(products);
            }
        });
        JMenuItem idDescItem = new JMenuItem("Id(↓)");
        idDescItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Products> products = Model_YourProducts.sortById(Model_YourSales.getUserId(View_Login.getUsernameText()), "DESC");
                frame.dispose();
                new View_YourProducts(products);
            }
        });
        JMenuItem priceAscItem = new JMenuItem("Price(↑)");
        priceAscItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Products> products = Model_YourProducts.sortByPrice(Model_YourSales.getUserId(View_Login.getUsernameText()), "ASC");
                frame.dispose();
                new View_YourProducts(products);
            }
        });
        JMenuItem priceDescItem = new JMenuItem("Price(↓)");
        priceDescItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Products> products = Model_YourProducts.sortByPrice(Model_YourSales.getUserId(View_Login.getUsernameText()), "DESC");
                frame.dispose();
                new View_YourProducts(products);
            }
        });
        JMenuItem stockAscItem = new JMenuItem("Stock(↑)");
        stockAscItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Products> products = Model_YourProducts.sortByStock(Model_YourSales.getUserId(View_Login.getUsernameText()), "ASC");
                frame.dispose();
                new View_YourProducts(products);
            }
        });
        JMenuItem stockDescItem = new JMenuItem("Stock(↓)");
        stockDescItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Products> products = Model_YourProducts.sortByStock(Model_YourSales.getUserId(View_Login.getUsernameText()), "DESC");
                frame.dispose();
                new View_YourProducts(products);
            }
        });
        JMenuItem minStockDescItem = new JMenuItem("Minimum Stock Alert(↑)");
        minStockDescItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Products> products = Model_YourProducts.sortByMinStockAlert(Model_YourSales.getUserId(View_Login.getUsernameText()), "ASC");
                frame.dispose();
                new View_YourProducts(products);
            }
        });
        JMenuItem maxStockDescItem = new JMenuItem("Minimum Stock Alert(↓)");
        maxStockDescItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Products> products = Model_YourProducts.sortByMinStockAlert(Model_YourSales.getUserId(View_Login.getUsernameText()), "DESC");
                frame.dispose();
                new View_YourProducts(products);
            }
    });
        JPanel successPanel = new JPanel(new BorderLayout());
        successPanel.add(successLabel, BorderLayout.CENTER);
        

        idAscItem.setFont(buttonFont);
        idDescItem.setFont(buttonFont);
        priceAscItem.setFont(buttonFont);
        priceDescItem.setFont(buttonFont);
        stockAscItem.setFont(buttonFont);
        stockDescItem.setFont(buttonFont);
        minStockDescItem.setFont(buttonFont);
        maxStockDescItem.setFont(buttonFont);

        sortMenu.add(idAscItem);
        sortMenu.add(idDescItem);
        sortMenu.add(priceAscItem);
        sortMenu.add(priceDescItem);
        sortMenu.add(stockAscItem);
        sortMenu.add(stockDescItem);
        sortMenu.add(minStockDescItem);
        sortMenu.add(maxStockDescItem);
        menuBar.add(sortMenu);

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

        JLabel searchLabel = new JLabel("Search by product name:");
        searchLabel.setFont(buttonFont);
        searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));         

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(menuBar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(searchLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonPanel.add(searchBar, gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 0;
        buttonPanel.add(addProduct, gbc);
    
        gbc.gridx = 4;
        gbc.gridy = 0;
        buttonPanel.add(deleteProduct, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(3, 0, 0, 0);
        buttonPanel.add(editName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(3, 0, 0, 0);
        buttonPanel.add(editStock, gbc);
    
        gbc.gridx = 6;
        gbc.gridy = 0;
        buttonPanel.add(goBack, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(successLabel, gbc);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(successPanel, BorderLayout.SOUTH);

        String[] columnNames = {"ID", "Name", "Description", "Price", "Stock", "Minimum Stock Alert"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        

        for (Products product : products) {
            Object[] rowData = {product.getIdProductsForEachUser(), product.getName(), product.getDescription(), product.getPrecio(), product.getStock(), product.getMin_stock_alert()};
            tableModel.addRow(rowData);
        }

        table = new JTable(tableModel);
        int[] columnWidths = {30, 150, 250, 50, 50, 150};
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        
        CustomTableCellRenderer customRenderer = new CustomTableCellRenderer(products);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        table.getColumnModel().getColumn(2).setCellRenderer(new CustomTableCellRenderer(products));
        table.getColumnModel().getColumn(2).setCellRenderer(new CustomMultiLineTableCellRenderer(products));

        

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
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addProduct){
            frame.dispose();
            new View_AddProductButton();
        }
        else if(e.getSource() == deleteProduct){
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1){
                int idValue = (int) table.getValueAt(selectedRow, 0); 
                Model_YourProducts.deleteProduct(idValue, Model_YourSales.getUserId(View_Login.getUsernameText()));
                frame.dispose();
                new View_YourProducts(Model_YourProducts.getInfoProductsTable(Model_YourSales.getUserId(View_Login.getUsernameText())));
            }
            else{
                successLabel.setText("Please select a row before proceeding");
            }
        }
        else if(e.getSource() == editStock){
            int selectRow = table.getSelectedRow();
            if(selectRow != -1){
                int idValue = (int) table.getValueAt(selectRow, 0); 
                frame.dispose();
                new View_EditStock(idValue);
            }
            else{
                successLabel.setText("Please select a row before proceeding");
            }
        }
        else if(e.getSource() == editName){
            int selectRow = table.getSelectedRow();
            if(selectRow != -1){
                int idValue = (int) table.getValueAt(selectRow, 0);
                frame.dispose();
                new View_EditName(idValue);
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
}
