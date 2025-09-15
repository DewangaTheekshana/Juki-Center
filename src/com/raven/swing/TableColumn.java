package com.raven.swing;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class TableColumn extends JTable {

    public TableColumn() {
        setBackground(new Color(245, 245, 245));
        setRowHeight(40);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSelected, boolean hasFocus, int row, int column) {
                return new TableCell(o);
            }
        });
    }

    @Override
    public Component prepareRenderer(TableCellRenderer tcr, int row, int column) {
        TableCell.CellType celLType = TableCell.CellType.CENTER;
        if (column == 0) {
            celLType = TableCell.CellType.LEFT;
        } else if (column == getColumnCount() - 1) {
            celLType = TableCell.CellType.RIGHT;
        }

        Object value = getValueAt(row, column);
        TableCell cell = new TableCell(value, isCellSelected(row, column), celLType);

        // --- find the view index of the Qty column by header name (case-insensitive) ---
        int qtyViewIndex = -1;
        for (int i = 0; i < getColumnCount(); i++) {
            Object headerVal = getColumnName(i);
            if (headerVal != null && headerVal.toString().trim().equalsIgnoreCase("qty")) {
                qtyViewIndex = i;
                break;
            }
        }

        // --- get the qty value for this row (if the qty column exists) ---
        Object qtyValue = null;
        if (qtyViewIndex >= 0) {
            try {
                qtyValue = getValueAt(row, qtyViewIndex);
            } catch (Exception ex) {
                qtyValue = null;
            }
        }

        // --- determine out-of-stock ---
        boolean outOfStock = false;
        if (qtyValue != null) {
            String qs = qtyValue.toString().trim();
            if (qs.equalsIgnoreCase("out of stock")) {
                outOfStock = true;
            } else {
                try {
                    double dd = Double.parseDouble(qs);
                    if (dd <= 0.0) outOfStock = true;
                } catch (Exception ex) {
                    // not a number
                }
            }
        }

        // --- if out of stock, color the whole row red ---
        if (outOfStock) {
            cell.setOpaque(true);
            cell.setBackground(new Color(0xE74C3C)); // red
            cell.setForeground(Color.WHITE);
        }

        return cell;
    }
}
