/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
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
import javax.swing.SwingUtilities;

/**
 *
 * @author HUNT GADGETS
 */
public class AddSpearPart extends javax.swing.JDialog {

    HashMap<String, String> brandMap = new HashMap<>();
    HashMap<String, String> locatorMap = new HashMap<>();

    private SpearPart spearPart;

    /**
     * Creates new form AddSpearPart
     */
    public AddSpearPart(java.awt.Frame parent, boolean modal, String type, SpearPart spearPart) {
        super(parent, modal); // Makes it modal
        setTitle(type);
        this.spearPart = spearPart;

        initComponents();

        if (type.equals("add")) {
            registerBtn.setVisible(true);
            updateBtn.setVisible(false);
            hiddenId.setText("");
            hiddenId1.setText("");

            jPanel1.requestFocusInWindow();
            addPlaceholderStyle(partName);
            addPlaceholderStyle(partPrice);
            addPlaceholderStyle(partQty);
            addPlaceholderStyle(partLocator);
            addPlaceholderStyle(partBrandId);

            jComboBox2.setEnabled(true);
            jButton2.setEnabled(true);

            loadBrand();
            loadLocator();

        } else {
            registerBtn.setVisible(false);
            updateBtn.setVisible(true);
            hiddenId1.setText(" id : ");
            hiddenId.setText(type);
            jPanel1.requestFocusInWindow();

            jComboBox2.setEnabled(false);
            jButton2.setEnabled(false);

            loadBrand();
            loadLocator();
            loadData(type);
        }

    }

    public void addPlaceholderStyle(JTextField textField) {
        Font font = textField.getFont();
        textField.setFont(font);
        textField.setForeground(Color.gray);
    }

    public void removePlaceholderStyle(JTextField textField) {
        Font font = textField.getFont();
        textField.setFont(font);
        textField.setForeground(Color.black);
    }

