/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pti.enigma.main;

import com.pti.enigma.connect.Connection;
import com.pti.enigma.login.FormLogin;
import com.pti.enigma.sendMail.SendMail;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author Agustinus Agri
 */
public class EnigmaMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new FormLogin().setVisible(true);
        
    }
    
    public static void testConnection() {
        Connection connect = null;
        try {
            connect = new Connection("testing1.pti@gmail.com", "proyekteknologiinformasi");
        } catch (MessagingException ex) {
            Logger.getLogger(EnigmaMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        SendMail sendMail = new SendMail(connect);
        try {
            sendMail.send("testing2.pti@gmail.com", "", "Hello No 1", "Hello No 1");
        } catch (MessagingException ex) {
            Logger.getLogger(EnigmaMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            connect.signOut();
        } catch (MessagingException ex) {
            Logger.getLogger(EnigmaMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
