package com.example.snakegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    //list of snake points/snake length
    private final List<SnakePoints> snakePointslist=new ArrayList<>();


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
    private static final int pointSize=28;

    //default snake tale
    private static final int defaultTalePoints=3;

    //snake color
    private static final int snakeColor= Color.YELLOW;

    //moving speed(1-1000)
    private static final int snakeMovingSpeed=800;

    //random point position cordinates on the surfaceView
    private int positionX=0,positionY=0;

    //timer to move snake/change snake position after a specific time(snakeMovingSpeed)
    private Timer timer;


    //canvas to draw snake and show on surface view
    private Canvas canvas=null;


    //point color/single point color of a snake
    private Paint pointColor=null;


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
        snakePointslist.clear();

        //set default score as 0
        scoreTV.setText("0");

        //make score 0
        score=0;

        //setting default moving position
        movingPosition="right";

        //default snake position on the screen
        int startPositionX=(pointSize)*defaultTalePoints;


        //making snakes default length/points
        for(int i=0; i<defaultTalePoints; i++){
            //adding points to snakes table
            SnakePoints snakePoints=new SnakePoints(startPositionX, pointSize);
            snakePointslist.add(snakePoints);

            //increasing value for next point as snakes table
            startPositionX=startPositionX-(pointSize*2);
        }

        //add random point on the screen to be eaten bt the snake
        addPoint();

        //start moving snake/ start game
        moveSnake();
    }

    private void addPoint(){

        //getting surfaceView width and height to add point on the surface to be eaten by the snake
        int surfaceWidth = surfaceView.getWidth()-(pointSize*2);
        int surfaceHeight=surfaceView.getHeight()-(pointSize*2);

        int randomXPosition=new Random().nextInt(surfaceWidth/pointSize);
        int randomYPosition=new Random().nextInt(surfaceHeight/pointSize);

        //check if randomXPosition is even or odd value. We need only even number

        //check if position in even or odd value. We need only even number.
        if((randomXPosition%2)!=0){
            randomXPosition=randomXPosition+1;
        }


        if((randomYPosition%2)!=0){
            randomYPosition=randomYPosition+1;
        }

        positionX=(pointSize * randomXPosition)+pointSize;
        positionY=(pointSize * randomYPosition)+pointSize;
    }


    private void moveSnake(){

        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //getting head position
                int headPositionX=snakePointslist.get(0).getPositionX();
                int headPositionY=snakePointslist.get(0).getPositionY();

                //check if snake after a point
                if(headPositionX==positionX&&positionY==headPositionY){

                    //grow snake after eaten point
                    growSnake();

                    //add another random point on the screen
                    addPoint();

                }


                switch (movingPosition){
                    case "right":

                        //move snake's head to right.
                        //other points follow snakes head point to move the snake
                        snakePointslist.get(0).setPositionX(headPositionX+(pointSize*2));
                        snakePointslist.get(0).setPositionY(headPositionY);
                        break;

                    case "left":
                        //move snake's head to left.
                        //other points follow snakes head point to move the snake
                        snakePointslist.get(0).setPositionX(headPositionX-(pointSize*2));
                        snakePointslist.get(0).setPositionY(headPositionY);
                        break;

                    case "top":
                        //move snake's head to top.
                        //other points follow snakes head point to move the snake
                        snakePointslist.get(0).setPositionX(headPositionX);
                        snakePointslist.get(0).setPositionY(headPositionY-(pointSize*2));
                        break;

                    case "bottom":
                        //move snake's head to bottom.
                        //other points follow snakes head point to move the snake
                        snakePointslist.get(0).setPositionX(headPositionX);
                        snakePointslist.get(0).setPositionY(headPositionY+(pointSize*2));
                        break;


                }
                //check if game over. Weather anake touch edges or snake itself
                if(checkGameOver(headPositionX,headPositionY)){
                    //stop timer/ stop moving snake
                    timer.purge();
                    timer.cancel();


                    //show game over dialog
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Your score= "+score);
                    builder.setTitle("Game Over");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Start Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //restart game/ re-init data
                            init();
                        }
                    });


                    //timer runs in the background so we need to show dialog on main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            builder.show();
                        }
                    });
                }
                else{
                    //lock canvas on surfaceHolder to draw on it
                    canvas =surfaceHolder.lockCanvas();

                    //clear canvas with white color
                    canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);

                    //change snakes head position, other snake points will follow snakes head
                    canvas.drawCircle(snakePointslist.get(0).getPositionX(), snakePointslist.get(0).getPositionY(),pointSize,createPointColor());

                    //draw random point circle on the surface to be eaten by the snake
                    canvas.drawCircle(positionX, positionY, pointSize, createPointColor());

                    //other points following snakes head, position 0 is head of snake
                    for(int i=1; i<snakePointslist.size(); i++){
                        int getTempPositionX = snakePointslist.get(i).getPositionX();
                        int getTempPositionY = snakePointslist.get(i).getPositionY();

                        //move points accross the head
                        snakePointslist.get(i).setPositionX(headPositionX);
                        snakePointslist.get(i).setPositionY(headPositionY);
                        canvas.drawCircle(snakePointslist.get(i).getPositionX(), snakePointslist.get(i).getPositionY(), pointSize,createPointColor());

                        //change head position
                        headPositionX=getTempPositionX;
                        headPositionY=getTempPositionY;
                    }

                    //unlock canvas to draw on surface
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }, 1000-snakeMovingSpeed, 1000-snakeMovingSpeed);
    }


    private void growSnake()   {

        //create nes=w snake points
        SnakePoints snakePoints=new SnakePoints(0,0);

        //add point to the snake tale
        snakePointslist.add(snakePoints);


        //increase score
        score++;

        //setting score to TextView
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTV.setText(String.valueOf(score));
            }
        });
    };

    private boolean checkGameOver(int headPositionX, int headPositionY){
        boolean gameOver=false;

        //check if snakes head touches edges
        if(snakePointslist.get(0).getPositionX()<0 ||
            snakePointslist.get(0).getPositionY()<0 ||
                snakePointslist.get(0).getPositionX()>=surfaceView.getWidth() ||
                snakePointslist.get(0).getPositionY()>= surfaceView.getHeight()
        ){
            gameOver=true;
        }else{
            //check if snakes head touches snake itself
            for(int i=1; i<snakePointslist.size();i++){
                if(headPositionX==snakePointslist.get(i).getPositionX()  &&
                        headPositionY==snakePointslist.get(i).getPositionY()){
                    gameOver=true;
                    break;
                }
            }
        }
        return gameOver;
    }

    private Paint createPointColor() {
        //check if color not defined before
        if (pointColor == null) {
            pointColor = new Paint();
            pointColor.setColor(snakeColor);
            pointColor.setStyle(Paint.Style.FILL);
            pointColor.setAntiAlias(true);//smoothness

        }
        return pointColor;
    }
}