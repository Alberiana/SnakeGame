package com.example.snakegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    //list of snake points/snake length
    private final List<SnakePoints> snakePoints=new ArrayList<>();


    private SurfaceView surfaceView;
    private TextView scoreTV;

    //surface holder to draw snake on surface's canvas
    private SurfaceHolder surfaceHolder;

    //snake moving position. Values must be right, left, top, bottom.
    //By default snake move to right
    private String movingPosition ="right";

    //score
    private int score=0;

    //snake/size/point size
    private static final int positionSize=28;

    //default snake tale
    private static final int defaultTalePoints=3;

    //snake color
    private static final int snakeColor= Color.YELLOW;

    //moving speed(1-1000)
    private static final int snakeMovingSpeed=500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting surfaceview and score textView from xml file
        surfaceView=findViewById(R.id.surfaceView);
        scoreTV=findViewById(R.id.scoreTV);

        //getting ImageButtons from xml file
        final AppCompatImageButton topBtn=findViewById(R.id.topBtn);
        final AppCompatImageButton leftBtn=findViewById(R.id.leftBtn);
        final AppCompatImageButton rightBtn=findViewById(R.id.rightBtn);
        final AppCompatImageButton bottomBtn=findViewById(R.id.bottomBtn);

        //adding callbackk to surface
        surfaceView.getHolder().addCallback(this);


        topBtn.setOnClickListener(new View.OnClickListener(){

            //check if previous moving position is not bottom. Snake cant move from bottom to top directly.
            @Override
            public void onClick(View view) {
                if(!movingPosition.equals("bottom")){
                    movingPosition="top";
                }
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!movingPosition.equals("right")){
                    movingPosition="left";
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!movingPosition.equals("left")){
                    movingPosition="right";
                }
            }
        });

        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!movingPosition.equals("top")){
                    movingPosition="bottom";
                }
            }
        });
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        //when surface is created then get surfaceHolder from it and assign to surfaceHolder
        this.surfaceHolder=surfaceHolder;

        //init data for snale/surfaceview
        init();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
    private void init(){

        //clear snake points/ snake length
        snakePoints.clear();

        //set default score as 0
        scoreTV.setText("0");

        //make score 0
        score=0;

        //setting default moving position
        movingPosition="right";

        //default snake position
        int startPositionX=(positionSize)*defaultTalePoints;
    }
}