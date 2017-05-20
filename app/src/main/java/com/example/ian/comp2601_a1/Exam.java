package com.example.ian.comp2601_a1;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ian on 2016-01-31.
 */
public class Exam {

    private static final String TAG = Exam.class.getSimpleName();

    //XML tags used to define an exam of multiple choice questions.
    static final String XML_EXAM = "exam";
    static final String XML_QUESTION = "question";
    static final String XML_QUESTION_ID = "question_ID";
    static final String XML_QUESTION_TEXT = "question_text";

    static final String XML_ANSWER = "answer";
    static final String XML_ANSWER_TAG = "answer_tag";
    static final String XML_ANSWER_TEXT = "answer_text";

    static final String XML_EMAIL = "email";




    public static ArrayList<Question> pullParseFrom(BufferedReader reader, QuizActivity qAct){
        ArrayList<Question> questions = new ArrayList<Question>();

        String questionText = "";
        String questionTag = "";
        String answerA = "";
        String answerB = "";
        String answerC = "";
        String answerD = "";
        String answerE = "";
        String textType = "";
        String emailName = "";
        String currentAnswer = "";

        // Get our factory and create a PullParser
        XmlPullParserFactory factory = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(reader); // set input file for parser
            int eventType = xpp.getEventType(); // get initial eventType

            // Loop through pull events until we reach END_DOCUMENT
            while (eventType != XmlPullParser.END_DOCUMENT) {

                // handle the xml tags encountered
                switch (eventType) {
                    case XmlPullParser.START_TAG: //XML opening tags
                        //to do
                        Log.i(TAG, "Start tag: " + xpp.getName() );

                        if(xpp.getName().equals(Exam.XML_QUESTION)) {
                            questionText = "";
                            questionTag = "";
                            answerA = "";
                            answerB = "";
                            answerC = "";
                            answerD = "";
                            answerE = "";
                            currentAnswer = "";
                            textType = "";
                        } else if(xpp.getName().equals(Exam.XML_QUESTION_TEXT)){
                            textType = "q";
                        } else if(xpp.getName().equals(Exam.XML_QUESTION_ID)){
                            textType = "qid";
                        } else if(xpp.getName().equals(Exam.XML_ANSWER_TEXT)){
                            textType = "a";
                        } else if(xpp.getName().equals(Exam.XML_EMAIL)){
                            textType = "e";
                        } else if(xpp.getName().equals(Exam.XML_ANSWER_TAG)){
                            textType = "atag";
                        } else textType = "";

                        break;

                    case XmlPullParser.TEXT:
                        //to do
                        Log.i(TAG, "Text: " + xpp.getText().trim() );

                        if(textType.equals("q")){
                            questionText = xpp.getText().trim().replace('\n', ' ').replace('\t', ' ').replace("    ", " ");
                        } else if(textType.equals("qid")){
                            questionTag = xpp.getText().trim().replace('\n', ' ').replace('\t', ' ').replace("    ", " ");
                        } else if(textType.equals("qid")){
                            questionTag = xpp.getText().trim().replace('\n', ' ').replace('\t', ' ').replace("    ", " ");
                        } else if(textType.equals("a")){
                            if(currentAnswer.equals("A")){
                                answerA = xpp.getText().trim().replace('\n', ' ').replace('\t', ' ').replace("    ", " ");
                            } else if(currentAnswer.equals("B")){
                                answerB = xpp.getText().trim().replace('\n', ' ').replace('\t', ' ').replace("    ", " ");
                            } else if(currentAnswer.equals("C")){
                                answerC = xpp.getText().trim().replace('\n', ' ').replace('\t', ' ').replace("    ", " ");
                            } else if(currentAnswer.equals("D")){
                                answerD = xpp.getText().trim().replace('\n', ' ').replace('\t', ' ').replace("    ", " ");
                            } else if(currentAnswer.equals("E")){
                                answerE = xpp.getText().trim().replace('\n', ' ').replace('\t', ' ').replace("    ", " ");
                            }
                        } else if(textType.equals("atag")){
                            currentAnswer = xpp.getText().trim().replace('\n', ' ').replace('\t', ' ').replace("    ", " ");
                        } else if(textType.equals("e")){
                            emailName = xpp.getText().trim().replace('\n', ' ').replace('\t', ' ').replace("    ", " ");
                        }


                        textType = "";

                        break;

                    case XmlPullParser.END_TAG: //XML closing tags

                        if(xpp.getName().equals(Exam.XML_QUESTION) &&
                                !questionText.equals("") &&
                                !questionTag.equals("") &&
                                !answerA.equals("") &&
                                !answerB.equals("") &&
                                !answerC.equals("") &&
                                !answerD.equals("") &&
                                !answerE.equals("") &&
                                !emailName.equals("")){
                            Log.i(TAG, "New question: " + questionTag + " --- " + questionText);


                            Question newQ = new Question(questionText,
                                    answerA,
                                    answerB,
                                    answerC,
                                    answerD,
                                    answerE,
                                    questionTag);
                            questions.add(newQ);
                            qAct.setEmail(emailName);

                        }

                        textType = "";

                        Log.i(TAG, "End tag: " + xpp.getName() );
                        break;

                    default:
                        break;
                }
                //iterate
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return questions;

    }


}
