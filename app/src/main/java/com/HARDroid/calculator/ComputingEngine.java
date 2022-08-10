package com.HARDroid.calculator;


import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.Collections;


@RequiresApi(api = Build.VERSION_CODES.N)
public class ComputingEngine {

    private  ArrayList<Double> NumbersBuffer = new ArrayList<Double>();
    private  ArrayList<String> OperatorsBuffer = new ArrayList<String>();
    private  ArrayList<String> tokens;
    private    int TokensSize;
    private   MainActivity mainActivity;
    private    SyntaxCheck SyntaxHandle;
    private double MainResult = 0;
    private static boolean MainLoop = false;
    private Throwable thrwbl = new Throwable();
    private static int LineNumber = 0;
    private static String ThrownCause;

    ComputingEngine(ArrayList<String>T, MainActivity mainActivity){
        this.tokens = T; this.mainActivity = mainActivity;
        TokensSize = tokens.size();
        SyntaxHandle = new SyntaxCheck(mainActivity);
    }




    //----------------------------------------------------------------------------------------------

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void StartComputing()
    {
         int CurrentIndex = 0;
         int TokenSize = tokens.size();
         String CurrentSymbol;
         boolean isDigit;
         MainLoopBegin();
         while(MainLoop){
             if(CurrentIndex < TokenSize){ //don't go to the end of the equation
                 CurrentSymbol = tokens.get(CurrentIndex);
                 isDigit = SyntaxHandle.IsNumeric(CurrentSymbol);

                 if(isDigit){ // isDigit -- put on num stack
                     PutNumbersOnStack(CurrentSymbol);
                 }
                 else{
                     if(OperatorsBuffer.isEmpty()) PutOperatorsOnStack(CurrentSymbol);
                     else Analyse(CurrentSymbol);
                 }
                CurrentIndex+=1;
             }
             else{
                 //end of the equation
                 RemainCalculation();
             }
         }
        System.out.println("\nNumBuffer: " + NumbersBuffer);
        System.out.println("\nOperatorBuffer: " + OperatorsBuffer);
    }



    //----------------------------------------------------------------------------------------------

