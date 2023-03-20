package gestiondeventas;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class CustomTableCellRenderer extends DefaultTableCellRenderer {
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