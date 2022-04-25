import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class JSONWorker implements JsonDeserializer<User>{
	
	@Override
	public User deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		JsonObject jsonObject = arg0.getAsJsonObject();
		String name = jsonObject.get("name").getAsString();
		String surname = jsonObject.get("surname").getAsString();
		JsonArray phones = jsonObject.get("phones").getAsJsonArray();
		JsonArray sites = jsonObject.get("sites").getAsJsonArray();
		JsonObject address = jsonObject.get("address").getAsJsonObject();
		return new User(name,surname,jsonArrayToStringArray(phones),jsonArrayToStringArray(sites),jsonObjectToAddress(address));
	}
	
	public String[] jsonArrayToStringArray(JsonArray jsonArray) {
		ArrayList<String> string_list = new ArrayList<String>();
		for (JsonElement a : jsonArray) {
			string_list.add(a.getAsString());
		}
		return string_list.toArray(new String[0]);
	}
	
	public Address jsonObjectToAddress(JsonObject address) {
		String country = address.get("country").getAsString();
		String city = address.get("city").getAsString();
		String street = address.get("street").getAsString();
		return new Address(country,city,street);
	}
//	public static User loadFromJson(File file) {
//		Gson gson = new Gson();
//		User user = null;
//		address address = null;
//		try {
//			FileReader fr = new FileReader(file);
//			user = gson.fromJson(fr, User.class);
//			address = gson.fromJson(fr, address.class);
////			System.out.println(user.getAdress());
//			System.out.println(address);
//		}
//		catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
//			e.printStackTrace();
//		}
////		user.setAdress(adress);
//		return  user;
//	}
}
