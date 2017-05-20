package com.example.ian.comp2601_a1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Ian on 2016-01-31.
 * /
 *Reference:
 * http://stackoverflow.com/questions/7181526/how-can-i-make-my-custom-objects-be-parcelable
 * */

public class Question implements Parcelable {

    private static final String TAG = Question.class.getSimpleName();

    //XML tags the define Question properties
    public static final String XML_QUESTION = "question";
    public static final String XML_QUESTION_TEXT = "question_text";
    public static final String XML_ID = "question_ID";
    public static final String XML_ANSWER_A = "answerA";
    public static final String XML_ANSWER_B = "answerB";
    public static final String XML_ANSWER_C = "answerC";
    public static final String XML_ANSWER_D = "answerD";
    public static final String XML_ANSWER_E = "answerE";
    public static final String XML_RESPONSE = "response";
    public static final String XML_NAME = "student_name";
    public static final String XML_NUMBER = "student_number";
    public static final String XML_RESPONSE_HEAD = "responses";

    public static final String NULL_ANSWER = " - ";
    /*public static final String XML_ATTR_CONTRIBUTER = "contributor";*/

    private String mQuestionString; //id of string resource representing the question

    //Strings representing the multiple guess answers
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String answerE;

    //String reperesenting A/B/C/D/E that the user has guessed or null if they have not answered yet
    private String selectedAnswer;

    private String questionID;

    public Question(String cQuestion, String cAnsA, String cAnsB, String cAnsC, String cAnsD, String cAnsE, String cID){
        mQuestionString = cQuestion;
        answerA = cAnsA;
        answerB = cAnsB;
        answerC = cAnsC;
        answerD = cAnsD;
        answerE = cAnsE;

        questionID = cID;

        selectedAnswer = null; //questions always start without a curretn guess

    }

    public Question(Parcel in){
        String[] data = new String[8];
        in.readStringArray(data);

        this.mQuestionString = data[0];
        this.answerA = data[1];
        this.answerB = data[2];
        this.answerC = data[3];
        this.answerD = data[4];
        this.answerE = data[5];
        this.questionID = data[6];
        this.selectedAnswer = data[7];

    }


    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.mQuestionString,
                this.answerA,
                this.answerB,
                this.answerC,
                this.answerD,
                this.answerE,
                this.questionID,
                this.selectedAnswer
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getQuestionString(){return mQuestionString;}
    public String getAnswerA(){return answerA;}
    public String getAnswerB(){return answerB;}
    public String getAnswerC(){return answerC;}
    public String getAnswerD(){return answerD;}
    public String getAnswerE(){return answerE;}

    public String getSelectedAnswer(){return selectedAnswer;}
    public void setSelectedAnswer(String newAnswer){selectedAnswer = newAnswer;}


    public String getID(){return questionID;}

    public String toString(){
        String toReturn = "";
        toReturn += mQuestionString;
        return toReturn;
    }


    public static ArrayList<Question> exampleSet1(){
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question(
                "",
                "",
                "",
                "",
                "",
                "",
                "0"
        ));

        return questions;
    }

    public static ArrayList<Question> exampleSet2(){
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question(
                "",
                "",
                "",
                "",
                "",
                "",
                "0"
        ));

        return questions;
    }

}


