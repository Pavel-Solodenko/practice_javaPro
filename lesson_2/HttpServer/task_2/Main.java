import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {
	public static void main(String[] args) {
		HTTPServer server = new HTTPServer(8080,"C:\\temp");
		server.start();
		System.out.println("Server started");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				server.stop();
				System.out.println("Server stopped`");
			}
		});

//		try {
//			testChunker();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		server.stop();
	}	
	public static void testChunker() throws FileNotFoundException, IOException{
		InputStream is = new FileInputStream("C:\\temp\\index.html");
		OutputStream os = new FileOutputStream("C:\\eclipse_present_projects\\pro-lesson_2-HttpServer-Cache_live\\out.txt");
		new Chunker(30).enCode(is, os);
		os.flush();os.close();is.close();
		is = new FileInputStream("C:\\eclipse_present_projects\\pro-lesson_2-HttpServer-Cache_live\\out.txt");
		os = new FileOutputStream("C:\\eclipse_present_projects\\pro-lesson_2-HttpServer-Cache_live\\out2.txt");
		new Chunker().deCode(is, os);
		os.flush();is.close();os.close();
	}
}
