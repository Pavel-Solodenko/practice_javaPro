package academy.prog;

import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.regex.Pattern;

public class LoginServlet extends HttpServlet {
    static final String LOGIN = "admin";
    static final String PASS = "Admin1@999";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String ageString = request.getParameter("age");
        final Pattern passPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{10,}$");
        int age = 0;
        
        if (!checkEmptyFields(login,password,ageString,response)) return;

        if (!checkGoodPassword(passPattern,password,response)) return;

        age = checkAgeValidation(age,ageString,response);
        if (age == -100) return;

        authorization(login,password,age,response,request);

    }

    private void authorization(String login,String password,int age,
                               HttpServletResponse response,
                               HttpServletRequest request) throws IOException{
        if (LOGIN.equals(login) && PASS.equals(password)) {
            if (age < 18) {
                response.sendRedirect("index.jsp?age=notEnough");
            }
            else {
                HttpSession session = request.getSession(true);
                session.setAttribute("user_login", login);
                response.sendRedirect("index.jsp");
            }
        }
        else {
            response.sendRedirect("index.jsp?loginOkOrNo="+LOGIN.equals(login)+
                    "&passwordOkOrNo="+PASS.equals(password));
        }
    }

    private int checkAgeValidation(int age,String ageString,
                                   HttpServletResponse response) throws IOException {
        try {
            age = Integer.parseInt(ageString);
        }
        catch (NumberFormatException e) {
            response.sendRedirect("index.jsp?age=notValid");
            return -100;
        }
        return age;
    }

    private boolean checkGoodPassword(Pattern passPattern,
                                   String password,HttpServletResponse response) throws IOException {
        if (!passPattern.matcher(password).matches()) {
            response.sendRedirect("index.jsp?passwordGood=false");
            return false;
        }
        return true;
    }

    private boolean checkEmptyFields(String login,String password,
                                  String ageString,HttpServletResponse response) throws IOException {
        if (login.isEmpty() || password.isEmpty() || ageString.isEmpty()) {
            response.sendRedirect("index.jsp?data=empty");
            return false;
        }
        return true;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String a = request.getParameter("a");
        HttpSession session = request.getSession(false);

        if ("exit".equals(a) && (session != null))
            session.removeAttribute("user_login");

        response.sendRedirect("index.jsp");
    }
}
