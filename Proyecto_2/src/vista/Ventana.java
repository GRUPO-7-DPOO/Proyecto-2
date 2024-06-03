package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana {

    public static void main(String[] args) {
        // Crear la ventana principal
        JFrame mainFrame = new JFrame("Sistema de Subastas");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
        
        // Crear un panel principal con GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel label = new JLabel("Entrar como:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        mainPanel.add(label, gbc);
        
        gbc.gridwidth = 1; // Reset gridwidth
        gbc.gridy = 1; // Next row

        JButton adminButton = new JButton("Admin");
        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openLoginWindow("Admin", mainFrame);
            }
        });
        mainPanel.add(adminButton, gbc);
        
        JButton employeeButton = new JButton("Empleado");
        employeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openLoginWindow("Empleado", mainFrame);
            }
        });
        gbc.gridx = 1;
        mainPanel.add(employeeButton, gbc);
        
        JButton clientButton = new JButton("Cliente");
        clientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openLoginWindow("Cliente", mainFrame);
            }
        });
        gbc.gridx = 2;
        mainPanel.add(clientButton, gbc);
        
        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setVisible(true);
    }
    
    // Función para abrir la ventana de login
    public static void openLoginWindow(String userType, JFrame mainFrame) {
        JFrame loginFrame = new JFrame("Login - " + userType);
        loginFrame.setSize(300, 200);
        
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel userLabel = new JLabel("Usuario:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(userLabel, gbc);
        
        JTextField userField = new JTextField(15);
        gbc.gridx = 1;
        loginPanel.add(userField, gbc);
        
        JLabel passwordLabel = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);
        
        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);
        
        JButton createButton = new JButton("Crear usuario");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createUser(userType);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(createButton, gbc);
        
        JButton loginButton = new JButton("Ingresar");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openUserWindow(userField.getText(), mainFrame);
                loginFrame.dispose();
            }
        });
        gbc.gridy = 3;
        loginPanel.add(loginButton, gbc);
        
        loginFrame.getContentPane().add(loginPanel);
        loginFrame.setVisible(true);
    }
    
    // Función para crear un nuevo usuario
    public static void createUser(String userType) {
        JOptionPane.showMessageDialog(null, "Se ha creado un nuevo usuario de tipo: " + userType);
    }
    
    // Función para abrir la ventana del usuario
    public static void openUserWindow(String username, JFrame mainFrame) {
        JFrame userFrame = new JFrame("Bienvenido " + username);
        userFrame.setSize(300, 200);
        
        JPanel userPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel userLabel = new JLabel("Usuario: " + username);
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        userPanel.add(userLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        
        JButton piezasButton = new JButton("Piezas");
        piezasButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddItemWindow();
            }
        });
        userPanel.add(piezasButton, gbc);
        
        JButton subastasButton = new JButton("Subastas");
        gbc.gridx = 1;
        userPanel.add(subastasButton, gbc);
        
        JButton configuracionesButton = new JButton("Configuraciones");
        gbc.gridx = 2;
        userPanel.add(configuracionesButton, gbc);
        
        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userFrame.dispose();
                mainFrame.setVisible(true);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        userPanel.add(volverButton, gbc);
        
        userFrame.getContentPane().add(userPanel);
        userFrame.setVisible(true);
    }

    // Función para abrir la ventana de agregar pieza
    public static void openAddItemWindow() {
        JFrame addItemFrame = new JFrame("Agregar");
        addItemFrame.setSize(400, 300);
        
        JPanel addItemPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("Agregar");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        addItemPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        
        JLabel nameLabel = new JLabel("Nombre:");
        gbc.gridx = 0;
        addItemPanel.add(nameLabel, gbc);
        
        JTextField nameField = new JTextField(15);
        gbc.gridx = 1;
        addItemPanel.add(nameField, gbc);

        JLabel authorLabel = new JLabel("Autor:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        addItemPanel.add(authorLabel, gbc);

        JTextField authorField = new JTextField(15);
        gbc.gridx = 1;
        addItemPanel.add(authorField, gbc);

        JLabel dateLabel = new JLabel("Fecha:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        addItemPanel.add(dateLabel, gbc);

        JTextField dateField = new JTextField(15);
        gbc.gridx = 1;
        addItemPanel.add(dateField, gbc);

        JLabel typeLabel = new JLabel("Tipo:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        addItemPanel.add(typeLabel, gbc);

        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Impresion", "Fotografia", "Escultura", "Video", "Pintura"});
        gbc.gridx = 1;
        addItemPanel.add(typeComboBox, gbc);

        JLabel locationLabel = new JLabel("Ubicación:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        addItemPanel.add(locationLabel, gbc);

        JComboBox<String> locationComboBox = new JComboBox<>(new String[]{"Bodega", "Exposición"});
        gbc.gridx = 1;
        addItemPanel.add(locationComboBox, gbc);

        JButton confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar el código para guardar la pieza
                String name = nameField.getText();
                String author = authorField.getText();
                String date = dateField.getText();
                String type = (String) typeComboBox.getSelectedItem();
                String location = (String) locationComboBox.getSelectedItem();

                // Lógica para guardar la pieza
                System.out.println("Pieza guardada: " + name + ", " + author + ", " + date + ", " + type + ", " + location);

                // Cerrar la ventana
                addItemFrame.dispose();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        addItemPanel.add(confirmButton, gbc);

        addItemFrame.getContentPane().add(addItemPanel);
        addItemFrame.setVisible(true);
    }
}
