public class Answer {
    private int q_id;
    private String answer;

    public Answer(int q_id,String answer) {
        this.q_id = q_id;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question id: "+q_id+"\tAnswer: "+answer+"\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Answer ans = (Answer) obj;
        return q_id == ans.getQ_id() && (answer != null && answer.equals(ans.getAnswer()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = answer.hashCode();
        result = prime * result + Integer.hashCode(q_id);
        return result;
    }

    public int getQ_id() {return q_id;}
    public String getAnswer() {return answer;}
}
