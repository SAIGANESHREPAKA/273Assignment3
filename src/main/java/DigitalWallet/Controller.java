/**
 * 
 */
package DigitalWallet;

import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdClientException;
import com.justinsb.etcd.EtcdResult;



/**
 * @author SAI GANESH REPAKA
 *
 */

@RestController
public class Controller {

	// USER CREATION
	
	

	
	
	
	
	//User Creation post,get and put
	
	@RequestMapping(value="/counter", method=RequestMethod.GET)
	public EtcdResult getCounter() throws EtcdClientException{
		System.out.println("in etcd get");
		EtcdResult result;
		EtcdClient client = new EtcdClient(URI.create("http://127.0.0.1:4001/"));

		String key = "/counter";
		int value=0;
		String storedValue=Integer.toString(value);
		
		result = client.set(key, storedValue);
		
		result = client.get(key);
		
		

		return result;
	}
	
	
}
	