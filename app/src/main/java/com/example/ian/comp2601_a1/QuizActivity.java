package com.example.ian.comp2601_a1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class QuizActivity extends AppCompatActivity {

    private static final String TAG = QuizActivity.class.getSimpleName();

    private Button mButtonA;
    private Button mButtonB;
    private Button mButtonC;
    private Button mButtonD;
    private Button mButtonE;

    private FloatingActionButton submitButton;

    private TextView textA;
    private TextView textB;
    private TextView textC;
    private TextView textD;
    private TextView textE;


    private Button mNextButton;
    private Button mPrevButton;

    private TextView mQuestionTextView;
    private ArrayList<Question> questions;
    private String userEmail;

    private int questionIndex;
    private Question currentQuestion;

    public void setEmail(String eml){userEmail = eml;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz); //set and inflate UI to manage
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        submitButton = (FloatingActionButton) findViewById(R.id.fab);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfQuestionsAnswered = 0;
                for(Question q: questions){
                    if(q.getSelectedAnswer() != null) numberOfQuestionsAnswered++;
                }
                String questionsAnswered = "" + numberOfQuestionsAnswered + " " + getString(R.string.outof) + " " + (questions.size()) + " " + getString(R.string.questionsAnswered);
                Log.i(TAG, "" + questionsAnswered);
                Snackbar.make(view, questionsAnswered, Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.submit), new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                Log.i(TAG, "SUBMIT EMAIL!!");
                                Intent submitIntent = new Intent(QuizActivity.this, SubmitActivity.class);
                                //or you can use intent.putExtra or parcelable
                                //Bundle InfoBundle = new Bundle();

                                String xml = questions2XML();

                                submitIntent.putExtra("email",userEmail);
                                submitIntent.putExtra("xml", xml);

                                startActivity(submitIntent);

                            }

                        }).show();
            }
        });

        mButtonA = (Button) findViewById(R.id.buttonA);
        mButtonA.setBackgroundColor(Color.BLUE);
        mButtonB = (Button) findViewById(R.id.buttonB);
        mButtonB.setBackgroundColor(Color.BLUE);
        mButtonC = (Button) findViewById(R.id.buttonC);
        mButtonC.setBackgroundColor(Color.BLUE);
        mButtonD = (Button) findViewById(R.id.buttonD);
        mButtonD.setBackgroundColor(Color.BLUE);
        mButtonE = (Button) findViewById(R.id.buttonE);
        mButtonE.setBackgroundColor(Color.BLUE);

        textA = (TextView) findViewById(R.id.textQuestionA);
        textA.setBackgroundColor(Color.LTGRAY);
        textA.setText(Question.NULL_ANSWER);
        textB = (TextView) findViewById(R.id.textQuestionB);
        textB.setBackgroundColor(Color.LTGRAY);
        textB.setText(Question.NULL_ANSWER);
        textC = (TextView) findViewById(R.id.textQuestionC);
        textC.setBackgroundColor(Color.LTGRAY);
        textC.setText(Question.NULL_ANSWER);
        textD = (TextView) findViewById(R.id.textQuestionD);
        textD.setBackgroundColor(Color.LTGRAY);
        textD.setText(Question.NULL_ANSWER);
        textE = (TextView) findViewById(R.id.textQuestionE);
        textE.setBackgroundColor(Color.LTGRAY);
        textE.setText(Question.NULL_ANSWER);

        mPrevButton = (Button) findViewById(R.id.prev_button);
        mNextButton = (Button) findViewById(R.id.next_button);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setTextColor(Color.BLUE);

        if(savedInstanceState != null){
            questionIndex = Integer.parseInt(savedInstanceState.getString(States.STATE_QUESTION_INDEX));
            userEmail = savedInstanceState.getString(States.STATE_EMAIL);
            questions = savedInstanceState.getParcelableArrayList("questions");

        }else{

            //Initialise Data Model objects
            questions = Question.exampleSet2();
            questionIndex = 0;
            currentQuestion = questions.get(0);
            userEmail = "";

            //Try to read resource data file with questions

            ArrayList<Question> parsedModel = null;
            try {
                InputStream iStream = getResources().openRawResource(R.raw.questions);
                BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
                //ArrayList<Question> parsedModel = Exam.parseFrom(bs);
                parsedModel = Exam.pullParseFrom(bReader, this);
                bReader.close();
                updateUI();
            }
            catch (java.io.IOException e){
                e.printStackTrace();

            }
            if(parsedModel == null || parsedModel.isEmpty())
                Log.i(TAG, "ERROR: Questions Not Parsed");
            questions = parsedModel;

            if(questions != null && questions.size() > 0)
                mQuestionTextView.setText("" + (questionIndex + 1) + ") " +
                        questions.get(questionIndex).toString());
        }

        updateUI();

        mButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Answer A selected");
                currentQuestion.setSelectedAnswer("A");
                updateUI();
            }
        });

        mButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Answer B selected");
                currentQuestion.setSelectedAnswer("B");
                updateUI();
            }
        });

        mButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Answer C selected");
                currentQuestion.setSelectedAnswer("C");
                updateUI();
            }
        });

        mButtonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Answer D selected");
                currentQuestion.setSelectedAnswer("D");
                updateUI();
            }
        });

        mButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Answer E selected");
                currentQuestion.setSelectedAnswer("E");
                updateUI();
            }
        });


        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i(TAG, "Prev Button Clicked");

                if(questionIndex > 0) questionIndex--;

                mQuestionTextView.setText("" + (questionIndex + 1) + ") " +
                        questions.get(questionIndex).toString());
                        currentQuestion = questions.get(questionIndex);

                updateUI();

            }

        });
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i(TAG, "Next Button Clicked");

                if(questionIndex < questions.size()-1) questionIndex++;

                mQuestionTextView.setText("" + (questionIndex + 1) + ") " +
                        questions.get(questionIndex).toString());
                        currentQuestion = questions.get(questionIndex);

                updateUI();

            }

        });

        updateUI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(States.STATE_QUESTION_INDEX, "" + questionIndex);
        outState.putString(States.STATE_EMAIL, userEmail);
        outState.putParcelableArrayList("questions", questions);
        super.onSaveInstanceState(outState);
    }

    public void updateUI(){

        currentQuestion = questions.get(questionIndex);



        //highlight the selected question blue
        if(currentQuestion != null){

            mQuestionTextView.setText("" + (questionIndex +1) + ":  " + currentQuestion.getQuestionString());
            textA.setBackgroundColor(Color.LTGRAY);
            textA.setText(currentQuestion.getAnswerA());
            textB.setBackgroundColor(Color.LTGRAY);
            textB.setText(currentQuestion.getAnswerB());
            textC.setBackgroundColor(Color.LTGRAY);
            textC.setText(currentQuestion.getAnswerC());
            textD.setBackgroundColor(Color.LTGRAY);
            textD.setText(currentQuestion.getAnswerD());
            textE.setBackgroundColor(Color.LTGRAY);
            textE.setText(currentQuestion.getAnswerE());


            if(currentQuestion.getSelectedAnswer() != null){
                if(currentQuestion.getSelectedAnswer().equals("A")){
                    textA.setBackgroundColor(Color.BLUE);
                } else if(currentQuestion.getSelectedAnswer().equals("B")){
                    textB.setBackgroundColor(Color.BLUE);
                } else if(currentQuestion.getSelectedAnswer().equals("C")){
                    textC.setBackgroundColor(Color.BLUE);
                } else if(currentQuestion.getSelectedAnswer().equals("D")){
                    textD.setBackgroundColor(Color.BLUE);
                } else if(currentQuestion.getSelectedAnswer().equals("E")){
                    textE.setBackgroundColor(Color.BLUE);
                }
            }
        }
    }

    private String questions2XML(){

        String retString = "<" + Question.XML_RESPONSE_HEAD + ">";

        for(Question q: questions){

            retString += "<" + Question.XML_QUESTION + ">";
            retString += "<" + Question.XML_ID + ">" + q.getID() + "</" + Question.XML_ID + ">";
            retString += "<" + Question.XML_RESPONSE + ">" + q.getSelectedAnswer() + "</" + Question.XML_RESPONSE + ">";
            retString += "</" + Question.XML_QUESTION + ">";

        }

        retString += "</" + Question.XML_RESPONSE_HEAD + ">";

        return retString;
    }
}




/*References:
http://stackoverflow.com/questions/6054562/how-to-make-the-corners-of-a-button-round
*/