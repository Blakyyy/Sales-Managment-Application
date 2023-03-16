package gestiondeventas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class View_AddSaleFromProducts implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JTable table;
    private JLabel success;
    private JButton addSale, goBack;
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
                fillTable(filteredProducts);
            }
        });
    
        JLabel searchLabel = new JLabel("Search by product name:");
        searchLabel.setFont(buttonFont);
    
        addSale = new JButton("Add sale");
        addSale.addActionListener(this);
        addSale.setFont(buttonFont);

        goBack = new JButton("<-");
        goBack.addActionListener(this);
        addSale.setFont(buttonFont);
    
        success = new JLabel("");
    
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
        buttonPanel.add(goBack,gbc);
    
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

        fillTable(productsList);
    
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(2).setCellRenderer(new MultiLineTableCellRenderer());
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
    
        frame.add(panel);
        frame.setVisible(true);
    }
    

    private void fillTable(List<Products> productsList) {
        for (Products product : productsList) {
            Object[] rowData = {product.getIdProductsForEachUser(), product.getName(), product.getDescription(), product.getPrecio(), product.getStock(), product.getMin_stock_alert()};
            tableModel.addRow(rowData);
        }
    }

    private class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
        public MultiLineTableCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), 0);
            int height_needed = getPreferredSize().height;
            if (table.getRowHeight(row)!= height_needed) {
                table.setRowHeight(row, height_needed);
            }
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }
            return this;
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
        else if(e.getSource() == goBack){
            frame.dispose();
            new View_YourSales(Model_YourSales.getInfoVentasTable());
        }
    }

    public static int getIdValue() {
        return idValue;
    }
}
