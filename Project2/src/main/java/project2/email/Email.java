package project2.email;

import java.time.Instant;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class Email {
	
	final static Logger logem = Logger.getLogger(Email.class);
	
	public static String mail1 = "Hello,\r\n"
			+ "\r\n"
			+ "There was a request to reset password.\r\n"
			+ "\r\n"
			+ "If you did not make this request, then ignore this email.\r\n"
			+ "\r\n"
			+ "Otherwise, please use this password to login.\r\n"
			+ "\r\n";
			
	public static String mail2 =	"\r\n"
			+ "Thank you,\r\n"
			+ "Revashare Team\r\n";
		
			
	
	public static void sendMail (String msg , String password) {
		Properties prop = new Properties();
		prop.setProperty("mail.debug", "true");
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.starttls.enable", "true");
		prop.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		prop.setProperty("mail.smtp.host", "chi106.greengeeks.net");
		prop.setProperty("mail.smtp.port", "465");
		prop.setProperty("mail.smtp.ssl.enable", "true");
		prop.setProperty("mail.smtp.ssl.trust", "chi106.greengeeks.net");
		Session session = Session.getInstance(prop, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		    	return new PasswordAuthentication("alchemy@kmason.net", "revaturealchemy");
		    }
		    
		});
		//session.setDebug(true);
		
		try  {
			String email = mail1 + password + mail2;
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("alchemy@kmason.net"));
			message.setRecipients(
			  Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(Instant.now().toString());
			logem.warn("SENDING TO DUMMY EMAIL WITH DUMMY MESSAGE");
			

			

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);

			Transport.send(message);
		} catch (SendFailedException err) {
			err.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} 
	}
	
	public String buildMail (String...email) {
		//TODO
		String newLine = System.getProperty("line.separator");
		return newLine;
	}
//	public static void main(String[] args) {
//		logem.setLevel(Level.ALL);
//		logem.trace("Email main");
//		String msg = "This is my first email using JavaMailer";
//		//sendMail(msg);
//		logem.trace("end");
//	}
}
