import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class SimulationDriver {
    private void simulatingStudentAnswers(VotingService v, Question q, ArrayList<Student> classroom, int n){
        System.out.println("SIMULATING STUDENTS ANSWERING...");
        Random rand = new Random();
        HashMap<String, Boolean> options = q.getAnswerBank();
        Object[] keys = options.keySet().toArray();
        String randomValue = (String) keys[rand.nextInt(keys.length)]; //choose a random answer

        for(Student s: classroom){
            ArrayList<String> studentAns = new ArrayList<>();
            int randInt = rand.nextInt(10); //to simulate a random amount of retries

            for(int i=0;i<randInt;i++){
                studentAns.clear();
                if(n==1){ //random chance of being right or wrong
                    boolean tf = rand.nextBoolean();
                    if(tf) studentAns.add((String)keys[0]);
                    else studentAns.add("wrong"); //single choice incorrect answer
                }
                else{ //select a random selection from multiple choice answers
                    while(studentAns.size()!=n) {//amount of answers the student is expected to choose
                        if(!studentAns.contains(randomValue)){
                            studentAns.add(randomValue);
                        }
                        else randomValue = (String) keys[rand.nextInt(keys.length)]; //choose a diff random answer
                    }
                }
            }
            v.submitAnswer(s.getStudentID(), studentAns);
        }
        System.out.println("Students have answered!\n");
    }

    public static void main(String[] args) {
        VotingService votingSession = new VotingService();
        ArrayList<Student> classroom = new ArrayList<>(); // the students in this session

        //only necessary to aid in the random simulation of student answers
        SimulationDriver sd = new SimulationDriver();

        //POPULATING the session with a random amount of students
        Random rand = new Random();
        int randomNum = 101 - rand.nextInt(100); //random amount of students, never 0
        for(int i=0;i<randomNum;i++){
            Student joiningStudent = new Student(UUID.randomUUID().toString());
            classroom.add(joiningStudent);
        }

        //CREATING questions
        SingleChoice firstQuestion = new SingleChoice("What is Earth's gravity in m/s^2? (write answer to the thousandth place)");
        firstQuestion.addAnswer("9.807", true);

        SingleChoice secondQuestion = new SingleChoice("What is Mars' gravity in m/s^2? (write answer to the thousandth place)");
        secondQuestion.addAnswer("3.711", true);

        MultipleChoice thirdQuestion = new MultipleChoice("What are Newton's three laws of motion?");
        thirdQuestion.addAnswer("Every object in a state of uniform motion will remain in that state of motion unless an external force acts on it", true);
        thirdQuestion.addAnswer("Force equals mass times acceleration", true);
        thirdQuestion.addAnswer("For every action there is an equal and opposite reaction", true);
        thirdQuestion.addAnswer("Density equals mass over volume", false);
        thirdQuestion.addAnswer("The laws of physics are the same for all inertial reference frames", false);

        //ASKING the first question from students and retrieving results
        votingSession.setCurrentQuestion(firstQuestion);
        votingSession.displayAnswers();
        sd.simulatingStudentAnswers(votingSession, firstQuestion, classroom, 1);
        votingSession.closeSubmissions();
        votingSession.displayStats();
        System.out.println("-------------------------");
        //ASKING the second question from students and retrieving results
        votingSession.setCurrentQuestion(secondQuestion);
        votingSession.displayAnswers();
        sd.simulatingStudentAnswers(votingSession, secondQuestion, classroom, 1);
        votingSession.closeSubmissions();
        votingSession.displayStats();
        System.out.println("-------------------------");
        //ASKING the third question from students and retrieving results
        votingSession.setCurrentQuestion(thirdQuestion);
        votingSession.displayAnswers();
        sd.simulatingStudentAnswers(votingSession, thirdQuestion, classroom, 3);
        votingSession.closeSubmissions();
        votingSession.displayStats();
    }
}
