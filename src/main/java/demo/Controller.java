/**
 * 
 */
package demo;

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

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * @author SAI GANESH REPAKA
 *
 */

@RestController
public class Controller {

	// USER CREATION
	ArrayList<UserCreation_Bean> al = new ArrayList<UserCreation_Bean>();
	
	
	public DB mongoConnection()
	{
		MongoCredential credential = MongoCredential.createMongoCRCredential("ganesh", "db_assignment2","ganesh".toCharArray());
		   
		try {
			MongoClient mongoClient = new MongoClient(new ServerAddress("ds049160.mongolab.com",49160), Arrays.asList(credential));
			DB db=mongoClient.getDB("db_assignment2");
			
			return db;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	    
		return null;
	}
	
	
	
	
	//User Creation post,get and put
	
	

	@RequestMapping(value = "/users/{user_id}", method = RequestMethod.GET)
	public UserCreation_Bean getUserDetails(@PathVariable String user_id) {
		System.out.println("in get");
		DB db=mongoConnection();
		DBCollection collection_userdetails = db.getCollection("collection_userdetails");
		
		BasicDBObject query = new BasicDBObject("id", user_id);
		DBCursor cursor;

		System.out.println(collection_userdetails.getCount());

		cursor = collection_userdetails.find(query);
		if (cursor.hasNext()) {
			System.out.println("in loop");
			DBObject user = cursor.next();
			System.out.println((String) user.get("id"));
			System.out.println(user.get("email"));
			UserCreation_Bean ucb = new UserCreation_Bean();
			ucb.getUserDetails_get((String) user.get("id"),
					(String) user.get("email"), (String) user.get("password"),
					(String) user.get("name"), (String) user.get("created_at"));
			return ucb;

		}
		return null;
	}
	EtcdResult result;
	@RequestMapping(value="/counter", method=RequestMethod.GET)
	public EtcdResult getCounter() throws EtcdClientException{
		System.out.println("in etcd get");

		EtcdClient client = new EtcdClient(URI.create("http://127.0.0.1:4001/"));

		String key = "/counter";
		int value=0;
		String storedValue=Integer.toString(value);
		
		result = client.set(key, storedValue);
		
		result = client.get(key);
		
		

		return result;
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public UserCreation_Bean createUser(
			@Valid @RequestBody UserCreation_Bean uBean) {
		System.out.println("in user details post");
		UserCreation_Bean ucb = new UserCreation_Bean();
		ucb.setUserDetails(uBean);

		DB db=mongoConnection();
		DBCollection collection_userdetails = db.getCollection("collection_userdetails");

		BasicDBObject bo_userdetails = new BasicDBObject("id", ucb.getUser_id())
				.append("email", ucb.getEmail())
				.append("password", ucb.getPassword())
				.append("created_at", ucb.getCreated_at())
				.append("name", ucb.getName());
		collection_userdetails.insert(bo_userdetails);
		System.out.println(collection_userdetails.getCount());
		return ucb;
	}

	@RequestMapping(value = "/users/{user_id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public UserCreation_Bean updateUser(@PathVariable String user_id,@Valid @RequestBody UserCreation_Bean uBean) {

		System.out.println("in user details put");

		DB db=mongoConnection();
		DBCollection collection_userdetails = db.getCollection("collection_userdetails");
		UserCreation_Bean ucb = new UserCreation_Bean();
		ucb.setUserDetails_update(uBean);



		BasicDBObject update_userdetails = new BasicDBObject();
		update_userdetails.append(
				"$set",
				new BasicDBObject("email", ucb.getEmail())
						.append("password", ucb.getPassword())
						.append("created_at", ucb.getCreated_at())
						.append("name", ucb.getName()));

		BasicDBObject query = new BasicDBObject("id", user_id);
		collection_userdetails.update(query, update_userdetails);
		System.out.println("after new");
		DBCursor cursor;
		cursor = collection_userdetails.find(query);
		if (cursor.hasNext()) {

			DBObject user = cursor.next();

			ucb.getUserDetails_get((String) user.get("id"),
					(String) user.get("email"), (String) user.get("password"),
					(String) user.get("name"), (String) user.get("created_at"));
			return ucb;

		}

		return null;
	}

	// ID CARDS CREATION

	// ArrayList<IdCard_Bean> al_id_details=new ArrayList<IdCard_Bean>();
	@RequestMapping(value = "/users/{user_id}/idcards", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public IdCard_Bean createIdCard(@PathVariable String user_id,
			@Valid @RequestBody IdCard_Bean idcb) {
		System.out.println("in id_details post");
		IdCard_Bean ib = new IdCard_Bean();
		ib.setId_details(idcb);
		DB db=mongoConnection();
		DBCollection collection_userdetails = db.getCollection("collection_userdetails");

	
		BasicDBObject query = new BasicDBObject("id", user_id);
		DBCursor cursor = collection_userdetails.find(query);

		if (cursor.hasNext()) {

			HashMap<String, String> al_id_details = new HashMap<String, String>();

			al_id_details.put("card_id", ib.getCard_id());
			al_id_details.put("card_name", ib.getCard_name());

			al_id_details.put("card_number", ib.getCard_number());
			al_id_details.put("expiration_date", ib.getExpiration_date());

			BasicDBObject update_id_details = new BasicDBObject();
			update_id_details.put("idcards", al_id_details);

			collection_userdetails.update(query, new BasicDBObject("$push",
					update_id_details));

			System.out.println(al_id_details.size());
			return ib;

		}

	

		return null;
	}

	@RequestMapping(value = "/users/{user_id}/idcards", method = RequestMethod.GET)
	public Object listAllIdCards(@PathVariable String user_id) {
		System.out.println("in id_details get");

		DB db=mongoConnection();
		DBCollection collection_userdetails = db.getCollection("collection_userdetails");

		BasicDBObject query = new BasicDBObject("id", user_id);

		DBCursor cursor;

		System.out.println(collection_userdetails.getCount());

		cursor = collection_userdetails.find(query);
		if (cursor.hasNext()) {

			DBObject user = cursor.next();
			System.out.println(user.get("idcards"));
			return user.get("idcards");
		

		}
		return null;

	}

	@RequestMapping(value = "/users/{user_id}/idcards/{card_id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteIdCard(@PathVariable String user_id,
			@PathVariable String card_id) {
		System.out.println("in id_details delete");

		DB db=mongoConnection();
		DBCollection collection_userdetails = db.getCollection("collection_userdetails");

		BasicDBObject query = new BasicDBObject("id", user_id);
		BasicDBObject card = new BasicDBObject("card_id", card_id);
		BasicDBObject delete_card = new BasicDBObject("idcards", card);
		// DBCursor cursor;

		System.out.println(collection_userdetails.getCount());
		// db.mytests.update({},{$pull:{'mylist':{'foo1':'bar1'}}})
		collection_userdetails.update(query, new BasicDBObject("$pull",
				delete_card));

	}

	// WEB LOGIN CREATION

	@RequestMapping(value = "/users/{user_id}/weblogins", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public WebLogin createWebLogin(@PathVariable String user_id,
			@Valid @RequestBody WebLogin wlogin) {
		System.out.println("in web details post");
		WebLogin wl = new WebLogin();
		wl.setWebDetails(wlogin);
		DB db=mongoConnection();
		DBCollection collection_userdetails = db.getCollection("collection_userdetails");

		BasicDBObject query = new BasicDBObject("id", user_id);
		DBCursor cursor = collection_userdetails.find(query);

		if (cursor.hasNext()) {

			HashMap<String, String> al_wl_details = new HashMap<String, String>();

			al_wl_details.put("login_id", wl.getLogin_id());
			al_wl_details.put("url", wl.getUrl());

			al_wl_details.put("login", wl.getLogin());
			al_wl_details.put("password", wl.getPassword());

			BasicDBObject update_wl_details = new BasicDBObject();
			update_wl_details.put("weblogins", al_wl_details);

			collection_userdetails.update(query, new BasicDBObject("$push",
					update_wl_details));

			return wl;

		}

		return null;
	}

	@RequestMapping(value = "/users/{user_id}/weblogins", method = RequestMethod.GET)
	public Object listAllIdWebLogins(@PathVariable String user_id) {
		System.out.println("in web_details get");
		DB db=mongoConnection();
		DBCollection collection_userdetails = db.getCollection("collection_userdetails");
		BasicDBObject query = new BasicDBObject("id", user_id);

		DBCursor cursor;

		System.out.println(collection_userdetails.getCount());

		cursor = collection_userdetails.find(query);
		if (cursor.hasNext()) {

			DBObject user = cursor.next();
			System.out.println(user.get("weblogins"));
			return user.get("weblogins");

		}
		return null;
	}

	@RequestMapping(value = "/users/{user_id}/weblogins/{login_id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteWebLogin(@PathVariable String user_id,
			@PathVariable String login_id) {
		System.out.println("in web_details delete");

		DB db=mongoConnection();
		DBCollection collection_userdetails = db.getCollection("collection_userdetails");

		BasicDBObject query = new BasicDBObject("id", user_id);
		BasicDBObject webLogin = new BasicDBObject("login_id", login_id);
		BasicDBObject delete_webLogin = new BasicDBObject("weblogins", webLogin);

		System.out.println(collection_userdetails.getCount());
		collection_userdetails.update(query, new BasicDBObject("$pull",
				delete_webLogin));

	}

	// BANK ACCOUNT CREATION

	@RequestMapping(value = "/users/{user_id}/bankaccounts", method = RequestMethod.POST)
	public ResponseEntity<BankDetails_Bean> bankAccount(@PathVariable String user_id,@Valid @RequestBody BankDetails_Bean bbean) {
		System.out.println("in bank_details post");
		
		
		
		RestTemplate restTemplate = new RestTemplate();
		

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		List<MediaType> supportedMediaTypes = new LinkedList<MediaType>(
				converter.getSupportedMediaTypes());
		MediaType textJavascriptMediaType = new MediaType("text", "plain",
				MappingJackson2HttpMessageConverter.DEFAULT_CHARSET);

		supportedMediaTypes.add(textJavascriptMediaType);
		converter.setSupportedMediaTypes(supportedMediaTypes);
		List<HttpMessageConverter<?>> httpMessageConvertorsList = restTemplate.getMessageConverters();
		httpMessageConvertorsList.add(converter);
		
		
		Page page=restTemplate.getForObject("http://www.routingnumbers.info/api/data.json?rn=".concat(bbean.getRouting_number()),Page.class);

		System.out.println(page.getCustomer_name());
		
		if(page.getCode().equals("200"))
		{
			BankDetails_Bean bd_return =creatingBank(bbean,page,user_id);
		return new ResponseEntity<BankDetails_Bean>(bd_return,HttpStatus.CREATED);

		}
		else
		{
		 return new ResponseEntity<BankDetails_Bean>(HttpStatus.NOT_FOUND);
		}

	}

	
	
	public BankDetails_Bean creatingBank(BankDetails_Bean bbean, Page page, String user_id)
	{
		
		BankDetails_Bean bd = new BankDetails_Bean();

		 bd.setBankDetails(bbean,page.getCustomer_name());
		 DB db=mongoConnection();
		DBCollection collection_userdetails = db.getCollection("collection_userdetails");
		
		 BasicDBObject query = new BasicDBObject("id", user_id);
		 DBCursor cursor = collection_userdetails.find(query);
		
		
		 if(cursor.hasNext())
		 {
			
		
		 HashMap<String,String> al_bd_details=new HashMap<String,String>();
		
		 al_bd_details.put("ba_id", bd.getBa_id());
		 al_bd_details.put("account_name", bd.getAccount_name());
		 al_bd_details.put("routing_number", bd.getRouting_number());
		 al_bd_details.put("account_number", bd.getAccount_number());
		
		 BasicDBObject update_bd_details=new BasicDBObject();
		 update_bd_details.put("bankaccounts", al_bd_details);
		
		 collection_userdetails.update(query,new
		 BasicDBObject("$push",update_bd_details));
		
		 return bd;
		 }
		
		
		
		
		
		return null;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/users/{user_id}/bankaccounts", method = RequestMethod.GET)
	public Object listAllBankAccounts(@PathVariable String user_id) {
		System.out.println("in bank_details get");
		DB db=mongoConnection();
		DBCollection collection_userdetails = db.getCollection("collection_userdetails");

		BasicDBObject query = new BasicDBObject("id", user_id);

		DBCursor cursor;

		System.out.println(collection_userdetails.getCount());

		cursor = collection_userdetails.find(query);
		if (cursor.hasNext()) {

			DBObject user = cursor.next();
			System.out.println(user.get("bankaccounts"));
			return user.get("bankaccounts");

		}
		return null;
	}

	@RequestMapping(value = "/users/{user_id}/bankaccounts/{ba_id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBankAccount(@PathVariable String user_id,
			@PathVariable String ba_id) {
		System.out.println("in bank_details delete");

		DB db=mongoConnection();
		DBCollection collection_userdetails = db.getCollection("collection_userdetails");

		BasicDBObject query = new BasicDBObject("id", user_id);
		BasicDBObject bankAccount = new BasicDBObject("ba_id", ba_id);
		BasicDBObject delete_bankAccount = new BasicDBObject("bankaccounts",
				bankAccount);

		System.out.println(collection_userdetails.getCount());
		collection_userdetails.update(query, new BasicDBObject("$pull",
				delete_bankAccount));

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelMap handleException(MethodArgumentNotValidException ex) {
		List<FieldError> errors = ex.getBindingResult().getFieldErrors();
		ModelMap mm = new ModelMap();
		ModelMap em = new ModelMap();
		for (FieldError fieldError : errors) {
			em.addAttribute(fieldError.getField(),
					fieldError.getDefaultMessage());

		}
		mm.addAttribute("", em);
		return mm;
	}

}