package bean;

public class Correo {

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getStarttls() {
		return starttls;
	}
	public void setStarttls(String starttls) {
		this.starttls = starttls;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	/******************************************/
	/***********Variables de clase*************/
	/******************************************/
	private  String host;
	private  String port; 
	private  String starttls; 
	private  String sender; 
	private  String user; 
	private  String pass; 
	private  String auth; 
	/*
	private final String host ="mail.intra.bcv";
	private final String port ="25"; 
	private final String starttls ="true"; 
	private final String sender ="admsspv@bcv.org.ve"; 
	private final String user ="admsspv"; 
	private final String auth ="true"; 
	*/

}
