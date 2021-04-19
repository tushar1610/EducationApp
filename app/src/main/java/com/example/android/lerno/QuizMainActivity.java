package com.example.android.lerno;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizMainActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private ImageButton mTrue;
    private ImageButton mFalse;
    private ImageButton mCheat, mScore;
    private ImageButton mPrev;
    private ImageButton mNext;
    private TextView questionText;
    private Questions[] questions = new Questions[] {

            new Questions(R.string.question_australia,true, true),
            new Questions(R.string.question_oceans, true, true),
            new Questions(R.string.question_mideast, false, true),
            new Questions(R.string.question_africa, false, true),
            new Questions(R.string.question_americas, true, true),
            new Questions(R.string.question_asia, true, true),


    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(savedInstanceState!=null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        questionText = findViewById(R.id.question_shown);
        mTrue = findViewById(R.id.true_btn);
        mFalse = findViewById(R.id.false_btn);
        mPrev = findViewById(R.id.prev_btn);
        mNext = findViewById(R.id.next_btn);
        mCheat = findViewById(R.id.cheat_button);
        mScore = findViewById(R.id.score_button);
        mPrev.setVisibility(View.INVISIBLE);

        final int question = questions[mCurrentIndex].getTextResId();
        questionText.setText(question);

        mTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questions[mCurrentIndex].setButtonEnabled(false);
                mTrue.setEnabled(questions[mCurrentIndex].getButtonEnabled());
                mFalse.setEnabled(questions[mCurrentIndex].getButtonEnabled());
                checkAnswer(true);
            }
        });

        mFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questions[mCurrentIndex].setButtonEnabled(false);
                mTrue.setEnabled(questions[mCurrentIndex].getButtonEnabled());
                mFalse.setEnabled(questions[mCurrentIndex].getButtonEnabled());
                checkAnswer(false);
            }
        });

        mCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizMainActivity.this, CheatActivity.class);
                intent.putExtra("answer", questions[mCurrentIndex].getAnswerTrue());
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1) % questions.length;
                if (mCurrentIndex != 5){
                    mNext.setVisibility(View.VISIBLE);
                }
                if(mCurrentIndex == 0){
                    mPrev.setVisibility(View.INVISIBLE);
                }
                updateQuestion();
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % questions.length;
                if(mCurrentIndex != 0){
                    mPrev.setVisibility(View.VISIBLE);
                }
                if(mCurrentIndex == 5){
                    mNext.setVisibility(View.INVISIBLE);
                }
                mIsCheater = false;
                updateQuestion();
            }
        });

        mScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizMainActivity.this, "Your score is " + score + "!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHEAT) {
            if (data!=null) {
                if (data.hasExtra("cheated")) {
                    mIsCheater = data.getExtras().getBoolean("cheated");
                }
            }
        }
    }

    private void checkAnswer(boolean b) {
        boolean answer = questions[mCurrentIndex].getAnswerTrue();
        int messageResId;
        if(mIsCheater){
            messageResId = R.string.judgement_toast;
            questions[mCurrentIndex].setButtonEnabled(false);
            score += 0;
        } else {
            if(b == answer){
                messageResId = R.string.correct;
                score += 5;
            } else {
                messageResId = R.string.incorrect;
                score += 0;
            }
        }
        Toast.makeText(QuizMainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        int question = questions[mCurrentIndex].getTextResId();
        questionText.setText(question);
        mTrue.setEnabled(questions[mCurrentIndex].getButtonEnabled());
        mFalse.setEnabled(questions[mCurrentIndex].getButtonEnabled());
    }
}