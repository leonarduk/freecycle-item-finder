package com.leonarduk.email;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
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
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.log4j.Logger;

public class EmailSender {
	private static Logger logger = Logger.getLogger(EmailSender.class);

	public static void main(String[] args) {
		sendEmail("test", "test text", "steveleonard11@gmail.com");
	}

	public static void sendEmail(String subject, String msgBody, String... to) {
		sendEmail("FreecycleItemFinder@leonarduk.com", "FreecycleItemFinder",
				subject, msgBody, true, to);
	}

	public static void sendEmail(String fromEmail, String fromName,
			String subject, String msgBody, boolean html, String... to) {
		logger.info("Sending to " + Arrays.asList(to) + " :  " + subject);
		Properties props = new Properties();
		// props.setProperty("mail.smtp.host", "localhost");

		props.put("mail.smtp.host", "warthog.acenet-inc.net");
		// If you want to use SSL
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		String user = "leonard";
		props.put("mail.stmp.user", user);
		// If you want you use TLS
		props.put("mail.smtp.auth", "true");

		props.put("mail.smtp.starttls.enable", "true");
		String password = "SW179TNKT26LJ";
		props.put("mail.smtp.password", password);
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
				});

		try {
			Message msg = new MimeMessage(session);
			InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				addressTo[i] = new InternetAddress(to[i]);
			}
			msg.setRecipients(RecipientType.TO, addressTo);

			msg.setFrom(new InternetAddress(fromEmail, fromName));
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
			String message, boolean asHtml) throws AddressException,
			MessagingException {

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
