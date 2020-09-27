import java.util.ArrayList;
import java.util.HashMap;

public class VotingService {
    private String currentQuestion;
    private Question question;
    private HashMap<String, Integer> distribution;
    private int[] result = {0,0}; //result[0] = amount wrong; result[1] = amount right
    private HashMap<String, ArrayList<String>> ansReceived = new HashMap<>();

    void setCurrentQuestion(Question q){
        this.currentQuestion = q.getQuestion();
        this.question = q;
        if(q.hasFake()) this.distribution = new HashMap<>();
    }

    void submitAnswer(String id, ArrayList<String> answer) {
        if(ansReceived.containsKey(id)) ansReceived.replace(id, answer);
        else ansReceived.put(id, answer);
    }

    //ansReceived will have been populated by now
    void closeSubmissions(){
        Object[] students = ansReceived.keySet().toArray(); //array of student ids
        HashMap<String, Boolean> temp = question.getAnswerBank();
        boolean multiChoice = question.hasFake();

        //finalize each student's answers
        for(Object student : students){
            boolean correct = true; //assume student is correct until a wrong answer is encountered
            ArrayList<String> studentAns = ansReceived.get((String)student); //gets current student's list of answers
            for(String answer : studentAns){ //for every answer that student sent...
                if(temp.containsKey(answer) && !temp.get(answer)) correct = false; //encountered wrong answer
                else if(!temp.containsKey(answer)) correct = false; //encounter wrong (nonexistent) answer

                if(multiChoice){ //we need to keep track of the distribution of results if multiChoice
                    if(distribution.containsKey(answer)){
                        int lastCount = distribution.get(answer);
                        lastCount++;
                        distribution.replace(answer, lastCount);
                    }
                    else distribution.put(answer, 1);
                }
            }
            //note whether this student was right/wrong
            if(correct) result[1]++;
            else result[0]++;
        }
    }

    void displayAnswers(){
        System.out.println(currentQuestion);
        HashMap<String, Boolean> temp = question.getAnswerBank();
        Object[] answerOptions = temp.keySet().toArray();

        if(answerOptions.length!=1){ //if there are multiple options
            int currentAnswer = 0;
            for(Object a : answerOptions){
                currentAnswer++;
                System.out.println(currentAnswer + ". " + a);
            }
            System.out.println("\n");
        }
        else System.out.println("Please enter an answer...\n");
    }

    void displayStats(){
        System.out.println("CALCULATING RESULTS...");
        if(distribution!=null){
            int count;
            Object[] tempAns = question.getAnswerBank().keySet().toArray();
            for(Object ans : tempAns){
                count = distribution.getOrDefault(ans, 0);
                System.out.println(count+ " answered \""+ans+"\".");
            }
            System.out.println("\n");
            double total = result[0] + result[1];
            double wrongPercentage = (((double)result[0])/total)*100;
            double rightPercentage = (((double)result[1])/total)*100;
            System.out.println("The correct answer(s):");
            HashMap<String, Boolean> correct = question.getAnswerBank();
            Object[] keys = correct.keySet().toArray();
            for(Object key : keys){
                if(correct.get(key)) System.out.println(key);
            }
            System.out.println(String.format("%.2f", wrongPercentage)+"% answered incorrectly.\n"+String.format("%.2f", rightPercentage)+"% answered correctly.\n");
        }
        else{
            Object[] correct = question.getAnswerBank().keySet().toArray();
            double total = result[0] + result[1];
            double wrongPercentage = (((double)result[0])/total)*100;
            double rightPercentage = (((double)result[1])/total)*100;
            System.out.println("The correct answer: "+correct[0]);
            System.out.println(String.format("%.2f", wrongPercentage)+"% answered incorrectly.\n"+String.format("%.2f", rightPercentage)+"% answered correctly.\n");
        }
    }

}