    private void Analyse(String symbol) {
         if (symbol.equals(")")) { //Close Bracket
             UntilKillBracket(symbol);
             return;
         }

         if (symbol.equals("(")){//Open Bracket
             PutOperatorsOnStack(symbol);
             return;
         }
         String TopOperator = LookOnTopOperatorStack();
        if (OperatorsBuffer.size() >= 1) TopOperator = LookOnTopOperatorStack();
        else return;

        byte PriorityOnTop = DefinePriority(TopOperator);
        byte PriorityCurrent = DefinePriority(symbol);

        while (PriorityOnTop >= PriorityCurrent){// F.e. on the top is "*" and current is "-"
            //do operations till we break conditions
            if (!UpdateBuffer()) break; // was exception
            if(OperatorsBuffer.isEmpty()) {break;}
            if (OperatorsBuffer.size() < 1) return;
            TopOperator = LookOnTopOperatorStack();
            PriorityOnTop = DefinePriority(TopOperator);
            PriorityCurrent = DefinePriority(symbol);
        }
        PutOperatorsOnStack(symbol);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean UpdateBuffer(){
        //returns NoErrors = true, if they weren't
        boolean NoErrors = true;
        String TopOperator;
        boolean isBinary;

        if (OperatorsBuffer.size()<1) return NoErrors = false;
        TopOperator = LookOnTopOperatorStack();

        isBinary = isBinaryOperator(TopOperator);

        if(isBinary){
            if (NumbersBuffer.size()<2) return NoErrors = false; // we can't calculate, because less than 2 values in buffer
            if(OperatorsBuffer.isEmpty()) return  NoErrors = false; // we need 1 operator or more
            double Value2 = Double.parseDouble(InsertTopNumBuffer());
            double Value1 = Double.parseDouble(InsertTopNumBuffer());
            double result;
            result = Calculate(Value1,TopOperator,Value2);
            PutNumbersOnStack(String.valueOf(result));
            RemoveFromTheTopOperatorStack();
        }
        else{//Unary
            if (NumbersBuffer.size()<1) return NoErrors = false; // we can't calculate, because less than 2 values in buffer
            if(OperatorsBuffer.isEmpty()) return  NoErrors = false; // we need 1 operator or more
            double Value = Double.parseDouble(InsertTopNumBuffer());
            double result;
            result = Calculate(Value,TopOperator);
            PutNumbersOnStack(String.valueOf(result));
            RemoveFromTheTopOperatorStack();
        }
        return NoErrors; // if everything is ok
    }



    private void RemainCalculation(){
        if(NumbersBuffer.isEmpty()) // that is exception, we don't have an answer
        {
            MainLoopEnd();
            MainResult = 0;
            ThrownCause = "NumberBuffer was Empty in RemainCalculation; Solution: MainResult = 0";
            LineNumber = thrwbl.getStackTrace()[0].getLineNumber();
            ShowInteruptionMessage();
            return;
        }
        boolean isEmpty = OperatorsBuffer.isEmpty();
        int tries = 500; // 500 tries;
        int i = 0;
        while (!isEmpty){
            if(i>tries) {
                MainLoopEnd();
                return; // something got wrong
            }
            if(!UpdateBuffer()) break;//exception was
            i+=1;
            isEmpty = OperatorsBuffer.isEmpty();
        }
        MainLoopEnd();
        MainResult = NumbersBuffer.get(0);
    }








    private String InsertTopNumBuffer(){
        //method insert get and than remove a num from top of the NumBuffer
        //Not Control IndexBounds
        String temp = NumbersBuffer.get(NumbersBuffer.size()-1).toString();
        NumbersBuffer.remove(NumbersBuffer.size()-1);
        return temp;
    }




    private  boolean isBinaryOperator(String chr){
        switch (chr){
            case "+": return  true;
            case "-": return  true;
            case "*": return  true;
            case "/": return  true;
            case "^": return  true;
            default: return false;
        }
    }







    private void UntilKillBracket(String symbol){
        String TopOfStack;
        TopOfStack = LookOnTopOperatorStack();

        if(TopOfStack.equals("empty")){// exception has occurred
            ThrownCause = "function: UntilKillBracket, returned: empty after LookOnTopOperatorStack";
            LineNumber = thrwbl.getStackTrace()[0].getLineNumber();
            ShowInteruptionMessage();
            return;
        }

        while (!TopOfStack.equals("(")){//Open Bracket is not on the top?
            if(!UpdateBuffer()) return; //exception
            TopOfStack = LookOnTopOperatorStack();
            if(TopOfStack.equals("empty")){// exception has occurred
                ThrownCause = "function: UntilKillBracket, returned: empty after LookOnTopOperatorStack";
                LineNumber = thrwbl.getStackTrace()[0].getLineNumber();
                ShowInteruptionMessage();
                return;
            }
        }
        //when we meet OpenBracket, we delete it and forgot symbol
        RemoveFromTheTopOperatorStack();
        //continue to MainLoop
    }



    private String LookOnTopOperatorStack(){
        try{
            return OperatorsBuffer.get(OperatorsBuffer.size()-1);
        }
        catch (Exception exp){
            return "empty";
        }
    }

    private void RemoveFromTheTopOperatorStack() {
        try {
            OperatorsBuffer.remove(OperatorsBuffer.size()-1);
        }
        catch (Exception exp){}
    }



    public void MainLoopBegin() {MainLoop = true;}
    public void MainLoopEnd() {MainLoop = false;}

    private  boolean AllBracketsClosed(){
        int OpenBrackets = Collections.frequency(tokens,"(");
        int CloseBrackets = Collections.frequency(tokens,")");
        return OpenBrackets == CloseBrackets;
    }



    public   void PutNumbersOnStack(String number)
    {
        //API 24 !!!!!!
        NumbersBuffer.add(Double.parseDouble(number));
    }

    public   void PutOperatorsOnStack(String operator)
    {
        OperatorsBuffer.add(operator);
    }

    private  boolean CheckExcessOperation()
    {

        boolean BeginOk = false;
        if (SyntaxHandle.IsNumeric(tokens.get(0))) BeginOk = true;
        else if (SyntaxHandle.IsBracket(tokens.get(0))) BeginOk = true;
        else if (SyntaxHandle.IsUnaryOperator(tokens.get(0))) BeginOk = true;
        else BeginOk = false;

        boolean EndOk = false;
        if (SyntaxHandle.IsNumeric(tokens.get(tokens.size() - 1))) EndOk = true;
        else if (SyntaxHandle.IsBracket(tokens.get(tokens.size() - 1))) EndOk = true;
        else if (SyntaxHandle.IsUnaryOperator(tokens.get(tokens.size() - 1))) EndOk = true;
        else EndOk = false;

        boolean BeginEndOk = BeginOk && EndOk;

        return  BeginEndOk;
    }

    private boolean CheckSyntax()
    {
        boolean Syntax = false;
        boolean CheckBrackets = AllBracketsClosed();
        boolean ExcessOperations = CheckExcessOperation();

        Syntax = CheckBrackets && ExcessOperations;
        return Syntax;

    }

    public Number getMainResult() {
        return MainResult;
    }





    private static byte DefinePriority(String symbol)
    {
        byte priority = -1;
        switch (symbol) {
            case "+": priority = 1; break;
            case "-": priority = 1;  break;
            case "*":  priority = 2;  break;
            case "/":  priority = 2;  break;
            case "^": priority = 3; break;
            case "√": priority = 3; break;
            case "%" : priority = 3; break;
            case "!": priority = 3; break;
            case "Sin": priority = 2; break;
            case "Cos": priority = 2; break;
            case ("tg"): priority = 2;break;
            case("log"): priority = 2; break;
            case("ln"):priority = 2; break;
            default:  priority = -1;  break;
        }
        return priority;
    }

    private  double Calculate(double val, String operator)
    {
        double result =0;
        switch (operator)
        {
            case "√": result = Math.sqrt(val); break;
            case "%": result = val/100; break;
            case "!":
                result =1;
                for (int i = 2; i <= val; i++) {
                    result *= i;
                }
                return result;
            case("Sin"): result = Math.sin(val); break;
            case("Cos"):result = Math.cos(val);break;
            case ("tg"):result = Math.tan(val);break;
            case ("ctg"):result =  1/Math.tan(val); break;
            case ("log"): result = Math.log10(val);break;
            case ("ln"): result = Math.log(val); break;
        }
        return  result;
    }


    private  double Calculate(double val1, String operator, double val2)
    {
        double result = 0;
        switch (operator)
        {
            case "+": result = val1 + val2; break;
            case "-": result = val1 - val2;break;
            case "*": result = val1 * val2;break;
            case "/": result = val1/val2;break;
            case "^": result = Math.pow(val1,val2); break;
        }
        return  result;
    }








    private void ShowInteruptionMessage() {
        System.out.println("Exceptions was in line: "+LineNumber+"; ThrownCause: "+ThrownCause);
        LineNumber = 0;
        ThrownCause ="";
    }
}
