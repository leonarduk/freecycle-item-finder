package com.leonarduk.itemfinder;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	public static void sendEmail(String toEmail, String toName, String subject,
			String msgBody) {
		sendEmail("FreecycleItemFinder@leonarduk.com", "FreecycleItemFinder",
				toEmail, toName, subject, msgBody);
	}

	public static void sendEmail(String fromEmail, String fromName,
			String toEmail, String toName, String subject, String msgBody) {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "localhost");

		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromEmail, fromName));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					toEmail, toName));
			msg.setSubject(subject);
			msg.setText(msgBody);
			Transport.send(msg);

		} catch (MessagingException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
