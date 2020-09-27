import java.util.HashMap;

public class SingleChoice extends Question {
    private HashMap<String, Boolean> answers = new HashMap<>();

    public SingleChoice(){
        this.question = "Question was not set.";
    }

    public SingleChoice(String q){
        this.question = q;
    }

    public void addAnswer(String a, boolean c){
        if(c){
            this.answers.clear(); //there should only be one answer to this question
            this.answers.put(a, true);
        }
        else System.out.println("Single choice question can only have one correct answer. Answer was not added.");
    }

    public HashMap<String, Boolean> getAnswerBank(){
        return answers;
    }

    public boolean hasFake(){
        return false;
    }
}
