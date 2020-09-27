import java.util.HashMap;

public abstract class Question {
    String question;

    public Question(){
        this.question = "Question was not set.";
    }

    public Question(String q){
        this.question = q;
    }

    void setQuestion(String q){
        this.question = q;
    }

    String getQuestion(){
        return question;
    }

    abstract void addAnswer(String a, boolean c);
    abstract HashMap<String, Boolean> getAnswerBank();
    abstract boolean hasFake();
}
