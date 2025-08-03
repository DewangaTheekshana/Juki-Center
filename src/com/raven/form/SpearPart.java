/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import Model.MySQL;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author HUNT GADGETS
 */
public class SpearPart extends javax.swing.JPanel {

    HashMap<String, String> brandMap = new HashMap<>();

    /**
     * Creates new form SpearPart
     */
    public SpearPart() {
        initComponents();
        setOpaque(false);
        loadSpearParts();
        loadBrand();

//        jTable2.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTable2.setDragEnabled(false);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jTable2.getModel());
        jTable2.setRowSorter(sorter);

        addPlaceholderStyle(ItemSearch);
        addPlaceholderStyle(ItemSearch1);
    }

    public void addPlaceholderStyle(JTextField textField) {
        Font font = textField.getFont();
        font = font.deriveFont(Font.ITALIC);
        textField.setFont(font);
        textField.setForeground(Color.gray);
    }

    public void removePlaceholderStyle(JTextField textField) {
        Font font = textField.getFont();
        font = font.deriveFont(Font.PLAIN | Font.BOLD);
        textField.setFont(font);
        textField.setForeground(Color.black);
    }

    public void reset() {
        ItemSearch.setText("Search Part Name");
        addPlaceholderStyle(ItemSearch);

        ItemSearch1.setText("Search Brand Id");
        addPlaceholderStyle(ItemSearch1);

        jComboBox1.setSelectedIndex(0);

        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) jTable2.getRowSorter();
        if (sorter != null) {
            sorter.setRowFilter(null);
        }
    }

    public void loadSpearParts() {

        try {

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            ResultSet rs = MySQL.execute("SELECT * FROM `product` INNER JOIN `sub_location` ON `sub_location`.`id` = `product`.`sub_location_id` INNER JOIN `locator` ON `locator`.`id` = `sub_location`.`locator_id` INNER JOIN `brand` ON `brand`.`id` = `product`.`brand_id` ORDER BY product.id");

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String brandId = rs.getString("brandNo");
                String brand = rs.getString("brand.name");
                String qty = rs.getString("qty");
                String price = rs.getString("price");
                String locator = rs.getString("locator.location_id") + " : " + rs.getString("sub_location.sub_location_id");

                Vector v = new Vector();
                v.add(id);
                v.add(name);
                v.add(brandId);
                v.add(brand);
                if (qty.equals("0")) {
                    v.add("Out Of Stock");
                } else {
                    v.add(qty);
                }
                v.add(price);
                v.add(locator);

                model.addRow(v);

            }

        } catch (Exception e) {
        }
    }

    public void loadBrand() {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM `brand`");

            Vector v = new Vector();
            v.add("Select Brand");

            while (resultSet.next()) {
                v.add(resultSet.getString("name"));
                brandMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }

            jComboBox1.setModel(new DefaultComboBoxModel(v));
            jComboBox1.setSelectedIndex(0);

        } catch (Exception e) {
        }
    }

    private void applyFilters() {
        String searchText = ItemSearch.getText().trim();
        String searchBrandId = ItemSearch1.getText().trim();
        String selectedBrand = (String) jComboBox1.getSelectedItem();

        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) jTable2.getRowSorter();
        if (sorter == null) {
            sorter = new TableRowSorter<>((DefaultTableModel) jTable2.getModel());
            jTable2.setRowSorter(sorter);
        }

        java.util.List<RowFilter<Object, Object>> filters = new java.util.ArrayList<>();

        if (!searchText.isEmpty() && !searchText.equals("Search Part Name")) {
            filters.add(RowFilter.regexFilter("(?i)" + searchText, 1));
        }

        if (!searchBrandId.isEmpty() && !searchBrandId.equals("Search Brand Id")) {
            filters.add(RowFilter.regexFilter("(?i)" + searchBrandId, 2));
        }

        if (selectedBrand != null && !selectedBrand.equals("Select Brand")) {
            filters.add(RowFilter.regexFilter("(?i)" + selectedBrand, 3));
        }

        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filters));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1ss = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        ItemSearch = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        ItemSearch1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new com.raven.swing.TableColumn();
        scrollBar1 = new com.raven.swing.ScrollBar();
        buttonCustom1 = new com.raven.swing.ButtonCustom();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Lucida Fax", 1, 36)); // NOI18N
        jLabel2.setText("Spear Parts");

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Lucida Fax", 1, 52)); // NOI18N
        jLabel3.setText("JUKI CENTER");

        jButton1ss.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jButton1ss.setText("Add Spear Part");
        jButton1ss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ssActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
        );

        ItemSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ItemSearch.setText("Search Part Name");
        ItemSearch.setToolTipText("");
        ItemSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ItemSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ItemSearchFocusLost(evt);
            }
        });
        ItemSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ItemSearchKeyReleased(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jButton3.setText("Update Spear Part");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        ItemSearch1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ItemSearch1.setText("Search Brand Id");
        ItemSearch1.setToolTipText("");
        ItemSearch1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ItemSearch1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ItemSearch1FocusLost(evt);
            }
        });
        ItemSearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ItemSearch1KeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/jmc_logo.png"))); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 51, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("X");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(null);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(9, 15));
        jScrollPane1.setVerticalScrollBar(scrollBar1);
        jScrollPane1.setViewportView(jTable2);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Brand Id", "Brand", "Qty", "Price", "Locator"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setDragEnabled(true);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable2);

        scrollBar1.setBackground(new java.awt.Color(245, 245, 245));

        jButton2.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 483, Short.MAX_VALUE)
                                .addComponent(jButton1ss, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)))
                        .addContainerGap())
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(ItemSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ItemSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(42, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(135, 135, 135))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel3)
                                .addGap(16, 16, 16)
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton1ss, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel4)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ItemSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                        .addComponent(ItemSearch1, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(scrollBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)))
                .addContainerGap())
        );

        scrollBar1.getAccessibleContext().setAccessibleParent(this);
    }// </editor-fold>//GEN-END:initComponents

    private void ItemSearch1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ItemSearch1KeyReleased
        applyFilters();
    }//GEN-LAST:event_ItemSearch1KeyReleased

    private void ItemSearch1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ItemSearch1FocusLost
        if (ItemSearch1.getText().length() == 0) {
            addPlaceholderStyle(ItemSearch1);
            ItemSearch1.setText("Search Brand Id");

        }
    }//GEN-LAST:event_ItemSearch1FocusLost

    private void ItemSearch1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ItemSearch1FocusGained
        if (ItemSearch1.getText().equals("Search Brand Id")) {
            ItemSearch1.setText(null);
            ItemSearch1.requestFocus();
            removePlaceholderStyle(ItemSearch1);
        }
    }//GEN-LAST:event_ItemSearch1FocusGained

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        int row = jTable2.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row first!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String part_id = String.valueOf(jTable2.getValueAt(row, 0));

        AddSpearPart dialog = new AddSpearPart((JFrame) SwingUtilities.getWindowAncestor(this), true, part_id, this);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            applyFilters();
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void ItemSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ItemSearchKeyReleased

        applyFilters();
    }//GEN-LAST:event_ItemSearchKeyReleased

    private void ItemSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ItemSearchFocusLost

        if (ItemSearch.getText().length() == 0) {
            addPlaceholderStyle(ItemSearch);
            ItemSearch.setText("Search Part Name");

        }
    }//GEN-LAST:event_ItemSearchFocusLost

    private void ItemSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ItemSearchFocusGained

        if (ItemSearch.getText().equals("Search Part Name")) {
            ItemSearch.setText(null);
            ItemSearch.requestFocus();
            removePlaceholderStyle(ItemSearch);
        }
    }//GEN-LAST:event_ItemSearchFocusGained

    private void jButton1ssActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ssActionPerformed

        AddSpearPart asp = new AddSpearPart((JFrame) SwingUtilities.getWindowAncestor(this), true, "add", this);
        asp.setVisible(true);
    }//GEN-LAST:event_jButton1ssActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        reset();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ItemSearch;
    private javax.swing.JTextField ItemSearch1;
    private com.raven.swing.ButtonCustom buttonCustom1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton1ss;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.raven.swing.TableColumn jTable2;
    private com.raven.swing.ScrollBar scrollBar1;
    // End of variables declaration//GEN-END:variables
}
