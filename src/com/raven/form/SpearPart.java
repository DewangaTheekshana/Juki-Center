/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import Model.MySQL;
import com.raven.model.StockTableCellRenderer;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Pattern;
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
    HashMap<String, String> locatorMap = new HashMap<>();

    /**
     * Creates new form SpearPart
     */
    public SpearPart() {
        initComponents();
        setOpaque(false);
        loadSpearParts();
        loadBrand();
        loadLocator();

        registerBtn.setVisible(true);
        updateBtn.setVisible(false);

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

    public void reset2() {

        partName.setText("");
        partPrice.setText("");
        partBrandId.setText("");
        jComboBox2.setSelectedIndex(0);
        partQty.setText("");
        jComboBox3.setSelectedIndex(0);
        partLocator.setText("");
        partLocator2.setText("");

    }

    public void loadSpearParts() {
        try {
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            ResultSet rs = MySQL.execute(
                    "SELECT * FROM `product` "
                    + "INNER JOIN `sub_location` ON `sub_location`.`id` = `product`.`sub_location_id` "
                    + "INNER JOIN `locator` ON `locator`.`id` = `sub_location`.`locator_id` "
                    + "INNER JOIN `brand` ON `brand`.`id` = `product`.`brand_id` "
                    + "ORDER BY product.id"
            );

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

            // ✅ Apply custom renderer ONCE after rows are added
            for (int i = 0; i < jTable2.getColumnCount(); i++) {
                jTable2.getColumnModel().getColumn(i).setCellRenderer(new StockTableCellRenderer());
            }

        } catch (Exception e) {
            e.printStackTrace(); // ✅ Don't swallow errors
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
            jComboBox2.setModel(new DefaultComboBoxModel(v));
            jComboBox2.setSelectedIndex(0);

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

            jComboBox3.setModel(new DefaultComboBoxModel(v));
            jComboBox3.setSelectedIndex(0);

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
            filters.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(searchText), 1));
        }

        if (!searchBrandId.isEmpty() && !searchBrandId.equals("Search Brand Id")) {
            filters.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(searchBrandId), 2));
        }

        if (selectedBrand != null && !selectedBrand.equals("Select Brand")) {
            filters.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(selectedBrand), 3));
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
        jPanel2 = new javax.swing.JPanel();
        ItemSearch = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        ItemSearch1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new com.raven.swing.TableColumn();
        scrollBar1 = new com.raven.swing.ScrollBar();
        buttonCustom1 = new com.raven.swing.ButtonCustom();
        jButton2 = new javax.swing.JButton();
        panelRound1 = new com.raven.swing.PanelRound();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        partName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        partPrice = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        partBrandId = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        partQty = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        partLocator = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        registerBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        hidden_id = new javax.swing.JLabel();
        partLocator2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        panelRound2 = new com.raven.swing.PanelRound();
        jPanel4 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(245, 245, 245));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Lucida Fax", 1, 36)); // NOI18N
        jLabel2.setText("Spear Parts");

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Lucida Fax", 1, 52)); // NOI18N
        jLabel3.setText("JUKI CENTER");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1547, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable2KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable2);

        scrollBar1.setBackground(new java.awt.Color(245, 245, 245));

        jButton2.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        panelRound1.setBackground(new java.awt.Color(235, 235, 235));

        jPanel1.setBackground(new java.awt.Color(235, 235, 235));

        jLabel5.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jLabel5.setText("Part Name");

        partName.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jLabel6.setText("Part Price");

        partPrice.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jLabel7.setText("Brand No");

        partBrandId.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jLabel8.setText("Select Brand");

        jLabel9.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jLabel9.setText("Part Quantity");

        partQty.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton4.setText("Add Brand");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jLabel10.setText("Select Location");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jButton5.setText("Add Location");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        partLocator.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jLabel11.setText("Location Number");

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

        hidden_id.setForeground(new java.awt.Color(255, 255, 255));

        partLocator2.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(partName)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(partPrice)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(partBrandId)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(partQty)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5))
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(updateBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hidden_id, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(partLocator, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(partLocator2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hidden_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(partName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(partPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(partBrandId, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(partQty, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(partLocator, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(partLocator2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 325, Short.MAX_VALUE)
                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        panelRound2.setBackground(new java.awt.Color(235, 235, 235));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1183, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

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
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addContainerGap())
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1195, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(7, 7, 7)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jButton1)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel4))))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap(42, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addGap(135, 135, 135)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2)
                        .addGap(22, 22, 22)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ItemSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ItemSearch1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        reset();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed

    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed

        try {
            String name = partName.getText().trim();
            String price = partPrice.getText().trim();
            String brandNo = partBrandId.getText().trim();
            String brand = String.valueOf(jComboBox2.getSelectedItem());
            String qty = partQty.getText().trim();
            String locator = String.valueOf(jComboBox3.getSelectedItem());
            String locatorNo = partLocator.getText().trim();
            String locatorNo2 = partLocator2.getText().trim();

            if (name.equals("") || price.equals("") || qty.equals("") || locatorNo.equals("") || brandNo.equals("") || locatorNo2.equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String brandId = brandMap.get(brand);
            String locatorId = locatorMap.get(locator);

            if (brandId == null || locatorId == null) {
                JOptionPane.showMessageDialog(this, "You Not select brand or locator.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String query3 = "SELECT * FROM `sub_location` WHERE `sub_location_id`='" + locatorNo + " : " + locatorNo2 + "' AND `locator_id` = '" + locatorId + "'";
            ResultSet resultSet2 = MySQL.execute(query3);

            if (!resultSet2.next()) {
                String query = "INSERT INTO `sub_location` (`sub_location_id`, `locator_id`) "
                        + "VALUES ('" + locatorNo + " : " + locatorNo2 + "', '" + locatorId + "')";
                MySQL.execute(query);

                ResultSet rsSub = MySQL.execute("SELECT `id` FROM `sub_location` WHERE `sub_location_id` = '" + locatorNo + " : " + locatorNo2 + "' AND `locator_id` = '" + locatorId + "'");
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

                loadSpearParts();
                loadBrand();

                reset2();

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
            String id = hidden_id.getText().trim();
            String name = partName.getText().trim();
            String price = partPrice.getText().trim();
            String brandNo = partBrandId.getText().trim();
            String brand = String.valueOf(jComboBox2.getSelectedItem());
            String qty = partQty.getText().trim();
            String locator = String.valueOf(jComboBox3.getSelectedItem());
            String locatorNo = partLocator.getText().trim();
            String locatorNo2 = partLocator2.getText().trim();

            // Validation
            if (name.equals("") || price.equals("") || qty.equals("")
                    || locatorNo.equals("") || locatorNo2.equals("") || brandNo.equals("")) {
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
                        "SELECT `id` FROM `sub_location` WHERE `sub_location_id`='" + locatorNo + " : " + locatorNo2 + "' AND `locator_id`='" + locatorId + "' AND `id` <> '" + subLocationIdFromProduct + "'"
                );

                if (duplicateCheck.next()) {
                    JOptionPane.showMessageDialog(this,
                            "This Locator ID already exists!",
                            "Duplicate Locator ID", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String queryUpdateSubLocation = "UPDATE `sub_location` SET `sub_location_id` = '" + locatorNo + " : " + locatorNo2 + "', `locator_id` = '" + locatorId + "' WHERE `id` = '" + subLocationIdFromProduct + "'";
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

                loadSpearParts();
                loadBrand();
                registerBtn.setVisible(true);
                updateBtn.setVisible(false);
                reset2();

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Product ID!", "Warning", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Update Product Error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_updateBtnActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int row = jTable2.getSelectedRow();

        if (evt.getClickCount() == 2) {

            registerBtn.setVisible(false);
            updateBtn.setVisible(true);

            String locatorCombined = String.valueOf(jTable2.getValueAt(row, 6));
            String[] parts = locatorCombined.split(" : ");

            hidden_id.setText(String.valueOf(jTable2.getValueAt(row, 0)));
            partName.setText(String.valueOf(jTable2.getValueAt(row, 1)));
            partPrice.setText(String.valueOf(jTable2.getValueAt(row, 5)));
            partBrandId.setText(String.valueOf(jTable2.getValueAt(row, 2)));
            jComboBox2.setSelectedItem(String.valueOf(jTable2.getValueAt(row, 3)));
            partQty.setText(String.valueOf(jTable2.getValueAt(row, 4)));

            if (parts.length == 3) {
                jComboBox3.setSelectedItem(parts[0]); // locator_id
                partLocator.setText(parts[1]);        // sub_location_id
                partLocator2.setText(parts[2]);
            } else {
                JOptionPane.showMessageDialog(this, "invalid Locator", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        addBrand ab = new addBrand((JFrame) SwingUtilities.getWindowAncestor(this), true, this);
        ab.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        addLocation al = new addLocation((JFrame) SwingUtilities.getWindowAncestor(this), true, this);
        al.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTable2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyReleased

    }//GEN-LAST:event_jTable2KeyReleased

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            evt.consume();

            int selectedRow = jTable2.getSelectedRow();
            String qty = jTable2.getValueAt(selectedRow, 4).toString();
            String name = jTable2.getValueAt(selectedRow, 1).toString();
            
            if (qty.equals("Out Of Stock") || qty.equals("0")) {
                JOptionPane.showMessageDialog(this, "Out Of Stock : "+name, "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (selectedRow != -1) {

                BuyQty al = new BuyQty((JFrame) SwingUtilities.getWindowAncestor(this), true, this, jTable2.getValueAt(selectedRow, 0).toString());
                al.setVisible(true);

            } else{
                JOptionPane.showMessageDialog(this, "Select Row", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jTable2KeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ItemSearch;
    private javax.swing.JTextField ItemSearch1;
    private com.raven.swing.ButtonCustom buttonCustom1;
    private javax.swing.JLabel hidden_id;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private com.raven.swing.TableColumn jTable2;
    private com.raven.swing.PanelRound panelRound1;
    private com.raven.swing.PanelRound panelRound2;
    private javax.swing.JTextField partBrandId;
    private javax.swing.JTextField partLocator;
    private javax.swing.JTextField partLocator2;
    private javax.swing.JTextField partName;
    private javax.swing.JTextField partPrice;
    private javax.swing.JTextField partQty;
    private javax.swing.JButton registerBtn;
    private com.raven.swing.ScrollBar scrollBar1;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
