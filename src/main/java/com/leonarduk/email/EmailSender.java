package com.leonarduk.email;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class EmailSender {
	private static Logger logger = Logger.getLogger(EmailSender.class);

	public static void main(String[] args) {
		sendEmail("stephen@localhost", "Stephen", "test", "test text");
	}

	public static void sendEmail(String toEmail, String toName, String subject,
			String msgBody) {
		sendEmail("FreecycleItemFinder@leonarduk.com", "FreecycleItemFinder",
				toEmail, toName, subject, msgBody,true);
	}

	public static void sendEmail(String fromEmail, String fromName,
			String toEmail, String toName, String subject, String msgBody,
			boolean html) {
		logger.info("Sending to " + toEmail + " , " + toName + " :  " + subject);
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "localhost");

		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(fromEmail, fromName));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					toEmail, toName));
			msg.setSentDate(new Date());
			msg.setSubject(subject);
			if (html) {
				msg.setContent(msgBody, "text/html");
			} else {
				msg.setText(msgBody);
			}

			Transport.send(msg);

		} catch (MessagingException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendHtmlEmail(String host, String port, final String userName,
			final String password, String toAddress, String subject,
			String message, boolean asHtml) throws AddressException, MessagingException {

		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// creates a new session with an authenticator
		Authenticator auth = getAuthenticator(userName, password);

		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		msg.setContent(message, "text/html");
		if (asHtml) {
			msg.setContent(message, "text/html");
		} else {
			// set plain text message
			msg.setText(message);
		}
		// sends the e-mail
		Transport.send(msg);
	}

	public Authenticator getAuthenticator(final String userName,
			final String password) {
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		return auth;
	}
}
