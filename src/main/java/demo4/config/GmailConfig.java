package demo4.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:configservice.properties")
public class GmailConfig {
	
	@Value("${config.username}")
	private String username;
	
	@Value("${config.pass}")
	private String pass;

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setDefaultEncoding("utf-8");
		sender.setHost("smtp.gmail.com");
		sender.setPort(587);
		sender.setUsername(username);
		sender.setPassword(pass);
		Properties pros = sender.getJavaMailProperties();
		pros.setProperty("mail.transport.protocol", "smtp");
		pros.setProperty("mail.smtp.auth", "true");
		pros.setProperty("mail.smtp.starttls.enable", "true");
		pros.setProperty("mail.debug","true");
		
		return sender;
	}
	
}
