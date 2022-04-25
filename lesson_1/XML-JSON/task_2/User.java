import java.util.Arrays;

public class User {
	private String name;
	private String surname;
	private String[] phones;
	private String[] sites;
	private Address address;
	
	public User(String name,String surname,String[] number,String[] site,Address address) {
		this.name = name;
		this.surname = surname;
		this.phones = number;
		this.sites = site;
		this.address = address;
	}
	public User(String name,String surname,String[] phones,String[] sites) {
		this.name = name;
		this.surname = surname;
		this.phones = phones;
		this.sites = sites;
	}
	
	@Override
	public String toString() {
		return "User info:\r\n"+"Name: "+name+"\r\nSurname: "+surname+"\r\nNumbers: "+Arrays.toString(phones)+"\r\nSites: "+Arrays.toString(sites)+"\r\nAdress: "+address.toString()+"\r\n";
	}
}
