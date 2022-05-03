import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/Statistic")
public class StatisticServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashSet<Answer> answers = new HashSet<>();
        HashSet<Question> questions = new HashSet<>();
        HashMap<Answer,Integer> values = new HashMap<>();
        Map<String,String[]> params = request.getParameterMap();
        params.forEach((k,v)-> {
            int q_id = Integer.parseInt(k.substring(k.length()-1));
            Question question = new Question(q_id,k.substring(0,k.length()-1));
            if (!questions.contains(question)) {
                questions.add(question);
            }
            for (String n : v) {
                if (v.length == 1) {
                    answers.add(new Answer(q_id,n));
                }
                else {
                    System.out.println("Something with parameter valueS!!");
                }
            }
        });
        HttpSession session = request.getSession();
        HashSet<Answer> ansv_list = (HashSet<Answer>) session.getAttribute("Answers");
        if (ansv_list != null) {
            initValuesMap(ansv_list,values);
            session.removeAttribute("Answers");
            updateAnswers(values,answers);
            session.setAttribute("Values",values);
            session.setAttribute("Questions",questions);
            OutputStream os = response.getOutputStream();
            os.write(createStatisticForm(values,questions).getBytes(StandardCharsets.UTF_8));
            os.close();
        }
        else {
            values = (HashMap<Answer, Integer>) session.getAttribute("Values");
            session.removeAttribute("Values");
            updateAnswers(values,answers);
            /*method build response form*/
            OutputStream os = response.getOutputStream();
            os.write(createStatisticForm(values, (HashSet<Question>) session.getAttribute("Questions"))
                    .getBytes(StandardCharsets.UTF_8));
            os.close();
            session.setAttribute("Values",values);
        }

/*        if (session.isNew()) {
            parameters.forEach((k,v)->session.setAttribute(k,v));
            Collection<String> val = parameters.values();
            HashMap<String,Integer> values = new HashMap<>();
            val.forEach(k-> values.put(k,1));
            session.setAttribute("Values",values);
            session.setAttribute("Questions",parameters.keySet());
        }
        else {
           Enumeration<String> atr_names = session.getAttributeNames();
           int length = 0;
           Iterator<String> t = atr_names.asIterator();
            while(t.hasNext()) {
                t.next();
                ++length;
            }
           if (length > 2) {
               HashMap<String,Integer> values_temp = (HashMap<String, Integer>) session.getAttribute("Values");
               atr_names = session.getAttributeNames();
               atr_names.asIterator().forEachRemaining(k->{
                   if (!(k.equals("Values") || k.equals("Questions"))) {
                      String temp =(String) session.getAttribute(k);
                        ///////////
                        if (values_temp.containsKey(temp)){
                            Integer int_val = values_temp.get(temp);
                            int_val++;
                            values_temp.put(temp,int_val);
                            session.removeAttribute(k);
                        }
                        else {
                            values_temp.put(temp,1);
                            System.out.println("Map is not alright!!!");
                        }
                        //////////////////
                   }
               });
               session.removeAttribute("Values");
               session.setAttribute("Values",values_temp);
           }
           else{
                HashMap<String,Integer> val_temp = (HashMap<String, Integer>) session.getAttribute("Values");
                Collection<String> par_temp = parameters.values();
                par_temp.forEach(k->{
                    ///////////////////
                    if (val_temp.containsKey(k)) {
                        Integer i_temp = val_temp.get(k);
                        i_temp++;
                        val_temp.put(k,i_temp);
                    }
                    else {
                        val_temp.put(k,1);
                        System.out.println("Map is not alright!!!");
                    }
                    ///////////////////
                });
                session.removeAttribute("Values");
                session.setAttribute("Values",val_temp);
           }
        }
        */
    }

    private void updateAnswers(HashMap<Answer,Integer> answers,HashSet<Answer> cur_answers) {
        Iterator<Answer> itr_cur_ans = cur_answers.iterator();
        while (itr_cur_ans.hasNext()) {
            Answer temp_ans = itr_cur_ans.next();
            if (answers.containsKey(temp_ans)) {
                int temp_int = answers.get(temp_ans);
                temp_int++;
                answers.put(temp_ans,temp_int);
            }
            else {
                answers.put(temp_ans,1);
            }
        }
    }

    private void initValuesMap(HashSet<Answer> answers,HashMap<Answer,Integer>values) {
        Iterator<Answer> itr_ans = answers.iterator();
        while (itr_ans.hasNext()) {
            values.put(itr_ans.next(),0);
        }
    }

    private String createStatisticForm(HashMap<Answer,Integer> answers,HashSet<Question> questions) {
        String form = "<html>" +
                "<head><title>Statistic</title><meta charset=\"utf-8\">" +
                "<style>" +
                "p {font-size: 22px; margin: 10px;}" +
                "div {margin-bottom: 50px; margin-right: 50px; float: left;}" +
                "table {text-align: center; border-collapse: collapse; border: 3px solid black}" +
                "td {border: 1px solid black;}" +
                "</style>" +
                "<style type=\"text/css\">" +
                "td {font-family:Verdana, Arial, Helvetica, Tahoma, sans-serif;font-size:14px;padding: 5px}" +
                "</style>" +
                "</head>" +
                "<body>";
        Iterator<Question> itr_quest_temp = questions.stream().sorted(Comparator.comparingInt(Question::getQ_id)).iterator();
        while (itr_quest_temp.hasNext()) {
            Question question_temp = itr_quest_temp.next();
            Iterator<Answer> itr_temp_answer = answers.keySet().iterator();
            form += "<div><table><caption><tr><th colspan=\"2\"><p>"+question_temp.getQuestion()+"</p></th></tr></caption>";
            while(itr_temp_answer.hasNext()) {
                Answer answer_temp = itr_temp_answer.next();
                if (question_temp.getQ_id() == answer_temp.getQ_id()) {
                    String temp_swch = answer_temp.getAnswer();
                    switch (temp_swch) {
                        case "Cplusplus":
                            form += "<tr><td>C++</td><td>"+answers.get(answer_temp)+"</td></tr>";
                            break;
                        case "Csharp":
                            form += "<tr><td>C#</td><td>"+answers.get(answer_temp)+"</td></tr>";
                            break;
                        default:
                            form += "<tr><td>"+answer_temp.getAnswer()+"</td><td>"+answers.get(answer_temp)+"</td></tr>";
                            break;
                    }
                }
            }
            form += "</table></div>";
        }
        form += "</body></html>";
        return form;
    }
}
