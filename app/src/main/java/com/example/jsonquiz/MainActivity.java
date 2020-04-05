package com.example.jsonquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView questionTV;
    Button answerBT1, answerBT2, answerBT3, answerBT4;

    List<QuestionItem> questionItems;

    int currentQuestion = 0;
    int correct = 0, wrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViewReferences();
        //Get all questions
        loadAllQuestions();



        //Shuffle the questions
        Collections.shuffle(questionItems);
        //Load the first questions
        showQuestions(currentQuestion);

        setAnswersOnClickListeners();




    }


    private void getViewReferences(){
        questionTV = (TextView) findViewById(R.id.question);
        answerBT1 = (Button)findViewById(R.id.answer1);
        answerBT2 = (Button)findViewById(R.id.answer2);
        answerBT3 = (Button)findViewById(R.id.answer3);
        answerBT4 = (Button)findViewById(R.id.answer4);
    }

    private void setAnswersOnClickListeners(){
        answerBT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checks if the answer is correct and adds a point respectively
                if(questionItems.get(currentQuestion).getAnswer1().equals(questionItems.get(currentQuestion).getCorrect())){
                    correct++;
                    Toast.makeText(MainActivity.this,"Correct", Toast.LENGTH_SHORT).show();
                } else {
                    wrong++;
                    Toast.makeText(MainActivity.this,"WROOOOONG!!!, the right one is " + questionItems.get(currentQuestion).getCorrect()
                            , Toast.LENGTH_SHORT).show();
                }

                //Load next question if any
                if(currentQuestion < questionItems.size() - 1){
                    currentQuestion++;
                    showQuestions(currentQuestion);
                }else{
                    //GAME OVER
                    Intent intent = new Intent(getApplicationContext(), EndActivity.class);
                    intent.putExtra("correct",correct);
                    intent.putExtra("wrong",wrong);
                    startActivity(intent);
                    finish();
                }

            }
        });
        answerBT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checks if the answer is correct and adds a point respectively
                if(questionItems.get(currentQuestion).getAnswer2().equals(questionItems.get(currentQuestion).getCorrect())){
                    correct++;
                    Toast.makeText(MainActivity.this,"Correct", Toast.LENGTH_SHORT).show();
                } else {
                    wrong++;
                    Toast.makeText(MainActivity.this,"WROOOOONG!!!, the right one is " + questionItems.get(currentQuestion).getCorrect(), Toast.LENGTH_SHORT).show();
                }

                //Load next question if any
                if(currentQuestion < questionItems.size() - 1){
                    currentQuestion++;
                    showQuestions(currentQuestion);
                }else{
                    //GAME OVER
                    Intent intent = new Intent(getApplicationContext(), EndActivity.class);
                    intent.putExtra("correct",correct);
                    intent.putExtra("wrong",wrong);
                    startActivity(intent);
                    finish();
                }

            }
        });
        answerBT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checks if the answer is correct and adds a point respectively
                if(questionItems.get(currentQuestion).getAnswer3().equals(questionItems.get(currentQuestion).getCorrect())){
                    correct++;
                    Toast.makeText(MainActivity.this,"Correct", Toast.LENGTH_SHORT).show();
                } else {
                    wrong++;
                    Toast.makeText(MainActivity.this,"WROOOOONG!!!, the right one is " + questionItems.get(currentQuestion).getCorrect(), Toast.LENGTH_SHORT).show();
                }

                //Load next question if any
                if(currentQuestion < questionItems.size() - 1){
                    currentQuestion++;
                    showQuestions(currentQuestion);
                }else{
                    //GAME OVER
                    Intent intent = new Intent(getApplicationContext(), EndActivity.class);
                    intent.putExtra("correct",correct);
                    intent.putExtra("wrong",wrong);
                    startActivity(intent);
                    finish();
                }

            }
        });
        answerBT4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checks if the answer is correct and adds a point respectively
                if(questionItems.get(currentQuestion).getAnswer4().equals(questionItems.get(currentQuestion).getCorrect())){
                    correct++;
                    Toast.makeText(MainActivity.this,"Correct", Toast.LENGTH_SHORT).show();
                } else {
                    wrong++;
                    Toast.makeText(MainActivity.this,"WROOOOONG!!!, the right one is " + questionItems.get(currentQuestion).getCorrect(), Toast.LENGTH_SHORT).show();
                }

                //Load next question if any
                if(currentQuestion < questionItems.size() - 1){
                    currentQuestion++;
                    showQuestions(currentQuestion);
                }else{
                    //GAME OVER
                    Intent intent = new Intent(getApplicationContext(), EndActivity.class);
                    intent.putExtra("correct",correct);
                    intent.putExtra("wrong",wrong);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    private void loadAllQuestions(){
        questionItems = new ArrayList<>();

        //Load all questions into jsonString
        String jsonStr = loadJasonFromFile("questions.json");

        //Load all data into a string
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray questions = jsonObject.getJSONArray("questions");

            for(int i = 0; i < questions.length(); i++){
                JSONObject question = questions.getJSONObject(i);

                String questionString = question.getString("question");
                String answer1String = question.getString("answer1");
                String answer2String = question.getString("answer2");
                String answer3String = question.getString("answer3");
                String answer4String = question.getString("answer4");
                String correctString = question.getString("correct");

                questionItems.add(new QuestionItem(questionString,answer1String,answer2String,answer3String,answer4String,correctString));
            }

        }catch (JSONException e){
            e.printStackTrace();
        }


    }

    private String loadJasonFromFile ( String file){
        String json = "";

        try {
            //
            InputStream IS = getAssets().open(file);
            //gets how many bits are available before I/O operations are blocked(I still don't understand this 100%)
            int size = IS.available();
            //creates a byte array with just the right amount of bytes so that I/O is not blocked.
            byte[] buffer = new byte[size];

            IS.read(buffer);
            IS.close();

            json = new String(buffer,"UTF-8");

        }catch (IOException e){
            e.printStackTrace();
        }


        return json;
    }

    private void showQuestions(int number){
        questionTV.setText(questionItems.get(number).getQuestion());
        answerBT1.setText(questionItems.get(number).getAnswer1());
        answerBT2.setText(questionItems.get(number).getAnswer2());
        answerBT3.setText(questionItems.get(number).getAnswer3());
        answerBT4.setText(questionItems.get(number).getAnswer4());



    }



}
