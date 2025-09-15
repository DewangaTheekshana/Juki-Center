package com.raven.model;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class StockTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setOpaque(true);

        // ✅ Check qty column (index 4)
        Object qtyValue = table.getValueAt(row, 4);

        boolean outOfStock = false;
        if (qtyValue != null) {
            String qs = qtyValue.toString().trim();
            if (qs.equalsIgnoreCase("Out Of Stock") || qs.equals("0")) {
                outOfStock = true;
            }
        }

        // ✅ Highlight ENTIRE row red if out of stock
        if (outOfStock && !isSelected) {
            c.setBackground(new Color(0xE74C3C)); // red
            c.setForeground(Color.WHITE);
        } else {
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            } else {
                c.setBackground(table.getBackground());
                c.setForeground(table.getForeground());
            }
        }

        return c;
    }
}
