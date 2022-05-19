package com.inthuon.assign08_6206021611087;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class GamePlay extends View implements View.OnTouchListener{

    private Paint mPaint;
    private int score , time , imageWidth , imageHeight;
    private int[] SPEED = new int[5];
    private final int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int boom;

    private float[] X = new float[5];
    private float[] Y = new float[5];

    private boolean finished = false;
    private boolean[] SHOOT = new boolean[5];

    private Random random = new Random();
    private CountDownTimer timer1, timer2;
    private SoundPool soundPool;

    private Bitmap heart = BitmapFactory.decodeResource(getResources(),R.drawable.heart);
    private Bitmap heartbreak = BitmapFactory.decodeResource(getResources(),R.drawable.heartbreak);

    public GamePlay(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);
        mPaint = new Paint();
        score = 0;
        time = 0;
        setOnTouchListener(this);

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        boom = soundPool.load(context , R.raw.boom, 1);

        imageWidth = heart.getWidth();
        imageWidth = heart.getHeight();


        for(int i=0 ; i < 5 ; i++){
            X[i] = random.nextInt(width - imageWidth);
            SPEED[i] = random.nextInt(10) + 10;
        }
        timer1 = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long ms) {
                time++;
                invalidate();
            }

            @Override
            public void onFinish() {
                finished = true;
                invalidate();
            }
        };

        timer2 = new CountDownTimer(30000,50) {
            @Override
            public void onTick(long millisUntilFinished) {
                for (int i = 0; i < 5 ; i++){
                    Y[i] += SPEED[i];
                    if(Y[i] > height + imageHeight){
                        Y[i] = 0 - imageHeight;
                        X[i] = random.nextInt(width - imageWidth);
                    }
                }
                invalidate();
            }

            @Override
            public void onFinish() {

            }
        };

        timer1.start();
        timer2.start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if(finished){
            finished = false;
            timer1.start();
            timer2.start();
            time = 0;
            score = 0;
            invalidate();
        }else{
            float x = event.getX();
            float y = event.getY();

            for(int i =0 ;i < 5 ; i++){
                if( x > X[i] && x < X[i] + imageWidth){
                    if(y > Y[i] && x < Y[i] + imageHeight){
                        soundPool.play(boom , 1 ,1,1,0,1);
                        score++;
                        SHOOT[i] = true;

                        invalidate();
                    }
                }
            }
        }

        return false;
    }
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if(finished){
            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(60);
            mPaint.setTextAlign(Paint.Align.CENTER);

            canvas.drawText("Time Out!", width /2 ,height/2 - 100, mPaint );
            canvas.drawText("Press for play again or Back to Exit!", width /2 ,height/2 + 100, mPaint );

        }else{
            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(60);
            mPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("Score : " + score, 20,60, mPaint);
            mPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("Time : " + time, width-20,60, mPaint);

            for(int i=0 ; i < 5 ; i++){
                if(SHOOT[i]){
                    canvas.drawBitmap(heartbreak, X[i],Y[i],mPaint);
                    Y[i] = 0 - imageHeight;
                    X[i] = random.nextInt(width - imageWidth);
                    SHOOT[i] = false;
                }
                canvas.drawBitmap(heart, X[i] , Y[i] , null);
            }
        }

    }

}
