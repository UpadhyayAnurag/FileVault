package com.filevault;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.Node;

import java.io.File;
import java.util.Objects;

public class App extends Application {

    private PasswordField encryptionKeyField;
    private Button chooseFileBtn;
    private Button encryptBtn;
    private Button decryptBtn;
    private Label statusLabel;
    private Label selectedFileLabel;
    private File selectedFile;
    private VBox mainContainer;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FileVault");

        // Set application icon
        try {
            Image icon = new Image("/fileVaultLogo.png");
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Could not load application icon: " + e.getMessage());
        }

        // Create main layout
        createMainLayout();

        // Create scene with gradient background
        Scene scene = new Scene(mainContainer, 500, 650);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

        // Set window properties
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Add entrance animation
        playEntranceAnimation();
    }

    private void createMainLayout() {
        mainContainer = new VBox(25);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(40));
        mainContainer.getStyleClass().add("main-container");

        // Header section
        VBox headerSection = createHeaderSection();

        // File selection section
        VBox fileSection = createFileSection();

        // Encryption key section
        VBox keySection = createKeySection();

        // Action buttons section
        HBox buttonSection = createButtonSection();

        // Status section
        VBox statusSection = createStatusSection();

        mainContainer.getChildren().addAll(
                headerSection, fileSection, keySection, buttonSection, statusSection
        );
    }

    private VBox createHeaderSection() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("File Vault");
        titleLabel.getStyleClass().add("title-label");

        Label subtitleLabel = new Label("Secure File Encryption & Decryption");
        subtitleLabel.getStyleClass().add("subtitle-label");

        // Add security icon using Unicode
        Label iconLabel = new Label("🔐");
        iconLabel.setStyle("-fx-font-size: 48px;");

        header.getChildren().addAll(iconLabel, titleLabel, subtitleLabel);
        return header;
    }

    private VBox createFileSection() {
        VBox fileSection = new VBox(15);
        fileSection.setAlignment(Pos.CENTER);

        Label fileLabel = new Label("Select File");
        fileLabel.getStyleClass().add("section-label");

        chooseFileBtn = new Button("📁 Choose File");
        chooseFileBtn.getStyleClass().add("file-button");
        chooseFileBtn.setOnAction(e -> chooseFile());

        selectedFileLabel = new Label("No file selected");
        selectedFileLabel.getStyleClass().add("file-info-label");
        selectedFileLabel.setWrapText(true);
        selectedFileLabel.setMaxWidth(400);

        fileSection.getChildren().addAll(fileLabel, chooseFileBtn, selectedFileLabel);
        return fileSection;
    }

    private VBox createKeySection() {
        VBox keySection = new VBox(15);
        keySection.setAlignment(Pos.CENTER);

        Label keyLabel = new Label("Encryption Key");
        keyLabel.getStyleClass().add("section-label");

        encryptionKeyField = new PasswordField();
        encryptionKeyField.setPromptText("Enter your encryption key...");
        encryptionKeyField.getStyleClass().add("key-field");
        encryptionKeyField.setPrefWidth(350);

        keySection.getChildren().addAll(keyLabel, encryptionKeyField);
        return keySection;
    }

    private HBox createButtonSection() {
        HBox buttonSection = new HBox(20);
        buttonSection.setAlignment(Pos.CENTER);

        encryptBtn = new Button("🔒 Encrypt");
        encryptBtn.getStyleClass().add("encrypt-button");
        encryptBtn.setOnAction(e -> performEncryption());

        decryptBtn = new Button("🔓 Decrypt");
        decryptBtn.getStyleClass().add("decrypt-button");
        decryptBtn.setOnAction(e -> performDecryption());

        // Add hover animations
        addButtonHoverEffect(encryptBtn);
        addButtonHoverEffect(decryptBtn);

        buttonSection.getChildren().addAll(encryptBtn, decryptBtn);
        return buttonSection;
    }

    private VBox createStatusSection() {
        VBox statusSection = new VBox(10);
        statusSection.setAlignment(Pos.CENTER);

        Label statusTitleLabel = new Label("Status");
        statusTitleLabel.getStyleClass().add("section-label");

        statusLabel = new Label("Ready");
        statusLabel.getStyleClass().add("status-ready");

        statusSection.getChildren().addAll(statusTitleLabel, statusLabel);
        return statusSection;
    }

    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Encrypt/Decrypt");
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            selectedFileLabel.setText("📄 " + selectedFile.getName());
            selectedFileLabel.getStyleClass().clear();
            selectedFileLabel.getStyleClass().add("file-selected-label");
            updateStatus("File selected: " + selectedFile.getName(), "status-info");

            // Add selection animation
            playSelectionAnimation(selectedFileLabel);
        }
    }

    private void performEncryption() {
        if (validateInputs()) {
            updateStatus("🔄 Encrypting file...", "status-working");
            playProcessAnimation();

            // Simulate encryption process
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(2), e -> {
                        updateStatus("✅ File encrypted successfully!", "status-success");
                        playSuccessAnimation();
                    })
            );
            timeline.play();
        }
    }

    private void performDecryption() {
        if (validateInputs()) {
            updateStatus("🔄 Decrypting file...", "status-working");
            playReverseProcessAnimation();

            // Simulate decryption process
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(2), e -> {
                        updateStatus("✅ File decrypted successfully!", "status-success");
                        playSuccessAnimation();
                    })
            );
            timeline.play();
        }
    }

    private boolean validateInputs() {
        if (selectedFile == null) {
            updateStatus("❌ Please select a file first", "status-error");
            playErrorAnimation();
            return false;
        }

        if (encryptionKeyField.getText().trim().isEmpty()) {
            updateStatus("❌ Please enter an encryption key", "status-error");
            playErrorAnimation();
            return false;
        }

        return true;
    }

    private void updateStatus(String message, String styleClass) {
        statusLabel.setText(message);
        statusLabel.getStyleClass().clear();
        statusLabel.getStyleClass().add(styleClass);
    }

    // Animation methods
    private void playEntranceAnimation() {
        mainContainer.setOpacity(0);
        mainContainer.setTranslateY(30);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.8), mainContainer);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        TranslateTransition slideUp = new TranslateTransition(Duration.seconds(0.8), mainContainer);
        slideUp.setFromY(30);
        slideUp.setToY(0);

        ParallelTransition entrance = new ParallelTransition(fadeIn, slideUp);
        entrance.play();
    }

    private void playSelectionAnimation(Node node) {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(0.3), node);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(1.05);
        scale.setToY(1.05);
        scale.setCycleCount(2);
        scale.setAutoReverse(true);
        scale.play();
    }

    private void playProcessAnimation() {
        RotateTransition rotate = new RotateTransition(Duration.seconds(1), statusLabel);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setCycleCount(2);
        rotate.play();
    }

    private void playReverseProcessAnimation() {
        RotateTransition rotate = new RotateTransition(Duration.seconds(1), statusLabel);
        rotate.setByAngle(-360);
        //rotate.setToAngle(0);
        rotate.setCycleCount(2);
        rotate.play();
    }

    private void playSuccessAnimation() {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(0.4), statusLabel);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(1.2);
        scale.setToY(1.2);
        scale.setCycleCount(2);
        scale.setAutoReverse(true);
        scale.play();
    }

    private void playErrorAnimation() {
        TranslateTransition shake = new TranslateTransition(Duration.seconds(0.1), statusLabel);
        shake.setFromX(0);
        shake.setToX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        shake.play();
    }

    private void addButtonHoverEffect(Button button) {
        button.setOnMouseEntered(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.seconds(0.2), button);
            scale.setToX(1.05);
            scale.setToY(1.05);
            scale.play();
        });

        button.setOnMouseExited(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.seconds(0.2), button);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.play();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
