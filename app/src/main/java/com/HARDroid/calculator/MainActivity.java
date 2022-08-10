package com.HARDroid.calculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.HARDroid.R;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView MainScreen;
    private Button Num0,Num1,Num2,Num3,Num4,Num5,Num6,Num7,Num8,Num9,Plus,Minus,Mult,Divide,
            Percent, Square, Factor,PowerOf,OpenBracket,CloseBracket,Dot,C,Equal,
            sin,cos, tg, ctg,
            pi,exp,log,ln;

    private ImageView Back,Fx;
    private static LinearLayout layout_Fx,_789_,_456_,_123_,_0_,functions, _Trigonometry_;
    private String MainScreenText = "0";
    private ArrayList <String> OperatorBuffer = new ArrayList<String>(){
        {
            add("+"); add("-"); add("*"); add("/"); add("(");add(")");add("^");
        }
    };

    private ArrayList<String> OperatorBufferUnary = new ArrayList<String>(){
        {
            add("√");
        }
    };

    private ArrayList <LinearLayout> layouts = new ArrayList<LinearLayout>(){};



    public  ArrayList getOperatorList()
    {
        return OperatorBuffer;
    }

    public ArrayList getUnaryOperatorList(){
        return OperatorBufferUnary;
    }

    public void setMainScreenText(String symbol) {
        SyntaxCheck check = new SyntaxCheck(this);

        if(MainScreenText.equals("0")&&!check.IsBracket(symbol) && check.IsOperator(symbol)){
            MainScreenText +=symbol;
            MainScreen.setText(MainScreenText);
            check  = null;
            return;
        }


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
        check  = null;
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

    public void ShowInfo(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }



    public void SetWeight(LinearLayout layout, int weight)
    {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = weight;
        layout.setLayoutParams(params);
        params = null;
    }

    public void SetDefaultWeight(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1; // default
        _0_.setLayoutParams(params);
        _123_.setLayoutParams(params);
        _456_.setLayoutParams(params);
        _789_.setLayoutParams(params);
        layout_Fx.setLayoutParams(params);
        params = null;
    }

    public void AlignFxSize(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.width = Num0.getWidth();
        params.height = Num0.getHeight();
        params.weight = 1;
        Fx.setLayoutParams(params);
        params = null;
    }

    public void ShowLayout(LinearLayout layout)
    {
        layout.setVisibility(View.VISIBLE);
    }

    public void HideLayout(LinearLayout layout) {
        layout.setVisibility(View.GONE);
    }

    public void ShowFunctions(){
        if(functions.getVisibility() == View.VISIBLE){ //hide functions, but show nums
            SetDefaultWeight();
            for(int i = 0; i < layouts.size(); i++)
                ShowLayout(layouts.get(i));
            HideLayout(functions);


        }
        else{//show functions, but hide nums
            for(int i = 0; i < layouts.size(); i++)
                HideLayout(layouts.get(i));
            SetWeight(layout_Fx,1);
            SetWeight(functions,4);
            ShowLayout(functions);
            AlignTrigonomIconSize();
        }
    }

    private void AlignTrigonomIconSize(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.height = Num0.getHeight();
        params.width = Num0.getWidth();
        params.leftMargin = 0;
        params.rightMargin = 0;
        params.bottomMargin = 0;
        params.topMargin = 0;
        PowerOf.setLayoutParams(params);
        Percent.setLayoutParams(params);
        Square.setLayoutParams(params);
        Factor.setLayoutParams(params);
        sin.setLayoutParams(params);
        cos.setLayoutParams(params);
        tg.setLayoutParams(params);
        ctg.setLayoutParams(params);
        log.setLayoutParams(params);
        ln.setLayoutParams(params);
        exp.setLayoutParams(params);
        pi.setLayoutParams(params);
        params = null;
    }

    public String getSymbolFromEnd(int indexFromEnd){
        int len = MainScreenText.length();
        String Symbol;
        try{
            Symbol = MainScreenText.substring(len+indexFromEnd);
            return Symbol;
        }
        catch (Exception exp){
            return "";
        }
    }



    public void AlignFx(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.height = OpenBracket.getHeight();
        params.width = OpenBracket.getWidth();
        Fx.setLayoutParams(params);
        params = null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        Back.setImageResource(R.drawable.back);

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
        C = findViewById(R.id.C);
        Dot = findViewById(R.id.Dot);
        OpenBracket = findViewById(R.id.OpenBracket);
        CloseBracket = findViewById(R.id.CloseBracket);
        Equal = findViewById(R.id.Equal);

        Plus = findViewById(R.id.Plus);
        Minus = findViewById(R.id.Minus);
        Mult = findViewById(R.id.Multiply);
        Divide = findViewById(R.id.Divide);
        Fx = findViewById(R.id.Fx);
        PowerOf = findViewById(R.id.PowerOf);
        Percent = findViewById(R.id.Percent);
        Square = findViewById(R.id.Square);
        Factor = findViewById(R.id.Factor);

        sin = findViewById(R.id.Sin);
        cos =  findViewById(R.id.Cos);
        tg =  findViewById(R.id.Tg);
        ctg = findViewById(R.id.CTg);

        pi =  findViewById(R.id.Pi);
        exp =  findViewById(R.id.Exp);
        log =  findViewById(R.id.Log);
        ln =  findViewById(R.id.Ln);

        functions = findViewById(R.id.functions);
        layout_Fx = findViewById(R.id.linear_layout_Fx);
        _789_ = findViewById(R.id._789_);
        _456_ = findViewById(R.id._456_);
        _123_ = findViewById(R.id._123_);
        _0_ = findViewById(R.id._0_);
        _Trigonometry_ =findViewById(R.id._Trigonometry_);

        layouts.add(_789_);layouts.add(_456_);layouts.add(_123_);//layouts.add(_0_);

        SyntaxCheck SyntaxHandle = new SyntaxCheck(MainActivity.this);

        Parse ParseHandle = new Parse(MainActivity.this);

//         AlignFx();


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
                if(SyntaxHandle.IsSymbolTheSameAsPrevious(symbol)) return;
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

        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearAll();
            }
        });


        Back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String LastSymbol = getSymbolFromEnd(-1);
                String SecondToLastSymbol = getSymbolFromEnd(-2);

                if(LastSymbol.equals("(")&&SecondToLastSymbol.isEmpty()){ClearLastSymbol();return;}

                if (LastSymbol.equals("(") && Character.isLetter(SecondToLastSymbol.charAt(0))) {
                    //if a open bracket and after that there is a letter
                    //so
                    //delete full operation
                    ClearLastSymbol(); // deleting a bracket first
                    LastSymbol = getSymbolFromEnd(-1);
                    while (Character.isLetter(LastSymbol.charAt(0))) {
                        ClearLastSymbol();
                        LastSymbol = getSymbolFromEnd(-1);
                    }
                } else
                    ClearLastSymbol();
            }
        });


        Fx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ShowInfo(((Button) view).getText().toString());
                ShowFunctions();
            }
        });






        Equal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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
                    setMainScreenText(String.valueOf(String.format("%.2f",result_doub).replace(",",".")));
                }
                Engine = null;
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

        Percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String symbol = "%";
                if(SyntaxHandle.IsSymbolTheSameAsPrevious(symbol)) return;
                setMainScreenText(symbol);
            }
        });

        Square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "√";
                if(SyntaxHandle.IsSymbolTheSameAsPrevious(symbol)) return;
                setMainScreenText(symbol);
            }
        });

        Factor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "!";
                if(SyntaxHandle.IsSymbolTheSameAsPrevious(symbol)) return;
                setMainScreenText(symbol);
            }
        });

        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "Sin(";
                setMainScreenText(symbol);
            }
        });

        cos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "Cos(";
                setMainScreenText(symbol);
            }
        });

        tg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "tg(";
                setMainScreenText(symbol);
            }
        });


        ctg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "ctg(";
                setMainScreenText(symbol);
            }
        });


        pi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "π";
                setMainScreenText(symbol);
            }
        });

        exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "e";
                setMainScreenText(symbol);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "log(";
                setMainScreenText(symbol);
            }
        });

        ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = "ln(";
                setMainScreenText(symbol);
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