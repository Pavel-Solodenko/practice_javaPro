import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ConcurrentHashMap;

public class FileManager {
	private String path;
	private static ConcurrentHashMap<String,CacheRecorder> map = new ConcurrentHashMap<String,CacheRecorder>();
	private static long beginLifeTime = 0;
	private static long lifeTime = 0;
//	private static int a = 0;
	
	public FileManager(String path) {
		if (path.endsWith("/") || path.endsWith("\\")) {
			path = path.substring(0,path.length() - 1);
		}
		this.path = path;
	}
	
	public byte[] get(String url) {
		cacheLive();
		try {
			byte [] buf = null;
			if (map.get(url)!= null) {
				buf = map.get(url).data;
//				a++;
//				System.out.println(a);
//				if (a == 10) {map.clear();}
				System.out.println("From cache");
				/*lifeTime = (System.currentTimeMillis() - beginLifeTime);
				if (lifeTime >= 1000) {
					lifeTime = lifeTime / 1000;
					System.out.println("Cache live time: "+lifeTime+" seconds");
				}
				else {
					System.out.println("Cache live time: "+ lifeTime+" millis");
				}*/
				return buf;
			}
			System.out.println("Not from cache");
			
			String fullPath = path.replace('\\','/')+url;
			RandomAccessFile f = new RandomAccessFile(fullPath, "r");
			try {
				buf = new byte[(int)f.length()];
				f.read(buf,0,buf.length);
			}
			finally {
				f.close();
			}
			
			map.put(url, new CacheRecorder(buf));	//put to cache
//			beginLifeTime = System.currentTimeMillis();
			return buf;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void cacheLive() {
		int i = 1;
		if (map.isEmpty() == false) {
			map.forEach((k, v) -> System.out.println(map.get(k).toString()));
		}
		else {
			System.out.println("No cache");
		}
	}
}
