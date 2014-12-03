/**
 * 
 */
package DigitalWallet;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

public class WebLogin {

	private String login_id;
	
	@NotNull(message="url cannot be empty")
	@URL
	private String url;
	
	@NotNull(message="Login cannot be empty")
	private String login;
	
	@NotNull(message="password cannot be empty")
	private String password;
	
	
	
	public String getLogin_id() {
		return login_id;
	}
	@SuppressWarnings("deprecation")
	public String setLogin_id() {
		Date date=new Date();
		return "w"+date.toGMTString();
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public void setWebDetails(WebLogin wl)
	{
		
		this.login_id=this.setLogin_id();
		this.url=wl.url;
		this.login=wl.login;
		this.password=wl.password;
	}
	
	
}
