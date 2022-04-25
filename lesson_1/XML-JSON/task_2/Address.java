
public class Address {
	private String country;
	private String city;
	private String street;
	
	public Address(String country,String city,String street) {
		this.country = country;
		this.city = city;
		this.street = street;
	}
	
	@Override
	public String toString() {
		return "Country: "+country+"\tCity: "+city+"\tStreet: "+street;
	}
}
