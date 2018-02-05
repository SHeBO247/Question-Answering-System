package question.answering.system;

import static question.answering.system.QA_Form.answers;
import static question.answering.system.QA_Form.whoQuestions;
import static question.answering.system.QA_Form.questionEntities;
import static question.answering.system.QA_Form.questionList;
import static question.answering.system.QA_Form.questionTags;

public class WhoQuestions {
    public void answerQuestion(){
        // Split All Question to only when question
        for (int i = 0; i < questionList.size(); i++){
            if (questionList.get(i).question.startsWith("How")){
                whoQuestions.add(new Question(questionList.get(i).id, questionList.get(i).question, questionList.get(i).answer));
            }
        }

        // Print All When Questions
        System.out.println("Who Questions");
        for (int i = 0; i < whoQuestions.size(); i++){
            System.out.println(whoQuestions.get(i).question);
        }

        //==== Question Entities ====
        // Split All When Question to only questions that have the entities
        if (!whoQuestions.isEmpty()){
            if (!questionEntities.isEmpty()){
                for (int c = 0; c < questionEntities.size(); c++){
                    for (int i = 0; i < whoQuestions.size(); i++){
                        if (whoQuestions.get(i).question.contains(questionEntities.get(c))){
                            answers.add(new Question(whoQuestions.get(i).id, whoQuestions.get(i).question, whoQuestions.get(i).answer));
                        }
                    }
                }
            }
            else{
                if (!questionTags.isEmpty()){
                    for (int c = 0; c < questionTags.size(); c++){
                        for (int i = 0; i < whoQuestions.size(); i++){
                            if (whoQuestions.get(i).question.contains(questionTags.get(c))){
                                answers.add(new Question(whoQuestions.get(i).id, whoQuestions.get(i).question, whoQuestions.get(i).answer));
                            }
                        }
                    }
                }
                else{
                    System.out.println("There no answer for your question !");
                    System.exit(0);
                }
            }
        }

        // Print All Entities Questions
        System.out.println("Questions of 'Who' that have entities and tags :");
        for (int i = 0; i < answers.size(); i++){
            for (int j = 1; j < answers.size(); j++){
                if (answers.get(i).id == answers.get(j).id){
                    answers.remove(j);
                }
            }
        }
        
        for (int i = 0; i < answers.size(); i++){
            System.out.println(answers.get(i).question);
        }


        //==== Question Tags ====
        for (int i = 0; i < questionTags.size(); i++){
            for (int k = 0; k < answers.size(); k++){
                if (answers.get(k).question.contains(questionTags.get(i))){
                    continue;
                }
                else{
                    answers.remove(k);
                }
            }
        }
    }
}