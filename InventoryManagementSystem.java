import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InventoryManagementSystem extends JFrame {
    // Components
    private JTable inventoryTable;
    private JTextField itemNameField, itemPriceField, itemQuantityField;
    private JButton addButton, updateButton, removeButton, calculateButton;
    private JLabel totalValueLabel;
    
    // Data structure to store inventory items
    private ArrayList<Item> inventory;
    
    public InventoryManagementSystem() {
        inventory = new ArrayList<>();
        
        // Initialize the frame
        setTitle("Inventory Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize components
        itemNameField = new JTextField(15);
        itemPriceField = new JTextField(15);
        itemQuantityField = new JTextField(15);
        addButton = new JButton("Add Item");
        updateButton = new JButton("Update Item");
        removeButton = new JButton("Remove Item");
        calculateButton = new JButton("Calculate Total");
        totalValueLabel = new JLabel("Total Value: $0.00");
        
        // Initialize table for displaying inventory
        String[] columnNames = {"Item Name", "Price", "Quantity", "Total Value"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        inventoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        
        // Set layout
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Item Name:"));
        panel.add(itemNameField);
        panel.add(new JLabel("Price:"));
        panel.add(itemPriceField);
        panel.add(new JLabel("Quantity:"));
        panel.add(itemQuantityField);
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(removeButton);
        panel.add(calculateButton);
        panel.add(totalValueLabel);
        
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });
        
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeItem();
            }
        });
        
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotal();
            }
        });
    }
    
    // Add item to inventory
    private void addItem() {
        String name = itemNameField.getText();
        double price = Double.parseDouble(itemPriceField.getText());
        int quantity = Integer.parseInt(itemQuantityField.getText());
        
        Item newItem = new Item(name, price, quantity);
        inventory.add(newItem);
        
        updateTable();
    }
    
    // Update item in inventory
    private void updateItem() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow != -1) {
            String name = itemNameField.getText();
            double price = Double.parseDouble(itemPriceField.getText());
            int quantity = Integer.parseInt(itemQuantityField.getText());
            
            Item updatedItem = inventory.get(selectedRow);
            updatedItem.setName(name);
            updatedItem.setPrice(price);
            updatedItem.setQuantity(quantity);
            
            updateTable();
        }
    }
    
    // Remove item from inventory
    private void removeItem() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow != -1) {
            inventory.remove(selectedRow);
            updateTable();
        }
    }
    
    // Update the inventory table
    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
        model.setRowCount(0); // Clear existing data
        
        for (Item item : inventory) {
            model.addRow(new Object[]{
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity(),
                    item.getTotalValue()
            });
        }
    }
    
    // Calculate the total value of the inventory
    private void calculateTotal() {
        double totalValue = 0;
        for (Item item : inventory) {
            totalValue += item.getTotalValue();
        }
        totalValueLabel.setText("Total Value: $" + totalValue);
    }
    
    // Item class representing an inventory item
    class Item {
        private String name;
        private double price;
        private int quantity;
        
        public Item(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getTotalValue() {
            return price * quantity;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InventoryManagementSystem frame = new InventoryManagementSystem();
                frame.setVisible(true);
            }
        });
    }
}
