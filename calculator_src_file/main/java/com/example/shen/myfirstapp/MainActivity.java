package com.example.shen.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private String res="", resCode=""; // res is shown on screen, rescode is used to calculate
    private int cursorPosition=0;
    private int[] numericButtons = {R.id.number0,R.id.number1,R.id.number2,R.id.number3,R.id.number4,
            R.id.number5,R.id.number6,R.id.number7,R.id.number8,R.id.number9};
    private int[] operatorButtons = {R.id.signP,R.id.signM,R.id.signX,R.id.signD,R.id.point,
            R.id.parenthesL,R.id.parenthesR,R.id.Sin,R.id.Cos,R.id.Tan};
    private EditText insert;
    private TextView output;

    private String operator ="+-×÷.()SCT^";
    private String operatorCode ="qwer.()tyui";

    private Stack<String> Ans = new  Stack<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insert = (EditText) findViewById(R.id.insert);
        output = (TextView) findViewById(R.id.output);
        insert.setHorizontallyScrolling(true);
        insert.setSingleLine();

        //disable keyboard for EditText insert
        this.insert.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int inType = insert.getInputType();
                // backup the input type
                insert.setInputType(InputType.TYPE_NULL);
                // disable soft input
                insert.onTouchEvent(event);
                // call native handler
                insert.setInputType(inType);
                // restore input type
                insert.setSelection(cursorPosition);
                return true; // consume touch even
                } });


            setNumericOnClickListener();
        setOperatorOnClickListener();
    }

    private void setNumericOnClickListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if(cursorPosition==res.length()) {
                    String text = button.getText().toString();
                    res += button.getText();
                    resCode += button.getText();
                }
                else {
                    res = res.substring(0, cursorPosition) + button.getText() +
                            res.substring(cursorPosition, res.length());
                    resCode = resCode.substring(0, cursorPosition) + button.getText() +
                            resCode.substring(cursorPosition, resCode.length());
                }
                insert.setText(res);
                cursorPosition++;
                insert.setSelection(cursorPosition);
                output.setText(resCode);
            }
        };
        for(int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorOnClickListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                Character toAdd = operatorCode.charAt(operator.indexOf(button.getText().charAt(0)));
                String text = button.getText().toString();
                if(text.equals("Sin") || text.equals("Cos") || text.equals("Tan")) {
                    text += "(";
                }
                if(cursorPosition==res.length()) {
                    res += text;
                    resCode += toAdd;
                }
                else {
                    res = res.substring(0, cursorPosition) + text +
                            res.substring(cursorPosition, res.length());
                    resCode = resCode.substring(0, cursorPosition) + toAdd +
                            resCode.substring(cursorPosition, resCode.length());
                }
                insert.setText(res);
                cursorPosition++;
                if(text.equals("Sin") || text.equals("Cos") || text.equals("Tan")) {
                    cursorPosition+=3;
                }
                insert.setSelection(cursorPosition);
                output.setText(resCode);
            }
        };
        for(int id : operatorButtons) {
            findViewById(id).setOnClickListener(listener);
        }
        // button left arrow
        findViewById(R.id.buttonLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cursorPosition>0 && cursorPosition<=res.length())
                    cursorPosition--;
                insert.setSelection(cursorPosition);
                output.setText(Integer.toString(cursorPosition));
            }
        });
        findViewById(R.id.buttonRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cursorPosition>=0 && cursorPosition<res.length())
                    cursorPosition++;
                insert.setSelection(cursorPosition);
                output.setText(Integer.toString(cursorPosition));
            }
        });
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                res="";
                resCode="";
                insert.setText(res);
                cursorPosition=0;
                insert.setSelection(cursorPosition);
                output.setText(res);
            }
        });
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = 1;
                if(resCode.charAt(cursorPosition) == 't' ||
                        resCode.charAt(cursorPosition) == 'y'||
                        resCode.charAt(cursorPosition) == 'u')
                    n = 4;
                if(res.length()>0) {
                    if(cursorPosition==0)
                        return;
                    else if(cursorPosition==res.length()) {
                        res = res.substring(0, res.length() - n);
                        resCode = resCode.substring(0, resCode.length() - n);
                    }
                    else {
                        res = res.substring(0, cursorPosition - n) +
                                res.substring(cursorPosition, res.length());
                        resCode = resCode.substring(0, cursorPosition - n) +
                                resCode.substring(cursorPosition, resCode.length());
                    }
                    insert.setText(res);
                    cursorPosition-=n;
                    insert.setSelection(cursorPosition);
                    output.setText(Integer.toString(cursorPosition));
                }
            }
        });
        findViewById(R.id.signE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculation calculate = new Calculation(resCode);
                String ans = calculate.display();
                if(ans.charAt(0)!='W')
                    Ans.push(ans);
                output.setText(ans);
            }
        });

        findViewById(R.id.Ans).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans = "";
                if(Ans.empty())
                    ans = "0";
                else
                    ans = Ans.pop();

                if(cursorPosition==res.length()) {
                    res += "Ans";
                    resCode += ans;
                }
                else{
                    res = res.substring(0, cursorPosition) + "Ans" +
                            res.substring(cursorPosition, res.length());
                    resCode = resCode.substring(0, cursorPosition) + ans +
                            resCode.substring(cursorPosition, resCode.length());
                }
                insert.setText(res);
                cursorPosition+=3;
                insert.setSelection(cursorPosition);
                output.setText(resCode);

            }
        });
    }
}

