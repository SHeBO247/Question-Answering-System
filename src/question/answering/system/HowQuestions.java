package question.answering.system;

import static question.answering.system.QA_Form.answers;
import static question.answering.system.QA_Form.howQuestions;
import static question.answering.system.QA_Form.questionEntities;
import static question.answering.system.QA_Form.questionList;
import static question.answering.system.QA_Form.questionTags;

public class HowQuestions {
    public void answerQuestion(){
        // Split All Question to only when question
        for (int i = 0; i < questionList.size(); i++){
            if (questionList.get(i).question.startsWith("How")){
                howQuestions.add(new Question(questionList.get(i).question, questionList.get(i).answer));
            }
        }

        // Print All When Questions
        System.out.println("How Questions");
        for (int i = 0; i < howQuestions.size(); i++){
            System.out.println(howQuestions.get(i).question);
        }

        //==== Question Entities ====
        // Split All When Question to only questions that have the entities
        if (!howQuestions.isEmpty()){
            if (!questionEntities.isEmpty()){
                for (int c = 0; c < questionEntities.size(); c++){
                    for (int i = 0; i < howQuestions.size(); i++){
                        if (howQuestions.get(i).question.contains(questionEntities.get(c))){
                            answers.add(new Question(howQuestions.get(i).question, howQuestions.get(i).answer));
                        }
                    }
                }
            }
            else{
                if (!questionTags.isEmpty()){
                    for (int c = 0; c < questionTags.size(); c++){
                        for (int i = 0; i < howQuestions.size(); i++){
                            if (howQuestions.get(i).question.contains(questionTags.get(c))){
                                answers.add(new Question(howQuestions.get(i).question, howQuestions.get(i).answer));
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
        System.out.println("Questions of 'Where' that have entities and tags :");
        for (int i = 0; i < answers.size(); i++){
            System.out.println(answers.get(i).question);
        }


        //==== Question Tags ====
        //int i = 0;
        //while (i < questionTags.size()){
        for (int i = 0; i < questionTags.size(); i++){
            if (!answers.isEmpty()){
                for (int k = 0; k < answers.size(); k++){
                    if (answers.get(k).question.contains(questionTags.get(i))){
                        continue;
                    }
                    else{
                        answers.remove(k);
                    }
                }
            }
            else{
                for (int k = 0; k < howQuestions.size(); k++){
                    if (howQuestions.get(k).question.contains(questionTags.get(i))){
                        continue;
                    }
                    else{
                        howQuestions.remove(k);
                    }
                }
            }
        }
    }
}
