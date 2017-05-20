package com.example.ian.comp2601_a1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ian on 2016-02-02.
 */
public class SubmitActivity extends Activity {

    private  final String TAG = Activity.class.getSimpleName() + " @" + System.identityHashCode(this);

    private String addr;
    private String subj;
    private String emailMsg;

    private String stName;
    private String stNumber;
    private EditText nameTextEdit;
    private EditText stNumTextEdit;
    private Button submitButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent(); // gets the previously created intent
        addr = intent.getStringExtra("email"); // will return "FirstKeyValue"
        emailMsg = intent.getStringExtra("xml");

        subj = getString(R.string.submitSubject);

        setContentView(R.layout.activity_name);

        nameTextEdit = (EditText) findViewById(R.id.userName);
        stNumTextEdit = (EditText) findViewById(R.id.userNumber);
        submitButton = (Button) findViewById(R.id.sButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Prepare email");
                stName = nameTextEdit.getText().toString();
                stNumber = stNumTextEdit.getText().toString();


                if(!stName.equals("") && !stNumber.equals("")){
                    String opentag = "<" + Question.XML_RESPONSE_HEAD + ">";
                    String newOpenTag = opentag + "<" + Question.XML_NAME + ">" + stName + "</" + Question.XML_NAME + ">";
                    newOpenTag += "<" + Question.XML_NUMBER + ">" + stNumber + "</" + Question.XML_NUMBER + ">";

                    emailMsg = emailMsg.replace(opentag, newOpenTag);

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + addr));

                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subj);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, emailMsg);

                    startActivity(Intent.createChooser(emailIntent, "Email Client ..."));

                }else{
                    Toast t;
                    t = Toast.makeText(SubmitActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT);
                    t.show();
                }

            }
        });

    }

}
