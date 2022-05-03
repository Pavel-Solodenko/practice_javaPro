public class Question {
    private int q_id;
    private String question;

    public Question(int q_id,String question) {
        this.q_id = q_id;
        this.question = question;
    }

    @Override
    public String toString() {
        return "Question id: "+q_id+"\tQuestion: "+question+"\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Question quest = (Question) obj;
        return q_id == quest.q_id && (question != null && question.equals(quest.getQuestion()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = question.hashCode();
        result = prime * result + Integer.hashCode(q_id);
        return result;
    }

    public int getQ_id() {return q_id;}
    public String getQuestion() {return question;}
}
