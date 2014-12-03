package DigitalWallet;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class IdCard_Bean {
	private String card_id;
	
	@NotNull(message="Card name cannot be empty")
	private String card_name;
	
	@NotNull(message="Card number cannot be empty")
	private String card_number;
	private String expiration_date;
	
	
	
	public String getCard_id() {
		return card_id;
	}
	@SuppressWarnings("deprecation")
	public String setCard_id() {
		Date date=new Date();
		return "c"+date.toGMTString();
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getExpiration_date() {
		return expiration_date;
	}
	public void setExpiration_Date(String expiration_date) {
		this.expiration_date = expiration_date;
	}
	
	public void setId_details(IdCard_Bean idcb)
	{
		this.card_id=this.setCard_id();
		this.card_name=idcb.card_name;
		this.card_number=idcb.card_number;
		this.expiration_date=idcb.expiration_date;	
	
	}
	
	
	
	

}
