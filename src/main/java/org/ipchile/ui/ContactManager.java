package org.ipchile.ui;

import org.ipchile.model.Contact;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ContactManager extends JFrame {
    private ArrayList<Contact> contactsList;
    private JTable tableContacts;
    private DefaultTableModel tableModel;
    private JButton addButton, removeButton, editButton;
    private AddContactUI addContactUI;

    public ContactManager(AddContactUI addContactUI) {
        this.addContactUI = addContactUI;
        setTitle("Gestor de Contactos");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                refreshContacts();
            }
        });

        contactsList = new ArrayList<>();
        contactsList.add(new Contact("Juan Perez", "434343", "juanperez@gmail.com"));
        contactsList.add(new Contact("Pedro Gomez", "232323", "pedrogomez@mail.com"));

        addButton = new JButton("Agregar Contacto");
        addButton.addActionListener(e -> addContact());

        editButton = new JButton("Editar Contacto");
        editButton.addActionListener(e -> editContact());

        removeButton = new JButton("Eliminar Contacto");
        removeButton.addActionListener(e -> removeContact());

        tableContacts = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableContacts);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private void addContact() {
        addContactUI.showAddContactUI("Agregar");
        addContactUI.getAddButton().addActionListener(e -> {
            if (addContactUI.validateFields()) {
                Contact newContact = new Contact();
                newContact.setName(addContactUI.getNameField());
                newContact.setPhone(addContactUI.getPhoneField());
                newContact.setEmail(addContactUI.getEmailField());
                if (!existsContact(newContact)) {
                    contactsList.add(newContact);
                    refreshContacts();
                    addContactUI.dispose();
                }
            }
        });
    }

    private void editContact() {
        int selectedRow = getIndexRow();
        if (selectedRow != -1) {
            var contact = contactsList.get(selectedRow);
            addContactUI.showAddContactUI("Editar");
            if (contact != null) {
                addContactUI.setFields(contact);
                addContactUI.getAddButton().addActionListener(e -> {
                    if (addContactUI.validateFields()) {
                        contact.setName(addContactUI.getNameField());
                        contact.setPhone(addContactUI.getPhoneField());
                        contact.setEmail(addContactUI.getEmailField());
                        contactsList.set(selectedRow, contact);
                        refreshContacts();
                        addContactUI.dispose();
                    }
                });
            }
        }
    }

    private void removeContact() {
        if (getIndexRow() != -1) {
            var name = contactsList.get(getIndexRow()).getName();
            contactsList.remove(getIndexRow());
            refreshContacts();
            JOptionPane.showMessageDialog(this, "Contacto "+name+" eliminado");
        }
    }

    private Boolean existsContact(Contact contact) {
        boolean exists = contactsList.stream().anyMatch(c -> c.getEmail().equalsIgnoreCase(contact.getEmail()) && !c.getEmail().isEmpty()
                || c.getPhone().equalsIgnoreCase(contact.getPhone()) && !c.getPhone().isEmpty());
        if (exists) {
            JOptionPane.showMessageDialog(addContactUI, "La información del contacto ya existe");
            return true;
        }
        return false;
    }

    private void refreshContacts() {
        tableModel = new DefaultTableModel(new Object[] {"NOMBRE", "TELÉFONO", "EMAIL"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        if (contactsList != null) {
            for (Contact c : contactsList) {
                tableModel.addRow(new Object[]{c.getName(), c.getPhone(), c.getEmail()});
            }
        }
        tableContacts.setModel(tableModel);
        tableContacts.setAutoResizeMode(0);
        TableColumnModel columnModel = tableContacts.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(80);
        columnModel.getColumn(2).setPreferredWidth(150);
    }

    private int getIndexRow() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(addContactUI, "La lista de contactos esta vacía");
            return -1;
        }
        if (tableContacts.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(addContactUI, "Debe seleccionar una contacto");
            return -1;
        }
        return tableContacts.getSelectedRow();
    }

    public void showContactManagerUI() {
        setVisible(true);
    }
}
