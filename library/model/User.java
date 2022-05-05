package library.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
	private String name, id, password, mail;
	
	public String getId() {
		return id;
	}
	
	protected void setId(String id) {
		this.id=id;
	}
	
	public String getName() {
		return name;
	}
	
	protected void setName(String name) {
		this.name=name;
	}
	
	public String getPassword() {
		return password;
	}
	
	protected void setPassword(String password) {
		this.password=password;
	}

	protected void setMail(String mail) {
		this.mail=mail;
	}
	
	public String getMail() {
		return mail;
	}
	
	protected boolean validateMail(String mail) {
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(mail);
		return matcher.matches();
	}
	
	protected boolean validatePassword(String password) {
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
	
}