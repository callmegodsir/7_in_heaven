package org.example.jvprojet711;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;

public class SevenElevenShop extends Application {
    private Map<String, Double> products = new HashMap<>();
    private Map<String, Integer> cart = new HashMap<>();
    private VBox cartBox = new VBox(10);
    private Label totalLabel = new Label("Total: 0.00€");
    private VBox productDisplayArea = new VBox(10);
    private BorderPane root = new BorderPane();

    @Override
    public void start(Stage stage) {
        // Configuration de la fenêtre en plein écran
        stage.initStyle(StageStyle.DECORATED);
        stage.setMaximized(true);

        initializeProducts();
        setupUI();

        Scene scene = new Scene(root);
        stage.setTitle("7-Eleven Shop");
        stage.setScene(scene);
        stage.show();
    }

    private void setupUI() {
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Header avec logo
        HBox header = createHeader();
        root.setTop(header);

        // Navigation par catégories
        HBox categoryNav = createCategoryNavigation();

        // Zone produits avec scroll
        ScrollPane productScroll = new ScrollPane(productDisplayArea);
        productScroll.setFitToWidth(true);
        productScroll.setStyle("-fx-background: #ffffff; -fx-border-color: #e0e0e0;");
        productScroll.setPrefHeight(Region.USE_COMPUTED_SIZE);

        VBox leftContainer = new VBox(10, categoryNav, productScroll);
        leftContainer.setPadding(new Insets(10));

        // Panier
        VBox cartContainer = createCartContainer();

        root.setCenter(leftContainer);
        root.setRight(cartContainer);

        showProductsByCategory("Snacks");
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setStyle("-fx-background-color: #ffffff; -fx-padding: 15; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");
        header.setAlignment(Pos.CENTER_LEFT);

        // Logo 7-Eleven
        ImageView logo = new ImageView(new Image("file:logo7inheaven.png"));
        logo.setFitHeight(80);
        logo.setPreserveRatio(true);

        // Navigation header - maintenant directement à droite du logo
        HBox navBox = new HBox(20);
        navBox.setAlignment(Pos.CENTER_LEFT);
        navBox.setPadding(new Insets(0, 0, 0, 20)); // Marge gauche pour séparer du logo

        Button trewards = createNavButton("TREWARDS");
        Button delivery = createNavButton("Order 7NOW Delivery");
        Button tcollection = createNavButton("Shop TCollection");

        navBox.getChildren().addAll(trewards, delivery, tcollection);

        header.getChildren().addAll(logo, navBox);
        return header;
    }

    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ff0000; -fx-font-weight: bold; -fx-border-color: #ff0000; -fx-border-radius: 3;");
        return btn;
    }

    private HBox createCategoryNavigation() {
        HBox categoryNav = new HBox(10);
        categoryNav.setPadding(new Insets(10));
        categoryNav.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 5; -fx-border-color: #e0e0e0;");

        String[] categories = {"Snacks", "Boissons", "Sandwichs", "Sucreries", "Glaces"};

        for (String category : categories) {
            Button catButton = new Button(category);
            catButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5;");
            catButton.setOnAction(e -> showProductsByCategory(category));
            categoryNav.getChildren().add(catButton);
        }

        return categoryNav;
    }

    private VBox createCartContainer() {
        VBox cartContainer = new VBox(15);
        cartContainer.setPadding(new Insets(20));
        cartContainer.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #ccc; -fx-border-width: 0 0 0 1px;");
        cartContainer.setPrefWidth(300);

        Label cartTitle = new Label("🛒 Votre panier");
        cartTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ff0000;");

        cartBox.setStyle("-fx-padding: 10;");

        Button checkoutButton = new Button("Commander");
        checkoutButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        checkoutButton.setMaxWidth(Double.MAX_VALUE);
        checkoutButton.setOnAction(e -> showAlert("Commande validée", "Merci pour votre achat chez 7-Eleven !"));

        cartContainer.getChildren().addAll(cartTitle, cartBox, totalLabel, checkoutButton);
        return cartContainer;
    }

    private void initializeProducts() {
        // Images des produits (remplacer par vos chemins d'images)
        products.put("7-Select Kettle Chips Spicy Jalapeno", 2.99);
        products.put("7-Select Loco Rollers Chili Lime", 3.49);
        products.put("7-Select Loco Rollers Habanero Lime", 3.49);
        products.put("Bugles Crispy Corn Snacks", 1.99);
        products.put("CLIP® BAR Chocolate Chip", 1.49);
        products.put("Slurpee Coca-Cola", 1.99);
        products.put("Café 7-Eleven", 1.49);
        products.put("Sandwich Thon Mayo", 3.99);
        products.put("Donut Chocolat", 1.29);
        products.put("Cornet Vanille", 2.49);
    }

    private void showProductsByCategory(String category) {
        productDisplayArea.getChildren().clear();

        Label categoryTitle = new Label(category.toUpperCase());
        categoryTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ff0000;");
        productDisplayArea.getChildren().add(categoryTitle);

        // Grille de produits
        GridPane productGrid = new GridPane();
        productGrid.setHgap(20);
        productGrid.setVgap(20);
        productGrid.setPadding(new Insets(10));

        int col = 0, row = 0;

        switch (category) {
            case "Snacks":
                addProductToGrid(productGrid, col++, row, "7-Select Kettle Chips Spicy Jalapeno", "file:chipsspicyjala.png");
                addProductToGrid(productGrid, col++, row, "7-Select Loco Rollers Chili Lime", "file:loco.jpeg");
                if (col > 2) { col = 0; row++; }
                addProductToGrid(productGrid, col++, row, "7-Select Loco Rollers Habanero Lime", "file:loco_habanero.png");
                addProductToGrid(productGrid, col++, row, "Bugles Crispy Corn Snacks", "file:bugles.png");
                break;
            case "Boissons":
                addProductToGrid(productGrid, col++, row, "Slurpee Coca-Cola", "file:slurpee.png");
                addProductToGrid(productGrid, col++, row, "Café 7-Eleven", "file:coffee.png");
                break;
            case "Sandwichs":
                addProductToGrid(productGrid, col++, row, "Sandwich Thon Mayo", "file:sandwich_thon.png");
                break;
            case "Sucreries":
                addProductToGrid(productGrid, col++, row, "Donut Chocolat", "file:donut.png");
                break;
            case "Glaces":
                addProductToGrid(productGrid, col++, row, "Cornet Vanille", "file:ice_cream.png");
                break;
        }

        productDisplayArea.getChildren().add(productGrid);
    }

    private void addProductToGrid(GridPane grid, int col, int row, String productName, String imagePath) {
        double price = products.get(productName);

        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-radius: 5;");
        card.setAlignment(Pos.CENTER);

        // Image du produit
        ImageView productImage = new ImageView(new Image(imagePath));
        productImage.setFitHeight(120);
        productImage.setPreserveRatio(true);

        Label nameLabel = new Label(productName);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-alignment: center;");
        nameLabel.setMaxWidth(150);
        nameLabel.setWrapText(true);

        Label priceLabel = new Label(String.format("%.2f€", price));
        priceLabel.setStyle("-fx-text-fill: #ff0000; -fx-font-weight: bold;");

        Button addButton = new Button("Ajouter");
        addButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");
        addButton.setOnAction(e -> addToCart(productName, price));

        card.getChildren().addAll(productImage, nameLabel, priceLabel, addButton);
        grid.add(card, col, row);
    }

    private void addToCart(String name, double price) {
        cart.put(name, cart.getOrDefault(name, 0) + 1);
        updateCartView();

        ScaleTransition scale = new ScaleTransition(Duration.millis(300), totalLabel);
        scale.setFromX(1);
        scale.setToX(1.2);
        scale.setFromY(1);
        scale.setToY(1.2);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
    }

    private void updateCartView() {
        cartBox.getChildren().clear();
        double total = 0;

        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            double price = products.get(itemName) * quantity;
            total += price;

            HBox itemBox = new HBox(10);
            itemBox.setAlignment(Pos.CENTER_LEFT);

            // Partie nom et quantité avec largeur fixe
            Label itemLabel = new Label(String.format("%s x%d", itemName, quantity));
            itemLabel.setStyle("-fx-font-size: 14px;");
            itemLabel.setMaxWidth(180); // Largeur fixe pour le nom
            itemLabel.setMinWidth(180);
            itemLabel.setWrapText(true); // Retour à la ligne si trop long

            // Partie prix avec style
            Label priceLabel = new Label(String.format("%.2f€", price));
            priceLabel.setStyle("-fx-text-fill: #ff0000; -fx-font-weight: bold; -fx-min-width: 60;");

            // Bouton suppression
            Button removeBtn = new Button("❌");
            removeBtn.setStyle("-fx-background-color: transparent; -fx-padding: 0 5 0 5;");

            // Configuration de la HBox
            itemBox.getChildren().addAll(itemLabel, priceLabel, removeBtn);
            HBox.setHgrow(itemLabel, Priority.NEVER); // Empêche l'étirement
            HBox.setHgrow(priceLabel, Priority.NEVER);

            removeBtn.setOnAction(e -> {
                cart.put(itemName, cart.get(itemName) - 1);
                if (cart.get(itemName) == 0) cart.remove(itemName);
                updateCartView();
            });

            cartBox.getChildren().add(itemBox);
        }

        totalLabel.setText(String.format("Total: %.2f€", total));
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ff0000;");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}