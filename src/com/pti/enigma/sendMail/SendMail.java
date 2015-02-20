/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pti.enigma.sendMail;

import com.pti.enigma.connect.Connection;
import java.util.Date;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Agustinus Agri
 */
public class SendMail {
    
    private String mRecipientEmail;
    private String mSubject;
    private String mMessage;
    
    private Connection mConnect;

    public SendMail() {
    }

    public SendMail(Connection mConnect) {
        this.mConnect = mConnect;
    }

    public String getmRecipientEmail() {
        return mRecipientEmail;
    }

    public void setmRecipientEmail(String mRecipientEmail) {
        this.mRecipientEmail = mRecipientEmail;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public Connection getmConnect() {
        return mConnect;
    }

    public void setmConnect(Connection mConnect) {
        this.mConnect = mConnect;
    }
    
    public void send(String recipientEmail, String ccEmail, String subject, String message) throws MessagingException {
        MimeMessage msg = new MimeMessage(getmConnect().getmSession());
        msg.setFrom(new InternetAddress(getmConnect().getmUsername()));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, true));
        
        if (ccEmail != null || ccEmail.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
        }
        
        msg.setSubject(subject);
        msg.setText(message, "UTF-8");
        msg.setSentDate(new Date());

        getmConnect().getmSMTPTransport().sendMessage(msg, msg.getAllRecipients());
    }
    
}
