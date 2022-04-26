import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


@WebServlet("/ZipServlet")
public class ZipServlet extends HttpServlet {
    private final String path_form = "C:\\temp\\index.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        InputStream is = new FileInputStream(path_form);
        byte[] buf = new byte[is.available()];
        is.read(buf);
        is.close();
        OutputStream os = response.getOutputStream();
        os.write(buf);
        os.flush();
        os.close();
    }
}
