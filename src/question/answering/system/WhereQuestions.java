package question.answering.system;

import static question.answering.system.QA_Form.answers;
import static question.answering.system.QA_Form.questionEntities;
import static question.answering.system.QA_Form.questionList;
import static question.answering.system.QA_Form.questionTags;
import static question.answering.system.QA_Form.whereQuestions;

public class WhereQuestions {
    public void answerQuestion(){
        // Split All Question to only when question
        for (int i = 0; i < questionList.size(); i++){
            if (questionList.get(i).question.startsWith("Where")){
                whereQuestions.add(new Question(questionList.get(i).question, questionList.get(i).answer));
            }
        }

        // Print All When Questions
        System.out.println("Where Questions");
        for (int i = 0; i < whereQuestions.size(); i++){
            System.out.println(whereQuestions.get(i).question);
        }

        //==== Question Entities ====
        // Split All When Question to only questions that have the entities
        if (!whereQuestions.isEmpty()){
            if (!questionEntities.isEmpty()){
                for (int c = 0; c < questionEntities.size(); c++){
                    for (int i = 0; i < whereQuestions.size(); i++){
                        if (whereQuestions.get(i).question.contains(questionEntities.get(c))){
                            answers.add(new Question(whereQuestions.get(i).question, whereQuestions.get(i).answer));
                        }
                    }
                }
            }
            else{
                if (!questionTags.isEmpty()){
                    for (int c = 0; c < questionTags.size(); c++){
                        for (int i = 0; i < whereQuestions.size(); i++){
                            if (whereQuestions.get(i).question.contains(questionTags.get(c))){
                                answers.add(new Question(whereQuestions.get(i).question, whereQuestions.get(i).answer));
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
        int i = 0;
        while (i < questionTags.size()){
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
                for (int k = 0; k < whereQuestions.size(); k++){
                    if (whereQuestions.get(k).question.contains(questionTags.get(i))){
                        continue;
                    }
                    else{
                        whereQuestions.remove(k);
                    }
                }
            }
            i++;
        }
    }
}
