import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ECommerceSystem {

    private JFrame frame;
    private JPanel productPanel, cartPanel;
    private JTextArea cartTextArea;
    private JLabel totalPriceLabel;
    private List<Product> products;
    private List<Product> cart;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ECommerceSystem().createAndShowGUI());
    }

    public void createAndShowGUI() {
        products = new ArrayList<>();
        cart = new ArrayList<>();

        // Sample Products
        products.add(new Product("Laptop", 1000.00));
        products.add(new Product("Smartphone", 500.00));
        products.add(new Product("Headphones", 150.00));
        products.add(new Product("Smartwatch", 200.00));

        frame = new JFrame("Simple E-commerce System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Product Panel
        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(products.size(), 2));
        for (Product product : products) {
            JButton productButton = new JButton(product.getName() + " - $" + product.getPrice());
            productButton.addActionListener(e -> addToCart(product));
            productPanel.add(productButton);
        }
        JScrollPane productScroll = new JScrollPane(productPanel);
        frame.add(productScroll, BorderLayout.NORTH);

        // Cart Panel
        cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout());

        cartTextArea = new JTextArea(10, 30);
        cartTextArea.setEditable(false);
        JScrollPane cartScroll = new JScrollPane(cartTextArea);
        cartPanel.add(cartScroll, BorderLayout.CENTER);

        totalPriceLabel = new JLabel("Total: $0.00", SwingConstants.CENTER);
        cartPanel.add(totalPriceLabel, BorderLayout.SOUTH);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> checkout());
        cartPanel.add(checkoutButton, BorderLayout.NORTH);

        frame.add(cartPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void addToCart(Product product) {
        cart.add(product);
        updateCart();
    }

    private void updateCart() {
        cartTextArea.setText("");
        double total = 0.0;
        for (Product product : cart) {
            cartTextArea.append(product.getName() + " - $" + product.getPrice() + "\n");
            total += product.getPrice();
        }

        // Applying discount if the total price is above $1000
        if (total > 1000) {
            total *= 0.9; // Apply 10% discount
            totalPriceLabel.setText("Total after 10% discount: $" + String.format("%.2f", total));
        } else {
            totalPriceLabel.setText("Total: $" + String.format("%.2f", total));
        }
    }

    private void checkout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Your cart is empty!", "Checkout Error", JOptionPane.ERROR_MESSAGE);
        } else {
            double total = 0.0;
            for (Product product : cart) {
                total += product.getPrice();
            }

            // Apply discount
            if (total > 1000) {
                total *= 0.9; // 10% discount
            }

            String message = "Your total is: $" + String.format("%.2f", total) + "\nProceed with payment?";
            int option = JOptionPane.showConfirmDialog(frame, message, "Checkout", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frame, "Order placed successfully! Thank you for shopping with us.", "Order Confirmed", JOptionPane.INFORMATION_MESSAGE);
                cart.clear();
                updateCart();
            } else {
                JOptionPane.showMessageDialog(frame, "Order canceled.", "Order Canceled", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    static class Product {
        private String name;
        private double price;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }
}
