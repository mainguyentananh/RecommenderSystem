package demo4.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class GmailConfig {
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setDefaultEncoding("utf-8");
		sender.setHost("smtp.gmail.com");
		sender.setPort(587);
		
		
		sender.setUsername("");
		sender.setPassword("");
		
		Properties pros = sender.getJavaMailProperties();
		pros.setProperty("mail.transport.protocol", "smtp");
		pros.setProperty("mail.smtp.auth", "true");
		pros.setProperty("mail.smtp.starttls.enable", "true");
		pros.setProperty("mail.debug","true");
		
		return sender;
	}
	
}
