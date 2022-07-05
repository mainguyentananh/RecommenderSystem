package demo4.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import demo4.service.userDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class webSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private userDetailService us;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(us);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider()).userDetailsService(us);
	}
	
	@Bean
	public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
	    return new MySimpleUrlAuthenticationSuccessHandler();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();
		http.authorizeRequests().antMatchers("/search/document/download/source/**").hasAnyAuthority("ROLE_ADMIN","ROLE_TEACHER");
		http.authorizeRequests().antMatchers("/search/**").authenticated();
		
		http.authorizeRequests().antMatchers("/student/**").hasAuthority("ROLE_STUDENT");
		http.authorizeRequests().antMatchers("/teacher/**").hasAnyAuthority("ROLE_ADMIN","ROLE_TEACHER");
		http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ROLE_ADMIN");
		
		http.authorizeRequests().antMatchers("/static/upload/excel/**","/static/upload/source/**","/document/download/source/**").hasAnyAuthority("ROLE_ADMIN","ROLE_TEACHER");
		
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		http.authorizeRequests()
		.and().formLogin().loginPage("/login")
			.loginProcessingUrl("/j_spring_security_check")
				.usernameParameter("username").passwordParameter("password")
				.successHandler(myAuthenticationSuccessHandler())
				.failureUrl("/login?error=true")
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").deleteCookies("remember-me");
				
		//Config remember-me		
		http.rememberMe().key("uniqueAndSecret").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(86400);	
	}

	//Lưu tạm cookies trên ram
	 @Bean
	 public PersistentTokenRepository persistentTokenRepository() {
		 InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl(); 
	     return memory;
	 }
	
}
