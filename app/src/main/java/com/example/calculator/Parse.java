package com.example.calculator;

import java.util.ArrayList;
import java.util.logging.Handler;

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


    public ArrayList StartParse(){
        UpdateText();
        ClearTokens();
        int InputSize = InputText.length();
        String Symbol = "";
        boolean isDigit = false;

        String tempNum  = "";

        for (int Elem = 0; Elem < InputSize; Elem++){
            Symbol = getSymbol(Elem);
            isDigit = SyntaxHandle.IsNumeric(Symbol);
            String PreviousSymbol,NextSymbol;

            if (isDigit || Symbol.equals(".")) {
                tempNum += Symbol;
            }
            else if (Symbol.equals("-")) { // SPECIAL FOR MINUS
                PreviousSymbol = getSymbol(Elem-1);
                NextSymbol = getSymbol(Elem+1);

                //Look regarding minus
                if (SyntaxHandle.IsOperator(PreviousSymbol) && SyntaxHandle.IsNumeric(NextSymbol)) {
                    //Example: 5 * -8, right:8 left:*, so minus contains num
                    if (SyntaxHandle.IsBracket(PreviousSymbol)) {
                        tokens.add(tempNum);
                        tempNum = "";
                        tokens.add(Symbol);
                    } else tempNum += Symbol;
                }
                else if (PreviousSymbol.equals("") && SyntaxHandle.IsNumeric(NextSymbol)){
                    //Example: -300 * 1, left: "", right:3
                    tempNum += Symbol;
                }
                else if (SyntaxHandle.IsNumeric(PreviousSymbol) && SyntaxHandle.IsNumeric(NextSymbol))
                {
                    //Example: 90 - 4; left: 90 right:4
                    tokens.add(tempNum);
                    tempNum = "";
                    tokens.add(Symbol);
                }
                else{
                    tokens.add(tempNum);
                    tempNum = "";
                    tokens.add(Symbol);
                }
            }
            else{
                tokens.add(tempNum);
                tempNum = "";
                tokens.add(Symbol);
            }
        }
        tokens.add(tempNum);
        DeleteEmptySpaces();
        return tokens;
    }





}