    public void loadData(String type) {
        try {
            ResultSet rs = MySQL.execute("SELECT * FROM `product` "
                    + "INNER JOIN `sub_location` ON `sub_location`.`id` = `product`.`sub_location_id` "
                    + "INNER JOIN `locator` ON `locator`.`id` = `sub_location`.`locator_id` "
                    + "INNER JOIN `brand` ON `brand`.`id` = `product`.`brand_id` "
                    + "WHERE `product`.`id` = '" + type + "'");

            if (rs.next()) {
                removePlaceholderStyle(partName);
                removePlaceholderStyle(partPrice);
                removePlaceholderStyle(partQty);
                removePlaceholderStyle(partLocator);
                removePlaceholderStyle(partBrandId);

                partName.setText(rs.getString("product.name"));
                partPrice.setText(rs.getString("product.price"));
                partQty.setText(rs.getString("product.qty"));
                partBrandId.setText(rs.getString("product.brandNo"));
                partLocator.setText(rs.getString("sub_location.sub_location_id"));

                String brandName = rs.getString("brand.name");
                jComboBox1.setSelectedItem(brandName);

                String locatorId = rs.getString("locator.location_id");
                jComboBox2.setSelectedItem(locatorId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBrand() {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM `brand`");

            Vector v = new Vector();
            v.add("Select");

            while (resultSet.next()) {
                v.add(resultSet.getString("name"));
                brandMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }

            jComboBox1.setModel(new DefaultComboBoxModel(v));
            jComboBox1.setSelectedIndex(0);

        } catch (Exception e) {
        }
    }

    public void loadLocator() {
        try {

            ResultSet resultSet = MySQL.execute("SELECT * FROM `locator`");

            Vector v = new Vector();
            v.add("Select");

            while (resultSet.next()) {
                v.add(resultSet.getString("location_id"));
                locatorMap.put(resultSet.getString("location_id"), resultSet.getString("id"));
            }

            jComboBox2.setModel(new DefaultComboBoxModel(v));
            jComboBox2.setSelectedIndex(0);

        } catch (Exception e) {
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

        jPanel1 = new javax.swing.JPanel();
        partName = new javax.swing.JTextField();
        partPrice = new javax.swing.JFormattedTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        partQty = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        partLocator = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        registerBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        hiddenId = new javax.swing.JLabel();
        hiddenId1 = new javax.swing.JLabel();
        partBrandId = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Juki center");
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        partName.setText("Part Name");
        partName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                partNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                partNameFocusLost(evt);
            }
        });

        partPrice.setText("Part Price");
        partPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                partPriceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                partPriceFocusLost(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Add Brand");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        partQty.setText("Part Quantity");
        partQty.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                partQtyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                partQtyFocusLost(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton2.setText("Add Location");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        partLocator.setText("Location Number");
        partLocator.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                partLocatorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                partLocatorFocusLost(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Fax", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Spear Part Register");

        jLabel2.setFont(new java.awt.Font("Lucida Fax", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("JUKI CENTER");

        registerBtn.setBackground(new java.awt.Color(51, 204, 0));
        registerBtn.setFont(new java.awt.Font("Lucida Fax", 1, 18)); // NOI18N
        registerBtn.setText("Register");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        updateBtn.setBackground(new java.awt.Color(51, 153, 255));
        updateBtn.setFont(new java.awt.Font("Lucida Fax", 1, 18)); // NOI18N
        updateBtn.setText("Update");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        hiddenId.setFont(new java.awt.Font("Lucida Fax", 1, 18)); // NOI18N

        hiddenId1.setFont(new java.awt.Font("Lucida Fax", 1, 18)); // NOI18N

        partBrandId.setText("Brand id");
        partBrandId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                partBrandIdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                partBrandIdFocusLost(evt);
            }
        });
        partBrandId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partBrandIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registerBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(partName)
                    .addComponent(partLocator)
                    .addComponent(partQty)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(partPrice)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(updateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(hiddenId1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hiddenId, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(partBrandId, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hiddenId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hiddenId1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(partName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(partPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(partBrandId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(partQty, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(partLocator, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addBrand ab = new addBrand((JFrame) SwingUtilities.getWindowAncestor(this), true, this);
        ab.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        addLocation al = new addLocation((JFrame) SwingUtilities.getWindowAncestor(this), true, this);
        al.setVisible(true);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void partNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partNameFocusGained
        // TODO add your handling code here:

        if (partName.getText().equals("Part Name")) {
            partName.setText(null);
            removePlaceholderStyle(partName);
        }

    }//GEN-LAST:event_partNameFocusGained

    private void partNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partNameFocusLost
        // TODO add your handling code here:

        if (partName.getText().length() == 0) {
            addPlaceholderStyle(partName);
            partName.setText("Part Name");

        }
    }//GEN-LAST:event_partNameFocusLost

    private void partPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partPriceFocusGained
        // TODO add your handling code here:
        if (partPrice.getText().equals("Part Price")) {
            partPrice.setText(null);
            partPrice.requestFocus();
            removePlaceholderStyle(partPrice);
        }
    }//GEN-LAST:event_partPriceFocusGained

    private void partQtyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partQtyFocusGained
        // TODO add your handling code here:
        if (partQty.getText().equals("Part Quantity")) {
            partQty.setText(null);
            partQty.requestFocus();
            removePlaceholderStyle(partQty);
        }
    }//GEN-LAST:event_partQtyFocusGained

    private void partLocatorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partLocatorFocusGained
        // TODO add your handling code here:

        if (partLocator.getText().equals("Location Number")) {
            partLocator.setText(null);
            partLocator.requestFocus();
            removePlaceholderStyle(partLocator);
        }
    }//GEN-LAST:event_partLocatorFocusGained

    private void partPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partPriceFocusLost
        // TODO add your handling code here:

        if (partPrice.getText().length() == 0) {
            addPlaceholderStyle(partPrice);
            partPrice.setText("Part Price");

        }
    }//GEN-LAST:event_partPriceFocusLost

    private void partQtyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partQtyFocusLost
        // TODO add your handling code here:

        if (partQty.getText().length() == 0) {
            addPlaceholderStyle(partQty);
            partQty.setText("Part Quantity");

        }
    }//GEN-LAST:event_partQtyFocusLost

    private void partLocatorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partLocatorFocusLost
        // TODO add your handling code here:

        if (partLocator.getText().length() == 0) {
            addPlaceholderStyle(partLocator);
            partLocator.setText("Location Number");

        }
    }//GEN-LAST:event_partLocatorFocusLost

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed

        try {
            String name = partName.getText().trim();
            String price = partPrice.getText().trim();
            String brandNo = partBrandId.getText().trim();
            String brand = String.valueOf(jComboBox1.getSelectedItem());
            String qty = partQty.getText().trim();
            String locator = String.valueOf(jComboBox2.getSelectedItem());
            String locatorNo = partLocator.getText().trim();

            if (name.equals("Part Name") || price.equals("Part Price") || qty.equals("Part Quantity") || locatorNo.equals("Location Number") || brandNo.equals("Brand id")) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String brandId = brandMap.get(brand);
            String locatorId = locatorMap.get(locator);

            if (brandId == null || locatorId == null) {
                JOptionPane.showMessageDialog(this, "You Not select brand or locator.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String query3 = "SELECT * FROM `sub_location` WHERE `sub_location_id`='" + locatorNo + "' AND `locator_id` = '" + locatorId + "'";
            ResultSet resultSet2 = MySQL.execute(query3);

            if (!resultSet2.next()) {
                String query = "INSERT INTO `sub_location` (`sub_location_id`, `locator_id`) "
                        + "VALUES ('" + locatorNo + "', '" + locatorId + "')";
                MySQL.execute(query);

                ResultSet rsSub = MySQL.execute("SELECT `id` FROM `sub_location` WHERE `sub_location_id` = '" + locatorNo + "' AND `locator_id` = '" + locatorId + "'");
                int subLocationId = -1;
                if (rsSub.next()) {
                    subLocationId = rsSub.getInt("id");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to fetch sub-location ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String query2 = "INSERT INTO `product` (`name`, `price`, `qty`, `brandNo`,`sub_location_id`, `brand_id`) "
                        + "VALUES ('" + name + "', '" + price + "', '" + qty + "', '" + brandNo + "','" + subLocationId + "', '" + brandId + "')";
                MySQL.execute(query2);

                int option = JOptionPane.showConfirmDialog(
                        this,
                        "Spare Part added successfully!",
                        "Success",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE
                );

                if (option == JOptionPane.OK_OPTION) {
                    if (spearPart != null) {
                        spearPart.loadSpearParts();
                        spearPart.loadBrand();
                    }
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "This Location Number Already Exist ", "Warning", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Add Product Error", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_registerBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed

        try {
            String id = hiddenId.getText().trim();
            String name = partName.getText().trim();
            String price = partPrice.getText().trim();
            String brandNo = partBrandId.getText().trim();
            String brand = String.valueOf(jComboBox1.getSelectedItem());
            String qty = partQty.getText().trim();
            String locator = String.valueOf(jComboBox2.getSelectedItem());
            String locatorNo = partLocator.getText().trim();

            // Validation
            if (name.equals("Part Name") || price.equals("Part Price") || qty.equals("Part Quantity")
                    || locatorNo.equals("Location Number") || brandNo.equals("Brand id")) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String brandId = brandMap.get(brand);
            String locatorId = locatorMap.get(locator);

            if (brandId == null || locatorId == null) {
                JOptionPane.showMessageDialog(this, "You have not selected a valid brand or locator.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String query3 = "SELECT `sub_location_id` FROM `product` WHERE `id`='" + id + "'";
            ResultSet resultSet2 = MySQL.execute(query3);

            if (resultSet2.next()) {
                String subLocationIdFromProduct = resultSet2.getString("sub_location_id");

                ResultSet duplicateCheck = MySQL.execute(
                        "SELECT `id` FROM `sub_location` WHERE `sub_location_id`='" + locatorNo + "' AND `locator_id`='" + locatorId + "' AND `id` <> '" + subLocationIdFromProduct + "'"
                );

                if (duplicateCheck.next()) {
                    JOptionPane.showMessageDialog(this,
                            "This Locator ID already exists!",
                            "Duplicate Locator ID", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String queryUpdateSubLocation = "UPDATE `sub_location` SET `sub_location_id` = '" + locatorNo + "', `locator_id` = '" + locatorId + "' WHERE `id` = '" + subLocationIdFromProduct + "'";
                MySQL.execute(queryUpdateSubLocation);

                String query2 = "UPDATE `product` SET "
                        + "`name` = '" + name + "', "
                        + "`price` = '" + price + "', "
                        + "`brandNo` = '" + brandNo + "', "
                        + "`qty` = '" + qty + "', "
                        + "`sub_location_id` = '" + subLocationIdFromProduct + "', "
                        + "`brand_id` = '" + brandId + "' "
                        + "WHERE `id` = '" + id + "'";
                MySQL.execute(query2);

                JOptionPane.showMessageDialog(this, "Spare Part updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                if (spearPart != null) {
                    spearPart.loadSpearParts();
                    spearPart.loadBrand();
                }
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Product ID!", "Warning", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Update Product Error", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_updateBtnActionPerformed

    private void partBrandIdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partBrandIdFocusGained
        if (partBrandId.getText().equals("Brand id")) {
            partBrandId.setText(null);
            partBrandId.requestFocus();
            removePlaceholderStyle(partBrandId);
        }
    }//GEN-LAST:event_partBrandIdFocusGained

    private void partBrandIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partBrandIdFocusLost
        if (partBrandId.getText().length() == 0) {
            addPlaceholderStyle(partBrandId);
            partBrandId.setText("Brand id");

        }
    }//GEN-LAST:event_partBrandIdFocusLost

    private void partBrandIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partBrandIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_partBrandIdActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            String type = "add";
            AddSpearPart dialog = new AddSpearPart(new javax.swing.JFrame(), true, type, null);
            dialog.setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel hiddenId;
    private javax.swing.JLabel hiddenId1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JFormattedTextField partBrandId;
    private javax.swing.JTextField partLocator;
    private javax.swing.JTextField partName;
    private javax.swing.JFormattedTextField partPrice;
    private javax.swing.JTextField partQty;
    private javax.swing.JButton registerBtn;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
