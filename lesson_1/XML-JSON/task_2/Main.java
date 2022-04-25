import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Main {
	public static void main(String args[]) {
		String path = "C:\\eclipse_present_projects\\pro-lesson_1-JSON\\users.txt";
//		User user1 = JSONWorker.loadFromJson(new File(path));
		GsonBuilder gsBuilder = new GsonBuilder();
		gsBuilder.registerTypeAdapter(User.class, new JSONWorker());
		gsBuilder.setPrettyPrinting();
		Gson gson = gsBuilder.create();
		User user = null;
		try {
			user = gson.fromJson(new FileReader(path), User.class);
			
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}
}
