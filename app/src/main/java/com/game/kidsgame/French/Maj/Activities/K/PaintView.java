package com.game.kidsgame.French.Maj.Activities.K;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.game.kidsgame.French.Maj.Activities.FingerPath;

import java.util.ArrayList;

import static com.game.kidsgame.French.Maj.Activities.K.K.maxX;
import static com.game.kidsgame.French.Maj.Activities.K.K.maxY;


public class PaintView extends View {

    public static int BRUSH_SIZE = 60;
    public static final int DEFAULT_COLOR = Color.RED;
    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private int current = 0;
    private Path mPath;
    private Paint mPaint;
    private ArrayList<FingerPath> paths = new ArrayList<>();
    private int currentColor;
    private int strokeWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);

    }

    public void init(DisplayMetrics metrics) {
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;


        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = DEFAULT_COLOR;
        strokeWidth = BRUSH_SIZE;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mCanvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);

        for (FingerPath fp : paths) {
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);
            mPaint.setMaskFilter(null);

            mCanvas.drawPath(fp.path, mPaint);

        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y) {

        System.out.println("start x is: "+ x);
        System.out.println("start y is: "+ y);
        mPath = new Path();
        FingerPath fp = new FingerPath(currentColor, strokeWidth, mPath);
        if(paths.size()==0 && x<((maxX*0.28)+50) && x>((maxX*0.28)-50) && y<((maxY*0.05)+50) && y>((maxY*0.05)-50))
        {

            paths.add(fp);
            current=1;
        }
        else if(paths.size()==1 && x<((maxX*0.77)+50) && x>((maxX*0.77)-50) && y<((maxY*0.05)+50) && y>((maxY*0.05)-50))
        {
            paths.add(fp);
            current=2;

        }
        else if(paths.size()==2 && x<((maxX*0.33)+50) && x>((maxX*0.33)-50) && y<((maxY*0.5)+50) && y>((maxY*0.5)-50))
        {
            paths.add(fp);
            current=3;

        }
        else{current=0;}
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;

        System.out.println("start x is: "+ mX/maxX);
        System.out.println("start y is: "+ mY/maxY);
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if(paths.size()==1 && current==1 && y>0.50*maxY-50 && y<0.50*maxY+50 && (x<0.3*maxX-50 || x>0.3*maxX+50)){
            paths.remove(paths.size()-1);
            mPath.reset();
            invalidate();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            K.onFailed();
            System.out.println("exit1 x is: "+ x/maxX);
            System.out.println("exit1 y is: "+ y/maxY);
        }
        if(paths.size()==2 && current==2 && y>0.32*maxY-30 && y<0.32*maxY+30 && (x<0.52*maxX-50 || x>0.52*maxX+50 ) ){
            paths.remove(paths.size()-1);
            mPath.reset();
            invalidate();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            K.onFailed();
            System.out.println("exit2 x is: "+ x/maxX);
            System.out.println("exit2 y is: "+ y/maxY);
        }
        if(paths.size()==3 && current==3 && y>0.7*maxY-30 && y<0.7*maxY+30 && (x<0.52*maxX-50 || x>0.52*maxX+50 ) ){
            paths.remove(paths.size()-1);
            mPath.reset();
            invalidate();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            K.onFailed();
            System.out.println("exit1 x is: "+ x/maxX);
            System.out.println("exit1 y is: "+ y/maxY);
        }
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp() {
        mPath.lineTo(mX, mY);
        System.out.println("end x is: "+ mX);
        System.out.println("end y is: "+ mY);
        if(paths.size()==1 && current==1 && !(mX<(((maxX)*0.25)+50) && mX>((maxX*0.25)-50) && mY<(((maxY)*0.88)+50) && mY>((maxY*0.8)-50))){
            paths.remove(paths.size()-1);
            mPath.reset();
            invalidate();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            K.onFailed();
        }
        if(paths.size()==2 && current==2 &&  !(mX<(((maxX)*0.3)+50) && mX>((maxX*0.3)-50) && mY<(((maxY)*0.5)+50) && mY>((maxY*0.5)-50))) {
            paths.remove(paths.size()-1);
            mPath.reset();
            invalidate();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            K.onFailed();
        }
        if(paths.size()==3 && current==3 &&  !(mX<(((maxX)*0.77)+50) && mX>((maxX*0.77)-50) && mY<(((maxY)*0.88)+50) && mY>((maxY*0.88)-50))) {
            paths.remove(paths.size()-1);
            mPath.reset();
            invalidate();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            K.onFailed();
        }
        if(paths.size()==3 && current==3 &&  (mX<(((maxX)*0.77)+50) && mX>((maxX*0.77)-50) && mY<(((maxY)*0.88)+50) && mY>((maxY*0.88)-50))) {
            K.onSuccess();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                touchUp();
                invalidate();
                break;
        }

        return true;
    }
}