import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class Chunker implements Processor {
	private int chunkSize;
	public Chunker() {}
	public Chunker(int chunkSize) {
		this.chunkSize = chunkSize;
	}
	@Override
	public byte[] process(byte[] data,List<String> headers) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			int n = data.length / chunkSize;
			int tail = data.length % chunkSize;
			int offset = 0;
			String head = Integer.toHexString(chunkSize)+"\r\n";
			
			for (int i = 0;i < n;i++) {
				os.write(head.getBytes());
				os.write(data,offset,chunkSize);
				os.write("\r\n".getBytes());
				offset += chunkSize;
			}
			
			if (tail > 0) {
				head = Integer.toHexString(tail)+"\r\n";
				os.write(head.getBytes());
				os.write(data,offset,tail);
				os.write("\r\n".getBytes());
			}
			os.write("0\r\n\r\n".getBytes());
			headers.add("Transfer-Encoding: chunked\r\n");
			return os.toByteArray();
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void enCode(InputStream is,OutputStream os) throws IOException{
		int len;
		byte[] buf = new byte[chunkSize];
		while ((len=is.read(buf))>0) {
			byte[] head = (Integer.toHexString(len)+"\r\n").getBytes();
			os.write(head);
			os.write(buf);
//			System.out.println(new String(buf)+"\r\n");
			os.write("\r\n".getBytes());
			buf = null;
			buf = new byte[chunkSize];
		}
		os.write("0\r\n\r\n".getBytes());
//		byte[] buf = new byte[is.available()];
//		is.read(buf);
////		System.out.println();
//		int n = buf.length / chunkSize;
//		int tail = buf.length % chunkSize;
//		int offset = 0;
//		String head = Integer.toHexString(chunkSize)+"\r\n";
//		
//		for (int i = 0;i < n;++i) {
//			os.write(head.getBytes());
//			os.write(buf,offset,chunkSize);
//			os.write("\r\n".getBytes());
//			offset += offset;
//		}
//		
//		if (tail > 0) {
//			head = Integer.toHexString(tail)+"\r\n";
//			os.write(head.getBytes());
//			os.write(buf,offset,tail);
//			os.write("\r\n".getBytes());
//		}
//		os.write("0\r\n\r\n".getBytes());	
	}
	
	public void deCode(InputStream is,OutputStream os) throws IOException{
		byte[] chunk = new byte[2];
		byte[] rn = new byte[2];	//for read \r\n
		boolean add_r = false;
		is.read(chunk);
		if (chunk[1] == 13) {
			chunkSize = Integer.parseInt(String.valueOf((char)chunk[0]),16);
			rn =new byte[1];
		}
		else {
			chunkSize = Integer.parseInt(String.valueOf((char)chunk[0]) + String.valueOf((char)chunk[1]),16);
		}
		is.read(rn);
		rn = new byte[2];
		byte[] buf = new byte[chunkSize];
		while(is.read(buf) > 0) {
			is.read(rn);
			is.read(chunk);
			if (add_r == true) {
				buf = ("\r"+new String(buf)).getBytes();
				add_r = false;
			}
			if ((chunk[0] == 0 && chunk[1] == 0)) {
				os.write(buf);
				break;
			}
			if (chunk[1] == 13) {
				chunkSize = Integer.parseInt(String.valueOf((char)chunk[0]),16);
				rn = new byte[1];
				add_r = true;
			}
			else {
				chunkSize = Integer.parseInt(String.valueOf((char)chunk[0]) + String.valueOf((char)chunk[1]),16);
			}
			is.read(rn);
			os.write(buf);
			rn = new byte[2];
			buf = null;
			buf = new byte[chunkSize];
		}
	}
}
