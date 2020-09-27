import java.util.HashMap;

public class MultipleChoice extends Question {
    private HashMap<String, Boolean> answers = new HashMap<>();

    public MultipleChoice(){
        this.question = "Question was not set.";
    }

    public MultipleChoice(String q){
        this.question = q;
    }

    public void addAnswer(String a, boolean c){
        this.answers.put(a, c);
    }

    public void removeAnswer(String a){
        this.answers.remove(a);
    }

    public HashMap<String, Boolean> getAnswerBank(){
        return answers;
    }

    public boolean hasFake(){
        return true;
    }
}
