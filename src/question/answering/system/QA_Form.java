package question.answering.system;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.Triple;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QA_Form extends javax.swing.JFrame {

    public static ArrayList<Question> questionList = new ArrayList<>();
    public static ArrayList<Question> whenQuestions = new ArrayList<>();
    public static ArrayList<Question> whoQuestions = new ArrayList<>();
    public static ArrayList<Question> whereQuestions = new ArrayList<>();
    public static ArrayList<Question> howQuestions = new ArrayList<>();
    public static ArrayList<Question> answers = new ArrayList<>();
    public static ArrayList<Question> andidateFinalAnswers = new ArrayList<>();
    
    public static ArrayList<String> questionEntities = new ArrayList<>();
    public static ArrayList<String> questionTags = new ArrayList<>();
    
    
    String question;
    
    static AbstractSequenceClassifier<CoreLabel> classifier;
    static String serializedClassifier;
    static MaxentTagger tagger;
    public QA_Form() {
        initComponents();
        
        // 1 >> Read the dataset.
        BufferedReader crunchifyBuffer = null;
		
        try {
            String crunchifyLine;
            crunchifyBuffer = new BufferedReader(new FileReader("C:/Users/SHeBO/Documents/NetBeansProjects/Quesrion Answering System/dataset.txt"));

            // How to read file in java line by line?
            while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
                convertCSVtoArrayList(crunchifyLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (crunchifyBuffer != null)
                    crunchifyBuffer.close();
            } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }
        }
    }
    
    public static void convertCSVtoArrayList(String crunchifyCSV) {

        if (crunchifyCSV != null) {
            String[] splitData = crunchifyCSV.split("\\s*,\\s*");
            questionList.add(new Question(splitData[0], splitData[1]));
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        questionField = new javax.swing.JTextField();
        askBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        answerArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        questionField.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        questionField.setToolTipText("");
        questionField.setBorder(null);
        questionField.setPreferredSize(new java.awt.Dimension(50, 15));

        askBtn.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        askBtn.setText("Ask");
        askBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                askBtnActionPerformed(evt);
            }
        });

        answerArea.setColumns(20);
        answerArea.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        answerArea.setRows(5);
        jScrollPane1.setViewportView(answerArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(questionField, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(askBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(questionField, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(askBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void askBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_askBtnActionPerformed
        // TODO add your handling code here:
        
        // get user question
        question = questionField.getText();
        
        // extract entities from question
        List<Triple<String,Integer,Integer>> triples = classifier.classifyToCharacterOffsets(question);
        for (Triple<String,Integer,Integer> trip : triples) {
            String entity = question.substring(trip.second(), trip.third());
            questionEntities.add(entity);
        }
        
        System.out.println("======================================");
        System.out.print("Entities : ");
        for (int i = 0; i < questionEntities.size(); i++)
            System.out.print(questionEntities.get(i) + " | ");
        System.out.println();
        
        // extract pos tags from the question
        String tags = tagger.tagString(question);
        
        // split tags in an array
        String [] splitedTagged = tags.split("\\s+");
        
        // Put only nouns and verbs into the array list
        for (String tag : splitedTagged) {
            if (tag.contains("NN") || tag.contains("VB")) {
                int position = tag.indexOf("_");
                questionTags.add(tag.substring(0, position));
            }
        }
        
        System.out.print("Tags : ");
        for (int i = 0; i < questionTags.size(); i++)
            System.out.print(questionTags.get(i) + " | ");
        System.out.println();
        System.out.println("======================================");
        
        // Find the answer
        if (question.startsWith("When") || question.startsWith("when")){
            new WhenQuestions().answerQuestion();
        }
        else if (question.startsWith("Who") || question.startsWith("who")){
            new WhoQuestions().answerQuestion();
        }
        else if (question.startsWith("Where") || question.startsWith("where")){
            new WhereQuestions().answerQuestion();
        }
        else if (question.startsWith("How") || question.startsWith("how")){
            new HowQuestions().answerQuestion();
        }
        
        // Print the candidate answer
        // Candidate Answers
        System.out.println("-------> Candidate Answers <-------");
        for (int i = 0; i < answers.size(); i++){
            System.out.println(answers.get(i).answer);
        }
        
        if (!answers.isEmpty())
            answerArea.setText(answers.get(0).answer);
        else
            answerArea.setText("Answer not found");
        
        System.out.println("-----------------------------------");
        
        // Remove all elements from answers, entities, tags list for new question
        answers.clear();
        questionEntities.clear();
        questionTags.clear();
        whenQuestions.clear();
        whereQuestions.clear();
        whoQuestions.clear();
        howQuestions.clear();
    }//GEN-LAST:event_askBtnActionPerformed

    public static void main(String args[]) throws IOException, ClassCastException, ClassNotFoundException {
        // load ner classifier
        serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";
        classifier = CRFClassifier.getClassifier(serializedClassifier);
        // load pos tagger
        tagger = new MaxentTagger("taggers/english-bidirectional-distsim.tagger");
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QA_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QA_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QA_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QA_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QA_Form().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea answerArea;
    private javax.swing.JButton askBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField questionField;
    // End of variables declaration//GEN-END:variables
}
