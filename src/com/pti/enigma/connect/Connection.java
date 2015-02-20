/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pti.enigma.connect;

import com.sun.mail.smtp.SMTPTransport;
import java.security.Security;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author Agustinus Agri
 */
public class Connection {

    private String mHost;
    private String mUsername;
    private String mPassword;

    private Properties mProperties;
    private Session mSession;
    private SMTPTransport mSMTPTransport;
    private Store mStore;

    private final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    public Connection() {
    }

    public Connection(String mUsername, String mPassword) throws MessagingException {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        connectSMTP(mUsername, mPassword);
        connectIMAP(mUsername, mPassword);
    }

    public String getmHost() {
        return mHost;
    }

    public void setmHost(String mHost) {
        this.mHost = mHost;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public Properties getmProperties() {
        return mProperties;
    }

    public void setmProperties(Properties mProperties) {
        this.mProperties = mProperties;
    }

    public Session getmSession() {
        return mSession;
    }

    public void setmSession(Session mSession) {
        this.mSession = mSession;
    }

    public SMTPTransport getmSMTPTransport() {
        return mSMTPTransport;
    }

    public void setmSMTPTransport(SMTPTransport mSMTPTransport) {
        this.mSMTPTransport = mSMTPTransport;
    }

    public Store getmStore() {
        return mStore;
    }

    public void setmStore(Store mStore) {
        this.mStore = mStore;
    }

    public void connectSMTP(String username, String password) throws NoSuchProviderException, MessagingException {

        String host = "smtp.gmail.com";

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        //Utk saat ini hardcode, consider properties file - input pakai stream
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtps.host", host);
        properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtps.auth", "true");

        /*
         If set to false, the QUIT command is sent and the connection is immediately closed. If set 
         to true (the default), causes the transport to wait for the response to the QUIT command.

         ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
         http://forum.java.sun.com/thread.jspa?threadID=5205249
         smtpsend.java - demo program from javamail
         */
        properties.setProperty("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(properties, null);
        SMTPTransport smtpTransport = (SMTPTransport) session.getTransport("smtps");
        smtpTransport.connect(host, username, password);

        this.setmHost(host);
        this.setmUsername(username);
        this.setmPassword(password);
        this.setmProperties(properties);
        this.setmSession(session);
        this.setmSMTPTransport(smtpTransport);

    }

    public void connectIMAP(String username, String password) throws NoSuchProviderException, MessagingException {

        String host = "imap.gmail.com";
        String storeType = "imaps";

        //create properties field
        Properties properties = new Properties();

        properties.setProperty("mail.imap.host", host);
        properties.setProperty("mail.imap.port", "993");
        properties.setProperty("mail.imap.connectiontimeout", "5000");
        properties.setProperty("mail.imap.timeout", "5000");
        properties.put("mail.imaps.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties);

        //create the POP3 store object and connect with the pop server
        Store store = session.getStore(storeType);

        store.connect(host, username, password);
        
        this.setmStore(store);
        this.setmUsername(username);
        this.setmPassword(password);
        this.setmProperties(properties);
        this.setmSession(session);
    }
    
    public void signOut() throws MessagingException {
        this.getmSMTPTransport().close();
    }

}
