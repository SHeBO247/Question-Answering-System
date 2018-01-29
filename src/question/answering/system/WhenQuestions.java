package question.answering.system;

import static question.answering.system.QA_Form.answers;
import static question.answering.system.QA_Form.questionEntities;
import static question.answering.system.QA_Form.questionList;
import static question.answering.system.QA_Form.questionTags;
import static question.answering.system.QA_Form.whenQuestions;

public class WhenQuestions {
    
    public void answerQuestion(){
        // Split All Question to only when question
        for (int i = 0; i < questionList.size(); i++){
            if (questionList.get(i).question.startsWith("When")){
                whenQuestions.add(new Question(questionList.get(i).question, questionList.get(i).answer));
            }
        }

        // Print All When Questions
        System.out.println("When Questions");
        for (int i = 0; i < whenQuestions.size(); i++){
            System.out.println(whenQuestions.get(i).question);
        }

        //==== Question Entities ====
        // Split All When Question to only questions that have the entities
        if (!whenQuestions.isEmpty()){
            if (!questionEntities.isEmpty()){
                for (int c = 0; c < questionEntities.size(); c++){
                    for (int i = 0; i < whenQuestions.size(); i++){
                        if (whenQuestions.get(i).question.contains(questionEntities.get(c))){
                            answers.add(new Question(whenQuestions.get(i).question, whenQuestions.get(i).answer));
                        }
                    }
                }
            }
            else{
                if (!questionTags.isEmpty()){
                    for (int c = 0; c < questionTags.size(); c++){
                        for (int i = 0; i < whenQuestions.size(); i++){
                            if (whenQuestions.get(i).question.contains(questionTags.get(c))){
                                answers.add(new Question(whenQuestions.get(i).question, whenQuestions.get(i).answer));
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
        System.out.println("Questions of 'When' that have entities and tags :");
        for (int i = 0; i < answers.size(); i++){
            System.out.println(answers.get(i).question);
        }


        //==== Question Tags ====
        int i = 0;
        while (i < questionTags.size()){
            // If There are entities
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
            // If There are not entities
            else{
                for (int k = 0; k < whenQuestions.size(); k++){
                    if (whenQuestions.get(k).question.contains(questionTags.get(i))){
                        continue;
                    }
                    else{
                        whenQuestions.remove(k);
                    }
                }
            }
            i++;
        }
    }
    
}
