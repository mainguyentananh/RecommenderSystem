package demo4.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class mailService {


	@Autowired
	private JavaMailSender mailsender;
	
	public void sendEmail(String from , String to, String subject, String content) throws MessagingException {
		 MimeMessage message = mailsender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(message,"utf-8");
		 helper.setFrom(from);
		 helper.setTo(to);
		 helper.setSubject(subject);
		 helper.setText(content,true);
		 mailsender.send(message);
}

}
