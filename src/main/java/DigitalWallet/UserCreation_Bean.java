/**
 * 
 */
package DigitalWallet;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class UserCreation_Bean {
	 
	private String user_id;
	
	@Email(message="not valid email")
	@NotEmpty(message="email cannot be empty")
	String email;
	
	@NotNull(message="password cannot be empty")
	private String password;
	private String name;
	
	private String created_at;
	@JsonIgnore
	private String updated_at;

	Date date=new Date();
	ArrayList<IdCard_Bean> al_id=new ArrayList<IdCard_Bean>();
	ArrayList<WebLogin> al_wl=new ArrayList<WebLogin>();  
	ArrayList<BankDetails_Bean> al_bd=new ArrayList<BankDetails_Bean>();
	
//	public ArrayList<IdCard_Bean> getAl_id() {
//		return al_id;
//	}


//	public ArrayList<WebLogin> getAl_wl() {
//		return al_wl;
//	}


	///public ArrayList<BankDetails_Bean> getAl_bd() {
		//return al_bd;
	//}

	

	public void setAl_bd(BankDetails_Bean bd) {
		this.al_bd.add(bd);
	}


	public void setAl_wl(WebLogin wl) {
		this.al_wl.add(wl);
	}


	public void setAl_id(IdCard_Bean ib) {
		this.al_id.add(ib);
	}


//	public UserCreation_Bean() {
//	
//	}
	
	
//	public UserCreation_Bean(String user_id, String email, String password,
//			String name) {
//		super();
//		this.user_id = user_id;
//		this.email = email;
//		this.password = password;
//		this.name = name;
//		this.created_at = created_at;
//		this.updated_at = updated_at;
//	}
	
	
	
	public String getCreated_at()
	{
		return created_at;
	}


	@SuppressWarnings("deprecation")
	public String setCreated_at() {
		return date.toLocaleString();
	}


	@SuppressWarnings("deprecation")
	public String date1()
	{
		return date.toGMTString();
	}


	@SuppressWarnings("deprecation")
	public String setUpdated_at() {
		return date.toLocaleString();
	}


	public String getUser_id() {
		return user_id;
	}
	
	@SuppressWarnings("deprecation")
	
	
	public String setUser_id() {
		return "u-"+date.toGMTString();
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUserDetails(UserCreation_Bean ucb)
	{
		this.user_id = this.setUser_id();
		this.email = ucb.email;
		this.password = ucb.password;
		this.name = ucb.name;
		this.created_at=this.setCreated_at();
	}
	
	public void setUserDetails_update(UserCreation_Bean ucb)
	{
		this.user_id = ucb.getUser_id();
		this.email = ucb.email;
		this.password = ucb.password;
		this.name = ucb.name;
		this.created_at=this.setCreated_at();
	}
	
	public void getUserDetails_get(String id, String email, String password, String name, String created_at)
	{
		this.user_id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.created_at=created_at;
	}

}
