package org.ipchile.ui;

import org.ipchile.model.Contact;

import javax.swing.*;
import java.awt.*;

public class AddContactUI extends JFrame {

    private JTextField nameField, phoneField, emailField;
    private JLabel nameLabel, phoneLabel, emailLabel;
    private JButton addButton, cancelButton;

    public AddContactUI() {
        setSize(400, 180);
        setResizable(false);

        addButton = new JButton();
        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPane.add(cancelButton);
        buttonPane.add(addButton);

        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        JPanel fieldPane = new JPanel(new GridLayout(0, 1));
        labelPane.add(nameLabel = new JLabel("Nombre"));
        labelPane.add(phoneLabel = new JLabel("Teléfono"));
        labelPane.add(emailLabel = new JLabel("Email"));
        fieldPane.add(nameField = new JTextField(100));
        fieldPane.add(phoneField = new JTextField(100));
        fieldPane.add(emailField = new JTextField(100));

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.add(labelPane, BorderLayout.WEST);
        contentPane.add(fieldPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

    public Boolean validateFields() {
        if (phoneField.getText().isEmpty() && emailField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese al menos un teléfono o email");
            return false;
        }
        if (nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre");
            return false;
        }
        return true;
    }

    public void setFields(Contact contact) {
        nameField.setText(contact.getName());
        phoneField.setText(contact.getPhone());
        emailField.setText(contact.getEmail());
    }

    public void showAddContactUI(String title) {
        setLocationRelativeTo(this);
        setVisible(true);
        setTitle(title + " contacto");
        getAddButton().setText(title);
    }

    public String getNameField() {
        return nameField.getText();
    }

    public String getPhoneField() {
        return phoneField.getText();
    }

    public String getEmailField() {
        return emailField.getText();
    }

    public JButton getAddButton() {
        return addButton;
    }
}
