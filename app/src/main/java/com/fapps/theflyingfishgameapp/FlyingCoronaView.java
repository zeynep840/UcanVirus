package com.fapps.theflyingfishgameapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingCoronaView extends View {
    private Bitmap corona[]=new Bitmap[2];
    private int coronaX=10;
    private int coronaY=550;
    private int coronaSpeed;
    private int canvasWidth,canvasHeight;
    private int score,lifeCounterOfCorona=3;
    private boolean touch=false;

    private Bitmap backgroundImage;
    private Paint scorePaint=new Paint();
    private Bitmap life[]=new Bitmap[2];
    private int yellowX,yellowY,yellowSpeed=19;
    private Paint yellowPaint=new Paint();
    private int greenX,greenY,greenSpeed=22;
    private Paint greenPaint=new Paint();
    private int redX,redY,redSpeed=25;
    private Paint redPaint=new Paint();



    public FlyingCoronaView(Context context) {
        super(context);
        corona[0]=BitmapFactory.decodeResource(getResources(),R.drawable.arkaplan3);
        corona[1]=BitmapFactory.decodeResource(getResources(),R.drawable.vir);
        backgroundImage=BitmapFactory.decodeResource(getResources(),R.drawable.doga);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.BLUE);
        redPaint.setAntiAlias(false);


        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(50);
        scorePaint.setTypeface(Typeface.DEFAULT);
        scorePaint.setAntiAlias(true);
        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.kalp);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.bos);

        //coronaY=550;
        score=0;
        //lifeCounterOfCorona=3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth=canvas.getWidth();
        canvasHeight=canvas.getHeight();
        canvas.drawBitmap(backgroundImage,0,0,null);

        int minCoronaY=corona[0].getHeight();
        int maxCoronaY=canvasHeight-corona[0].getHeight()*3;
        coronaY=coronaY+coronaSpeed;
        if(coronaY<minCoronaY){
            coronaY=minCoronaY;
        }
        if (coronaY>maxCoronaY){
            coronaY=maxCoronaY;
        }
        coronaSpeed=coronaSpeed+2;
        if(touch){
            canvas.drawBitmap(corona[1],coronaX,coronaY,null);
            touch=false;

        }
        else {
            canvas.drawBitmap(corona[0],coronaX,coronaY,null);
        }

        yellowX=yellowX-yellowSpeed;
        if (hitBallChecker(yellowX,yellowY)){
            score=score+10;
            yellowX=-100;
        }
        if(yellowX<0){
            yellowX=canvasWidth+21;
            yellowY=(int)Math.floor(Math.random()*(maxCoronaY-minCoronaY))+minCoronaY;
        }
        canvas.drawCircle(yellowX,yellowY,25,yellowPaint);



        greenX=greenX-greenSpeed;
        if (hitBallChecker(greenX,greenY)){
            score=score+20;
            greenX=-100;
        }
        if(greenX<0){
            greenX=canvasWidth+21;
            greenY=(int)Math.floor(Math.random()*(maxCoronaY-minCoronaY))+minCoronaY;
        }
        canvas.drawCircle(greenX,greenY,25,greenPaint);



        redX=redX-redSpeed;
        if (hitBallChecker(redX,redY)){

            redX=-100;
            lifeCounterOfCorona--;
            if(lifeCounterOfCorona==0){
               Toast.makeText(getContext(),"Dikkatli oynamalısın",Toast.LENGTH_SHORT).show();
               Intent gameOverIntent=new Intent(getContext(),GameOverActivity.class);

               ((Intent) gameOverIntent).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               gameOverIntent.putExtra("score",score);
               getContext().startActivity(gameOverIntent);

            }
        }
        if(redX<0){
            redX=canvasWidth+21;
            redY=(int)Math.floor(Math.random()*(maxCoronaY-minCoronaY))+minCoronaY;
        }
        canvas.drawCircle(redX,redY,30,redPaint);
        canvas.drawText("skor:" +score,20,60,scorePaint);


        for(int i=0;i<3;i++){
            //kalp koordinatlarını belirler
            int x=(int)(350+life[0].getWidth()*1.5*i);
            int y=30;
            if(i<lifeCounterOfCorona){
                canvas.drawBitmap(life[0],x,y,null);

            }
            else{
                canvas.drawBitmap(life[1],x,y,null);
            }
        }


        //bu kodu tekrar kontrol et
        //canvas.drawText("score" +score,20,60,scorePaint);
       // canvas.drawBitmap(life[0],380,10,null);
        //canvas.drawBitmap(life[0],480,10,null);
       // canvas.drawBitmap(life[0],580,10,null);

    }
    public boolean hitBallChecker(int x,int y){
        if(coronaX<x && x<(coronaX+corona[0].getWidth()) && coronaY < y && y < (coronaY+corona[0].getHeight())){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            touch=true;
            coronaSpeed=-28;
        }
        return true;
    }
}
