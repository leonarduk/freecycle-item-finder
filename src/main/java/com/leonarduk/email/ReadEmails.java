package com.leonarduk.email;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.ReadOnlyFolderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.mail.internet.InternetAddress;

import com.sun.mail.imap.IMAPBodyPart;

public class ReadEmails {

	enum ServerType {
		imap, pop3;
	}

	// Constructor Call
	public ReadEmails() {
	}

	// Responsible for printing Data to Console
	private void printData(String data) {
		System.out.println(data);
	}

	public void processMail(String server, String userName, String password,
			ServerType serverType) {
		Session session = null;
		Store store = null;
		Folder folder = null;
		Message message = null;
		Message[] messages = null;
		Object messagecontentObject = null;
		String sender = null;
		String subject = null;
		Multipart multipart = null;
		Part part = null;
		String contentType = null;

		try {
			printData("--------------processing mails started-----------------");
			session = Session.getDefaultInstance(System.getProperties(), null);

			printData("getting the session for accessing email.");
			store = session.getStore(serverType.name());

			store.connect(server, userName, password);
			printData("Connection established with IMAP server.");

			// Get a handle on the default folder
			folder = store.getDefaultFolder();

			printData("Getting the Inbox folder.");

			// Retrieve the "Inbox"
			folder = folder.getFolder("inbox");

			// Reading the Email Index in Read / Write Mode
			folder.open(Folder.READ_WRITE);

			// Retrieve the messages
			messages = folder.getMessages();

			// Loop over all of the messages
			for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {
				// Retrieve the next message to be read
				message = messages[messageNumber];

				// Retrieve the message content
				messagecontentObject = message.getContent();

				// Determine email type
				if (messagecontentObject instanceof Multipart) {
					printData("Found Email with Attachment");
					sender = ((InternetAddress) message.getFrom()[0])
							.getPersonal();

					// If the "personal" information has no entry, check the
					// address for the sender information
					printData("If the personal information has no entry, check the address for the sender information.");

					if (sender == null) {
						sender = ((InternetAddress) message.getFrom()[0])
								.getAddress();
						printData("sender in NULL. Printing Address:" + sender);
					}
					printData("Sender -." + sender);

					// Get the subject information
					subject = message.getSubject();

					printData("subject=" + subject);

					// Retrieve the Multipart object from the message
					multipart = (Multipart) message.getContent();

					printData("Retrieve the Multipart object from the message");

					// Loop over the parts of the email
					for (int i = 0; i < multipart.getCount(); i++) {
						// Retrieve the next part
						part = multipart.getBodyPart(i);

						// Get the content type
						contentType = part.getContentType();

						// Display the content type
						printData("Content: " + contentType);

						if (contentType.startsWith("text/plain")) {
							printData("---------reading content type text/plain  mail -------------");

							System.out.println(part.getContent());
						} else {
							// Retrieve the file name
							String fileName = part.getFileName();
							printData("retrive the fileName=" + fileName);
						}
					}
				} else {
					printData("Found Mail Without Attachment");
					sender = ((InternetAddress) message.getFrom()[0])
							.getPersonal();

					// If the "personal" information has no entry, check the
					// address for the sender information
					printData("If the personal information has no entry, check the address for the sender information.");

					if (sender == null) {
						sender = ((InternetAddress) message.getFrom()[0])
								.getAddress();
						printData("sender in NULL. Printing Address:" + sender);
					}

					// Get the subject information
					subject = message.getSubject();
					printData("subject=" + subject);

					System.out.println(messagecontentObject.toString());
				}
			}

			// Close the folder
			folder.close(true);

			// Close the message store
			store.close();
		} catch (AuthenticationFailedException e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		} catch (FolderClosedException e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		} catch (FolderNotFoundException e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		} catch (ReadOnlyFolderException e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		} catch (StoreClosedException e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		} catch (Exception e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		}
	}

	// Main Function for The readEmail Class
	public static void main(String args[]) {
		// Creating new readEmail Object
		ReadEmails readMail = new ReadEmails();

		// Calling processMail Function to read from IMAP Account
//		readMail.processMail("leonarduk.com", "leonard", "SW179TNKT26LJ",
//				ServerType.imap);
		

		// Calling processMail Function to read from IMAP Account
		readMail.processMail("localhost", "steve", "",
				ServerType.pop3);
	}

}