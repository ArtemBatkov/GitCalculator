package com.HARDroid.calculator;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class Parse {

    private ArrayList<String> tokens = new ArrayList<String>();

    private String InputText;
    private final  MainActivity mainActivity;
    private final SyntaxCheck SyntaxHandle;

    Parse(MainActivity mainActivity)
    {
        this.mainActivity= mainActivity;
        InputText = mainActivity.getMainScreenText(); //stream of text
        SyntaxHandle = new SyntaxCheck(this.mainActivity);

    }

    public void setToken(String token) {
        //add token to end of tokens
        tokens.add(token);
    }

    public void replaceToken(int index, String token) {
        try {
            tokens.set(index,token);
        }
        catch (Exception ex){
            System.out.println("Error in replaceToken" + ex.getMessage() + " in line "+ex.getStackTrace()[0].getLineNumber());
        }
    }

    public ArrayList<String> GetTokens()
    {
        return  tokens;
    }

    public void ShowTokens()
    {
        System.out.println("\n\t\t\t-------------------------------------------");
        System.out.println("\t\t\tTOKENS:");
        int len = tokens.size();
        for (int i = 0 ; i < len ; i++){
            System.out.println("token № "+ i + ": "+ tokens.get(i));
        }
        System.out.println("\n\t\t\t-------------------------------------------");
    }


    public String getSymbol(int index)
    {
        try{
            return Character.toString(InputText.charAt(index));
        }
        catch (IndexOutOfBoundsException ex){
            return "";
        }
    }

    public void UpdateText(){
        InputText = mainActivity.getMainScreenText();
    }

    public void  ClearTokens(){
        tokens.clear();
    }

    public void DeleteEmptySpaces(){
        while(tokens.remove("")) {};
    }

    private String UnaryBinary(String previous, String symbol, String next) {
        //Analyse binary or unary
        DeleteEmptySpaces();
        if (!symbol.equals("-")) return "";
        // Binary
        if (previous.equals(")")) return  "binary";//100% binary
        else if (SyntaxHandle.IsNumeric(previous)) return "binary"; // 100% binary
        else return "unary";
    }

    private String  getPreviousToken(){
        try{
            return tokens.get(tokens.size()-1);
        }
        catch (Exception ex){
            return "";
        }
    }

    private  void  DeletePreviousToken(){
        try {
            tokens.remove(tokens.size()-1);
        }
        catch (Exception ex){

        }
    }

    private boolean IsDigitPart(String symbol){
        return SyntaxHandle.IsNumeric(symbol)||SyntaxHandle.IsDot(symbol);
    }

    private boolean IsOperator(String symbol) {
        return SyntaxHandle.IsOperator(symbol);
    }

    private boolean IsBracket(String symbol) {
        return SyntaxHandle.IsBracket(symbol);
    }

    private boolean IsMinus(String symbol) {
        return symbol.equals("-");
    }

    private void ReplaceByPIEXP(){
        for(int i = 0; i < tokens.size(); i++){
            if(tokens.get(i).equals("π")) tokens.set(i, String.valueOf(Math.PI));
            if(tokens.get(i).equals("e")) tokens.set(i, String.valueOf(Math.E));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList StartParse(){
        UpdateText();
        ClearTokens();
        int InputSize = InputText.length();
        String Symbol = "";
        boolean isDigit = false;

        String tempNum  = "";
        String PreviousSymbol, NextSymbol;
        String UnaBina = "";
        String PreviousToken;
        ArrayList<ArrayList<String>>UnaryBinaryInfo = new ArrayList<ArrayList<String>>();
        boolean SymbolGate = false;



        for (int Elem = 0; Elem < InputSize; Elem++) {
            DeleteEmptySpaces();
            Symbol = getSymbol(Elem);
            isDigit = SyntaxHandle.IsNumeric(Symbol);
            PreviousSymbol = getSymbol(Elem-1);
            NextSymbol = getSymbol(Elem+1);

            if (IsDigitPart(Symbol)) {//is a digit part, so add to Temp
                tempNum += Symbol;
            }
            else{
                if (IsBracket(Symbol)) {
                    tokens.add(tempNum);
                    tokens.add(Symbol);
                    tempNum = "";
                    continue;
                }

                if(IsMinus(Symbol)){
                    UnaBina = UnaryBinary(PreviousSymbol,Symbol,NextSymbol);
                    tokens.add(tempNum);
                    if (UnaBina.equals("binary")){
                        tempNum = "";
                        tokens.add(Symbol);
                    }
                    else{
                        tempNum = Symbol;
                    }
                    continue;
                }

                if(Character.isLetter(Symbol.charAt(0))){
                    if(!tempNum.matches("[A-Za-z]+")) {tokens.add(tempNum);tempNum="";}
                    tempNum +=Symbol;
                    continue;
                }
                tokens.add(tempNum);
                tokens.add(Symbol);
                tempNum = "";
            }
        }
        if (!tempNum.isEmpty()) tokens.add(tempNum);
        DeleteEmptySpaces();
        ReplaceByPIEXP();

        return tokens;
    }

}
