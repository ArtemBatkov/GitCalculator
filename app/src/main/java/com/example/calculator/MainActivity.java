package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.sax.EndElementListener;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //class R позволяет находить айди, файлы, объекты,  и куча куча всего в проекте
//        resultTxtView = findViewById(R.id.textView); // присваиваем переменным существующие кнопки из дизайна
//        val1 = findViewById(R.id.number_field_1);
//        val2 = findViewById(R.id.number_field_2);
//        btn = findViewById(R.id.addBtn);
//
//        btn.setOnClickListener(new View.OnClickListener() { // OnClickListener -- анонимный класс
//            @Override
//            public void onClick(View view) {
//                float num1 = Float.parseFloat(val1.getText().toString());
//                float num2 = Float.parseFloat(val2.getText().toString());
//                float res = num1 + num2;
//                resultTxtView.setText(String.valueOf(res));
//            }
//        });




    private TextView MainScreen;
    private Button Num0,Num1,Num2,Num3,Num4,Num5,Num6,Num7,Num8,Num9,Plus,Minus,Mult,Divide,PlusMinus,PowerOf,OpenBracket,CloseBracket,Dot,Equal;
    private ImageButton Back;
    private String MainScreenText = "0";
    private ArrayList <String> OperatorBuffer = new ArrayList<String>(){
        {
            add("+"); add("-"); add("*"); add("/"); add("±"); add("(");add(")");add("^");
        }
    };


    public  ArrayList getOperatorList()
    {
        return OperatorBuffer;
    }



    public void setMainScreenText(String symbol) {
        if((MainScreenText.length() == 1) && (MainScreenText.equals("0")))
        {
            MainScreenText ="";
            MainScreenText += symbol;
            MainScreen.setText(MainScreenText);
        }
        else{
            MainScreenText += symbol;
            MainScreen.setText(MainScreenText);
        }

    }

    public String getMainScreenText()
    {
        return  MainScreen.getText().toString();
    }

    public void ClearLastSymbol()
    {
        int lastIndex = MainScreenText.toString().length() - 1;
        MainScreenText = MainScreenText.toString().substring(0,lastIndex);
        if (MainScreenText.isEmpty()) setMainScreenText("0");
        UpdateText();
    }

    public  void UpdateText()
    {
        MainScreen.setText(MainScreenText);
    }

    public void ClearAll()
    {
        MainScreenText = "0";
        UpdateText();
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Работает в момент запуска активити
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        MainScreen = findViewById(R.id.MainScreen);
//        MainScreen.setSelected(true);
        MainScreen.setMovementMethod(new ScrollingMovementMethod());

        Num0 = findViewById(R.id.Num0);
        Num1 = findViewById(R.id.Num1);
        Num2 = findViewById(R.id.Num2);
        Num3 = findViewById(R.id.Num3);
        Num4 = findViewById(R.id.Num4);
        Num5 = findViewById(R.id.Num5);
        Num6 = findViewById(R.id.Num6);
        Num7 = findViewById(R.id.Num7);
        Num8 = findViewById(R.id.Num8);
        Num9 = findViewById(R.id.Num9);

        Back = findViewById(R.id.Back);
        Dot = findViewById(R.id.Dot);
        OpenBracket = findViewById(R.id.OpenBracket);
        CloseBracket = findViewById(R.id.CloseBracket);
        Equal = findViewById(R.id.Equal);

        Plus = findViewById(R.id.Plus);
        Minus = findViewById(R.id.Minus);
        Mult = findViewById(R.id.Multiply);
        Divide = findViewById(R.id.Divide);
        PlusMinus = findViewById(R.id.PlusMinus);
        PowerOf = findViewById(R.id.XPWRY);
        SyntaxCheck SyntaxHandle = new SyntaxCheck(MainActivity.this);

        Parse ParseHandle = new Parse(MainActivity.this);



        Num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte Num0_val = 0;
                setMainScreenText(String.valueOf(Num0_val));
            }
        });

        Num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte Num1_val = 1;
                setMainScreenText(String.valueOf(Num1_val));
            }
        });

        Num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte Num2_val = 2;
                setMainScreenText(String.valueOf(Num2_val));
            }
        });

        Num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte Num3_val = 3;
                setMainScreenText(String.valueOf(Num3_val));
            }
        });

        Num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte Num4_val = 4;
                setMainScreenText(String.valueOf(Num4_val));
            }
        });


        Num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte Num5_val = 5;
                setMainScreenText(String.valueOf(Num5_val));
            }
        });


        Num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte Num6_val = 6;
                setMainScreenText(String.valueOf(Num6_val));
            }
        });


        Num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte Num7_val = 7;
                setMainScreenText(String.valueOf(Num7_val));
            }
        });

        Num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte Num8_val = 8;
                setMainScreenText(String.valueOf(Num8_val));
            }
        });

        Num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte Num9_val = 9;
                setMainScreenText(String.valueOf(Num9_val));
            }
        });


        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "+";
                if (SyntaxHandle.IsLastSymbolOperator()) // last symbol IS operator?
                {
                    //not the same as last
                    if(!SyntaxHandle.IsSymbolTheSameAsPrevious(symbol)) SyntaxHandle.ChangeLastOperatorForNew(symbol); //change for current
                }
                else
                    setMainScreenText(symbol);

            }
        });

        Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "-"; //minus
                if (SyntaxHandle.IsLastSymbolOperator())
                {
                    if(!SyntaxHandle.IsSymbolTheSameAsPrevious(symbol)) SyntaxHandle.ChangeLastOperatorForNew(symbol);
                }
                else setMainScreenText(symbol);
            }
        });

        Mult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "*";
                if (SyntaxHandle.IsLastSymbolOperator())
                {
                    if(!SyntaxHandle.IsSymbolTheSameAsPrevious(symbol)) SyntaxHandle.ChangeLastOperatorForNew(symbol);
                }
                else setMainScreenText(symbol);

            }
        });

        Divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "/";
                if (SyntaxHandle.IsLastSymbolOperator())
                {
                    if(!SyntaxHandle.IsSymbolTheSameAsPrevious(symbol)) SyntaxHandle.ChangeLastOperatorForNew(symbol);
                }
                else setMainScreenText(symbol);

            }
        });

        PowerOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "^";
                int len = MainScreenText.length();
                if (SyntaxHandle.IsLastSymbolOperator())
                {
                    if(!SyntaxHandle.IsSymbolTheSameAsPrevious(symbol)) SyntaxHandle.ChangeLastOperatorForNew(symbol);
                }
                else setMainScreenText(symbol);
            }
        });

        PlusMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isDigit = SyntaxHandle.IsNumeric(MainScreenText);
                Number value = 0;
                value = SyntaxHandle.MakeNegative();
                String PreviousSymbol;
                String PenultimateSymbol;

                if (value.doubleValue() == Double.MAX_VALUE) { // sign changer
                    return;
                }
                else {
                    if (isDigit) { //One Digit
                        ClearAll();
                        setMainScreenText(String.valueOf(value));
                    } else { // Equation
                        PreviousSymbol = SyntaxHandle.getPreviousSymbol();
                        PenultimateSymbol = SyntaxHandle.getPenultimateSymbol();

                        if (PreviousSymbol.equals("-") && (value.intValue() > 0)) {
                            ClearLastSymbol();
                            if (!(PenultimateSymbol.equals("*")||PenultimateSymbol.equals("/")||PenultimateSymbol.equals("^"))) setMainScreenText("+"); //delete "+" before *,/,^
                            setMainScreenText(String.valueOf(value));
                        }
                        else if (PreviousSymbol.equals("+") && (value.intValue() < 0)) {
                            ClearLastSymbol();
                            setMainScreenText(String.valueOf(value));
                        }
                        else {
                            setMainScreenText(String.valueOf(value));
                        }
                    }
                }
            }
        });

        OpenBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "(";
                setMainScreenText(symbol);
            }
        });


        CloseBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = ")";
                setMainScreenText(symbol);
            }
        });

        Dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = ".";
                String PreviousSymbol = SyntaxHandle.getPreviousSymbol();
                String PenultimateSymbol = SyntaxHandle.getPenultimateSymbol();

                boolean isDigit = SyntaxHandle.IsNumeric(MainScreenText);
                boolean IsDotContains = SyntaxHandle.DotAlreadyContains();
                int len = MainScreenText.length();
                if(len == 1 && MainScreenText.equals("0")) {setMainScreenText("0.");return;}
                if(SyntaxHandle.IsOperator(PreviousSymbol)){setMainScreenText("0.");return;}

                System.out.println("DotAlreadyContains: "+SyntaxHandle.DotAlreadyContains());
                if(!IsDotContains)
                    if(!SyntaxHandle.IsSymbolTheSameAsPrevious(symbol)) setMainScreenText(symbol);


