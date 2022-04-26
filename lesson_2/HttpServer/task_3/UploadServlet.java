import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.zip.*;
import java.util.Collection;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<Part> parts = request.getParts();
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition","attachment;filename=output.zip");
        OutputStream os = response.getOutputStream();
        ZipOutputStream zout = new ZipOutputStream(os);
        for (Part part : parts) {
            String filename = part.getSubmittedFileName();
            InputStream is = part.getInputStream();
            byte[] buf = new byte[is.available()];
            is.read(buf);
            WriteZip(zout,buf,filename);
            is.close();
        }
        zout.close();
        os.flush();
        os.close();
    }

    private void WriteZip(ZipOutputStream zout,byte[] buf,String filename) throws IOException{
        ZipEntry entry = new ZipEntry(filename);
        zout.putNextEntry(entry);
        zout.write(buf);
        zout.closeEntry();
    }
}
