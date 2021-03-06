package com.example.flightgearapp.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.NonNull;

public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    //fields
    float centerX;
    float centerY;
    float baseRadius;
    float hatRadius;
    private JoystickListener joystickCallback;

    //methods
    // set up the location of the joystick
    private void setupDimensions(){
        centerX = (float)getWidth() / 2;
        centerY = (float)getHeight() / 2;
        baseRadius = (float)Math.min(getWidth(), getHeight()) / 3;
        hatRadius =(float) Math.min(getWidth(), getHeight()) / 5;
        hatRadius = (float) (hatRadius * 0.85);
    }
    //constructors
    public JoystickView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoystickListener){
            joystickCallback =(JoystickListener)context;
        }
    }

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoystickListener){
            joystickCallback =(JoystickListener)context;
        }
    }

    public JoystickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoystickListener){
            joystickCallback =(JoystickListener)context;
        }
    }

    // draw joystick
    private void drawJoystick(float newX,float newY){
        if (getHolder().getSurface().isValid()) {
            // create canvas
            Canvas canvas = this.getHolder().lockCanvas();
            Paint colors = new Paint();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            // draw the background of the joystick
            colors.setARGB(255,194, 236, 252);
            canvas.drawRect(getLeft() - 100, getTop() - 450,getRight(),getBottom(),colors);
            // draw outter circle
            colors.setARGB(255, 52, 61, 235);
            canvas.drawCircle(centerX, centerY, baseRadius, colors);
            //draw inner circle
            colors.setARGB(255, 52, 211, 235);
            canvas.drawCircle(newX, newY, hatRadius, colors);
            // draw the canvas
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    // surfaceHolder listener function - when view created calculate the joystick location and draw it
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        setupDimensions();
        drawJoystick(centerX,centerY);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    // when the user touches the screen moves the joystick to the matching location and check inner circle boundaries
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.equals(this)){
            // while the user holds his finger against the screen
            if(event.getAction() != event.ACTION_UP){
                //Pythagoras' theorem for boundaries check
                float displacement = (float) Math.sqrt(Math.pow(event.getX() - centerX, 2)
                        + Math.pow(event.getY() - centerY, 2));
                // in bounds
                if (displacement < baseRadius) {
                    drawJoystick(event.getX(), event.getY());
                    joystickCallback.onJoystickMoved
                            ((event.getX() - centerX) / baseRadius, (event.getY() - centerY) / baseRadius, getId());
                }
                else {
                    // out of bounds
                    float ratio  = baseRadius / displacement;
                    float constrainedX = centerX + (event.getX() - centerX)*ratio;
                    float constrainedY = centerY + (event.getY() - centerY)*ratio;
                    drawJoystick(constrainedX,constrainedY);
                    joystickCallback.
                            onJoystickMoved((constrainedX - centerX) / baseRadius, (constrainedY - centerY) / baseRadius, getId());
                }
            }
            // end of motion case
            else {
                drawJoystick(centerX, centerY);
                joystickCallback.onJoystickMoved(0, 0, getId());
            }
        }
        return true;
    }

    public interface JoystickListener
    {
        // abstract method for joystick movement
        void onJoystickMoved(float xPercent, float yPercent, int source);
    }
}
