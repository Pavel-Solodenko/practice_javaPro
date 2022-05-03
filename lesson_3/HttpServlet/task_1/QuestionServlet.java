import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;

@WebServlet("/Question")
public class QuestionServlet extends HttpServlet {
    private final String path_form = "C:\\Users\\User\\IdeaProjects\\pro-lesson_3-question-statistics-form\\form.html";
    private final String form = getForm(path_form);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OutputStream os = response.getOutputStream();
        if (!form.isEmpty()) {
            os.write(form.getBytes(StandardCharsets.UTF_8));
            response.setStatus(200);
            HttpSession session = request.getSession(true);
//            System.out.println(session.isNew());
          if (session.isNew()) {
              response.sendRedirect("http://127.0.0.1:8080/Question");
              session.setAttribute("Answers",getListAnswers(form));
          }
        }
        else {
            response.setStatus(500);
            os.write("Something wrong with form :(".getBytes(StandardCharsets.UTF_8));
        }
    }
    
    private String getForm(String path) {
        String form= "";
        try {
            InputStream is = new FileInputStream(path);
            byte[] buf = new byte[is.available()];
            is.read(buf);
            form = new String(buf);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return form;
    }

    private HashSet<Answer> getListAnswers(String form) {
        HashSet<Answer> values = new HashSet<>();
        while (form.indexOf("value") != -1) {
            form = form.substring(form.indexOf("name="));
            form = form.substring(form.indexOf("?")+1);
            int q_id = Integer.parseInt(form.substring(0,form.indexOf("\"")));
            form = form.substring(form.indexOf("value=\"")).replaceFirst("value=\"","");
            values.add(new Answer(q_id,form.substring(0,form.indexOf("\""))));
        }
        return values;
    }
}
