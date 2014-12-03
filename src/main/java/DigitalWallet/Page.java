package DigitalWallet;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {

    private String customer_name;
    private String code;
    



	public String getCustomer_name() {
		return customer_name;
	}

	public String getCode(){
		return code;
	}
	
}