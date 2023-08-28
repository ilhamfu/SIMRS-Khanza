/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package document_filter;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author hp2k
 */
public abstract class SimpleDocumentListener implements DocumentListener{

    @Override
    public void insertUpdate(DocumentEvent e) {
        onChange(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        onChange(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        onChange(e);
    }
    
    public abstract void onChange(DocumentEvent e);
    
}
