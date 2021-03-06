import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

public class Compressor implements Processor{
	public static final int ALG_DEFLATE = 0;
	public static final int ALG_GZIP = 1;
	
	private int compLevel;
	private int compAlg = ALG_GZIP;
	
	public Compressor(int compLevel) {
		this.compLevel = compLevel;
	}
	@Override
	public byte[] process(byte[] data,List<String> headers) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			if (compAlg == ALG_DEFLATE) {
				DeflaterOutputStream ds = new DeflaterOutputStream(os,new Deflater(compLevel));
				ds.write(data);
				ds.finish();
				headers.add("Content-Encoding: deflated\r\n");
			}
			else {
				if (compAlg == ALG_GZIP) {
					GZIPOutputStream ds = new GZIPOutputStream(os);
					ds.write(data);
					ds.finish();
					headers.add("Content-Encoding: gzip\r\n");
				}
			}
			return os.toByteArray();
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
