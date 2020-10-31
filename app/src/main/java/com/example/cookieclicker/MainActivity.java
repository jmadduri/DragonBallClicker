package com.example.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    ImageView clickBall_main;
    TextView ballCounter;
    TextView passiveIncome;
    static AtomicLong count;
    static AtomicLong pidisp;
    ImageView Goku;
    TextView gokuCounter;
    final Context context = this;
    static boolean gokuClicked = false;
    int gokuCount = 0;
    float var;
    int x;
    boolean aBoolean = true;
    boolean aBoolean2 = false;
    boolean aBoolean3 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickBall_main = findViewById(R.id.imageView_mainball);
        ballCounter = findViewById(R.id.textView_countballs);
        passiveIncome = findViewById(R.id.textView_passiveincome);
        constraintLayout = findViewById(R.id.id_layout);
        Goku = findViewById(R.id.imageView2);
        count = new AtomicLong(0);
        pidisp = new AtomicLong(0);

        final ScaleAnimation animation = new ScaleAnimation(1.5f,1.0f,1.5f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(400);//If animation is going too fast it is because this is not set

        final ConstraintLayout.LayoutParams params4 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        gokuCounter = new TextView(context);
        gokuCounter.setId(View.generateViewId());
        gokuCounter.setLayoutParams(params4);
        constraintLayout.addView(gokuCounter);

        clickBall_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyThread().start();
                TextView plusOne;
                plusOne = new TextView(context);
                plusOne.setId(View.generateViewId());
                plusOne.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
                plusOne.setTextColor(new Color().parseColor("#0000FF"));

                try
                {
                    AnimationDrawable background = (AnimationDrawable)constraintLayout.getBackground();
                    background.start();

                } catch (Exception e) {
                    Log.d("HEY",e.getMessage());
                }

                if(count.get()>=100 && aBoolean)
                {
                   Goku.setImageResource(R.drawable.goku);
                   Animation fadeIn = new AlphaAnimation(0, 1);
                   fadeIn.setInterpolator(new AccelerateInterpolator());
                   fadeIn.setStartOffset(10);
                   fadeIn.setDuration(3000);
                   AnimationSet anim = new AnimationSet(false);
                   anim.addAnimation(fadeIn);
                   Goku.startAnimation(anim);
                   aBoolean = false;
                   aBoolean2 = true;
                }

                Animation animationUp = new TranslateAnimation(0, 0, 200, 0);
                animationUp.setDuration(250);
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                plusOne.setLayoutParams(params);

                constraintLayout.addView(plusOne);

                ConstraintSet constraintSet2 = new ConstraintSet();
                constraintSet2.clone(constraintLayout);

                constraintSet2.connect(plusOne.getId(),ConstraintSet.TOP, constraintLayout.getId(),ConstraintSet.TOP);
                constraintSet2.connect(plusOne.getId(),ConstraintSet.BOTTOM, constraintLayout.getId(),ConstraintSet.BOTTOM);
                constraintSet2.connect(plusOne.getId(),ConstraintSet.LEFT, constraintLayout.getId(),ConstraintSet.LEFT);
                constraintSet2.connect(plusOne.getId(),ConstraintSet.RIGHT, constraintLayout.getId(),ConstraintSet.RIGHT);

                Double rand = (double)(Math.random()*0.4)+0.3;
                Double rand2 = (double)(Math.random()*0.3)+0.3;
                BigDecimal random = new BigDecimal(rand);
                BigDecimal random2 = new BigDecimal(rand2);
                float myFloat = random.floatValue();
                float myFloat2 = random2.floatValue();

                constraintSet2.setHorizontalBias(plusOne.getId(),myFloat);
                constraintSet2.setVerticalBias(plusOne.getId(),myFloat2);

                constraintSet2.applyTo(constraintLayout);
                plusOne.setText("+1");
                plusOne.startAnimation(animationUp);
                plusOne.setVisibility(View.INVISIBLE);
                v.startAnimation(animation);
            }
        });

        Goku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aBoolean2) {
                    if (count.get() < 100) {
                        Toast toast = Toast.makeText(context, "Sorry, you do not have enough Dragon Balls to buy Goku.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                    } else {
                        gokuClicked = true;
                        gokuCount++;
                        for (int i = 1; i <= 100; i++) {
                            count.getAndDecrement();
                        }
                        new passiveIncTextView().start();

                        if(aBoolean3) {
                            new passiveInc().start();
                            aBoolean3 = false;
                        }

                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone(constraintLayout);

                        constraintSet.connect(gokuCounter.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
                        constraintSet.connect(gokuCounter.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
                        constraintSet.connect(gokuCounter.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
                        constraintSet.connect(gokuCounter.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);

                        constraintSet.setHorizontalBias(gokuCounter.getId(), 0f);
                        constraintSet.setVerticalBias(gokuCounter.getId(), 0.85f);

                        constraintSet.applyTo(constraintLayout);
                        gokuCounter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                        gokuCounter.setTextColor(new Color().parseColor("#000000"));
                        gokuCounter.setText("X" + gokuCount);

                        ImageView Goku2;
                        Goku2 = new ImageView(context);
                        Goku2.setId(View.generateViewId());
                        Goku2.setImageResource(R.drawable.goku);
                        ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(186, 300);
                        Animation fadeIn = new AlphaAnimation(0, 1);
                        fadeIn.setInterpolator(new AccelerateInterpolator());
                        fadeIn.setStartOffset(1);
                        fadeIn.setDuration(1500);
                        AnimationSet anim = new AnimationSet(false);
                        anim.addAnimation(fadeIn);
                        Goku2.setLayoutParams(params3);
                        constraintLayout.addView(Goku2);

                        ConstraintSet constraintSet2 = new ConstraintSet();
                        constraintSet2.clone(constraintLayout);

                        constraintSet2.connect(Goku2.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
                        constraintSet2.connect(Goku2.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
                        constraintSet2.connect(Goku2.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
                        constraintSet2.connect(Goku2.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);

                        x = gokuCount;
                        var = (x -= 1) * (float) 0.1;
                        constraintSet2.setHorizontalBias(Goku2.getId(), var);
                        constraintSet2.setVerticalBias(Goku2.getId(), 1f);

                        Goku2.startAnimation(anim);
                        constraintSet2.applyTo(constraintLayout);

                        v.startAnimation(animation);
                    }
                }
            }
        });

        Thread text = new Thread(){
            @Override
            public void run() {
                while(!isInterrupted()){
                    try{
                        Thread.sleep(1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ballCounter.setText(count.get()+" Dragon Balls ");
                                passiveIncome.setText(pidisp.get()+" Passive Dragon Balls");
                            }
                        });
                    }catch (Exception e){
                        Log.d("TAG", e.getMessage());
                    }
                }
            }
        };
        text.start();
    }

    public static class MyThread extends Thread
    {
        public void run()
        {
            try
            {
                Thread.sleep(2);
            }catch(Exception e){
                Log.d("TAG","Error :(");
            }
            count.getAndAdd(1);
        }
    }

    public static class passiveIncTextView extends Thread
    {
        public void run()
        {
            try
            {
                Thread.sleep(3);
            }catch(Exception e){
                Log.d("TAG2","Error :(");
            }
            pidisp.getAndAdd(1);
        }
    }

    public static class passiveInc extends Thread//Add income without clicking
    {
        public void run()
        {
            Looper.prepare();
            Log.d("TAGWHY?",gokuClicked+"");
            while(gokuClicked)
            {
                try
                {
                    Thread.sleep(1200);
                    count.getAndAdd(pidisp.get());
                }catch(Exception e){
                    Log.d("TAG2","Error :(");
                }
            }
        }
    }
}
