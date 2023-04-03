package gestiondeventas.Models;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class CustomMultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
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