//                System.out.println("\n"+MainScreenText+"\t\t\tisDigit: " + SyntaxHandle.IsNumeric(MainScreenText));
            }
        });




        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearLastSymbol();
            }
        });

        Back.setOnTouchListener(new View.OnTouchListener() {
            long start = 0;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case (MotionEvent.ACTION_DOWN):
                        start = System.currentTimeMillis();
                        ClearLastSymbol();
                        break;
                case (MotionEvent.ACTION_UP):
                if ((System.currentTimeMillis() - start) / 1000 >= 1) {
                    ClearAll();
                    break;
                }
            }
            return true;
            }
        });


        Equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String>MainActivityTokens = ParseHandle.StartParse();
                ParseHandle.ShowTokens();

                ComputingEngine Engine = new ComputingEngine(MainActivityTokens,MainActivity.this);
                Engine.StartComputing();


                Number result = Engine.getMainResult();
                double result_doub = 0;
                int result_int = 0;
                ClearAll();
                if(result.doubleValue() % 1 == 0){ // int
                    result_int=result.intValue();
                    setMainScreenText(String.valueOf(result_int));
                }else{ // double
                    result_doub=result.doubleValue();
                    setMainScreenText(String.valueOf(result_doub));
                }
                Engine = null;
            }
        });


    }







    //onRestart -- срабатывает после onStop
    //пользователь вышел ненадолго из приложения и снова зашел в него
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //onStart -- запуск активити
    @Override
    protected  void onStart()
    {
        super.onStart();

    }

    //onResume -- отображение активити пользователю
    //вызывается после события вернуться в приложение после паузы
    @Override
    protected void onResume() {
        super.onResume();
    }


    //onPause -- вызывается в момент смахивания шторки приложений
    @Override
    protected  void onPause()
    {
        super.onPause();

    }

    //onStop() -- вышел из приложения ненадолго, но не закрыл его
    @Override
    protected void onStop() {
        super.onStop();
    }


    //onDestroy -- полный выход из приложения
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}