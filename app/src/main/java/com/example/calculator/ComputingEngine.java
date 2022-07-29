package com.example.calculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ComputingEngine {

    private  ArrayList<Double> NumbersBuffer = new ArrayList<Double>();
    private  ArrayList<String> OperatorsBuffer = new ArrayList<String>();
    private  ArrayList<String> tokens;
    private    int TokensSize;
    private   MainActivity mainActivity;
    private    SyntaxCheck SyntaxHandle;
    private  Number MainResult = 0;


    ComputingEngine(ArrayList<String>T, MainActivity mainActivity){
        this.tokens = T; this.mainActivity = mainActivity;
        TokensSize = tokens.size();
        SyntaxHandle = new SyntaxCheck(mainActivity);
    }


    private  boolean AllBracketsClosed(){
        int OpenBrackets = Collections.frequency(tokens,"(");
        int CloseBrackets = Collections.frequency(tokens,")");
        return OpenBrackets == CloseBrackets;
    }


    public   void PutNumbersOnStack(String number)
    {
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
        else BeginOk = false;

        boolean EndOk = false;
        if (SyntaxHandle.IsNumeric(tokens.get(tokens.size() - 1))) EndOk = true;
        else if (SyntaxHandle.IsBracket(tokens.get(tokens.size() - 1))) EndOk = true;
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


    //----------------------------------------------------------------------------------------------

    public void StartComputing()
    {
        if(!CheckSyntax()) return;

        for (int CurrentIndex = 0; CurrentIndex < TokensSize; CurrentIndex++){
            Analyse(tokens.get(CurrentIndex));
        }

        try
        {
            RemainCalculation();
        }
        catch (Exception e)
        {

        }

        if (OperatorsBuffer.isEmpty())
            if(NumbersBuffer.size()==1)
                    MainResult = NumbersBuffer.get(0);



    }

    //----------------------------------------------------------------------------------------------

    private void Analyse(String symbol) {
        boolean isDigit = SyntaxHandle.IsNumeric(symbol);
        if (isDigit) {
            if (NumbersBuffer.isEmpty()) {
                PutNumbersOnStack(symbol);
                return;
            } else {
                PutNumbersOnStack(symbol);
            }
        } else {
            if (OperatorsBuffer.isEmpty()) {
                PutOperatorsOnStack(symbol);
                return;
            } else {
                if (SyntaxHandle.IsBracket(symbol)) {
                    if (symbol.equals("(")) OperatorsBuffer.add(symbol);
                    if (symbol.equals(")")) UntilCloseBrackets();
                } else {
                    DefinePriorityOperation(symbol);
                }

            }
        }
    }

    private  void DefinePriorityOperation(String symbol)
    {
        int Size = OperatorsBuffer.size();
        byte PriorityCurrentOperator = DefinePriority(symbol);
        byte PriorityPreviousOperator = DefinePriority(OperatorsBuffer.get(Size - 1));

        if (PriorityCurrentOperator > PriorityPreviousOperator) {
            HighPriority(symbol);
        }
        else if (PriorityCurrentOperator < PriorityPreviousOperator)
        {
            LessPriority(symbol);
        }
        else if (PriorityCurrentOperator == PriorityPreviousOperator) {
            EqualPriority(symbol);
        }
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
            default:  priority = -1;  break;
        }
        return priority;
    }

    private  void HighPriority(String symbol)
    {
        PutOperatorsOnStack(symbol);
    }


    private  void LessPriority(String symbol)
    {
        String LastOperationOnStack;
        byte PriorityCurrentOperator;
        byte PriorityPreviousOperator;
        double FstVal;
        double ScdVal;
        double Result;
        do
        {
            try {
                LastOperationOnStack = OperatorsBuffer.get(OperatorsBuffer.size() - 1);
                PriorityCurrentOperator = DefinePriority(symbol);
                PriorityPreviousOperator = DefinePriority(LastOperationOnStack);
            }
            catch (IndexOutOfBoundsException e)
            {
                System.out.println("\nError. Try to get index out of range in line: " +  e.getStackTrace()[0].getLineNumber());
                break;
            }


            if (PriorityPreviousOperator >= PriorityCurrentOperator)
            {
                try {
                    FstVal = NumbersBuffer.get(NumbersBuffer.size() - 2);
                    ScdVal = NumbersBuffer.get(NumbersBuffer.size() - 1);
                    Result = Calculate(FstVal, LastOperationOnStack, ScdVal);
                    NumbersBuffer.remove(NumbersBuffer.size()-1);
                    NumbersBuffer.remove(NumbersBuffer.size()-1);
                    OperatorsBuffer.remove(OperatorsBuffer.size()-1);
                    NumbersBuffer.add(Result);
                }
                catch (IndexOutOfBoundsException e)
                {
                    System.out.println("\nError. Try to get index out of range in line: " +  e.getStackTrace()[0].getLineNumber());
                    break;
                }
            }
            else break;

            if (OperatorsBuffer.isEmpty()) break;

        }while(PriorityPreviousOperator >= PriorityCurrentOperator);
        OperatorsBuffer.add(symbol);
    }


    private  void EqualPriority(String symbol)
    {
        int Size = OperatorsBuffer.size();

        try{
            String LastOperationOnStack = OperatorsBuffer.get(Size - 1);
            double FstVal = NumbersBuffer.get(NumbersBuffer.size() - 2);
            double ScdVal = NumbersBuffer.get(NumbersBuffer.size() - 1);
            double Result = Calculate(FstVal, LastOperationOnStack, ScdVal);
            NumbersBuffer.remove(NumbersBuffer.size()-1);
            NumbersBuffer.remove(NumbersBuffer.size()-1);
            OperatorsBuffer.remove(OperatorsBuffer.size()-1);
            NumbersBuffer.add(Result);
            OperatorsBuffer.add(symbol);
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.println("\nError. Try to get index out of range in line: " +  e.getStackTrace()[0].getLineNumber());
        }

    }

    private static double Calculate(double val1, String operator, double val2)
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


    private  void UntilCloseBrackets()
    {
        int index; //last element
        String Operator;
        Double Value1;
        Double Value2;
        Double Result;
        do{
            try {
                index = OperatorsBuffer.size()-1; //define index
                Operator = OperatorsBuffer.get(index); // get operator
                if (Operator.equals("(")) break;
                Value1 = NumbersBuffer.get(NumbersBuffer.size()-2); //val1
                Value2 = NumbersBuffer.get(NumbersBuffer.size()-1);//val2
                Result = Calculate(Value1,Operator,Value2);
                NumbersBuffer.remove(NumbersBuffer.size()-1);
                NumbersBuffer.remove(NumbersBuffer.size()-1);
                OperatorsBuffer.remove(OperatorsBuffer.size()-1);
                NumbersBuffer.add(Result);
            }
            catch (IndexOutOfBoundsException e)
            {
                System.out.println("\nError. Try to get index out of range in line: " +  e.getStackTrace()[0].getLineNumber());
                break;
            }
        } while(!Operator.equals("("));

        try {
            OperatorsBuffer.remove(OperatorsBuffer.size()-1); // delete open bracket
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.println("\nError. Try to get index out of range in line: " +  e.getStackTrace()[0].getLineNumber());
        }

    }

    private    void RemainCalculation()
    {
        String LastOperationOnStack = OperatorsBuffer.get(OperatorsBuffer.size() - 1);
        double val1,val2,res;
        do {
            if (OperatorsBuffer.size() > 0)
                LastOperationOnStack = OperatorsBuffer.get(OperatorsBuffer.size() - 1);
            else break;

            if (NumbersBuffer.size() > 0)
            {
                try {
                    val1 = NumbersBuffer.get(NumbersBuffer.size()-2);
                    val2 = NumbersBuffer.get(NumbersBuffer.size()-1);
                    res = Calculate(val1,LastOperationOnStack,val2);
                    NumbersBuffer.remove(NumbersBuffer.size()-1);
                    NumbersBuffer.remove(NumbersBuffer.size()-1);
                    OperatorsBuffer.remove(OperatorsBuffer.size()-1);
                    NumbersBuffer.add(res);
                }
                catch (IndexOutOfBoundsException e)
                {
                    System.out.println("\nError. Try to get index out of range in line: " +  e.getStackTrace()[0].getLineNumber());
                    break;
                }
            }
            else break;
        }while (OperatorsBuffer.size() > 0);
    }

}
