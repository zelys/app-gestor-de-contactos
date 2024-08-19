package org.ipchile;

import org.ipchile.ui.AddContactUI;
import org.ipchile.ui.ContactManager;

import javax.swing.*;


public class AppContactManager {
    public static void main(String[] args) {
        AddContactUI addContactUI = new AddContactUI();
        ContactManager contactManager = new ContactManager(addContactUI);
        SwingUtilities.invokeLater(contactManager::showContactManagerUI);
    }
}