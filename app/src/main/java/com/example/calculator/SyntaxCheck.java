package com.example.calculator;

public class SyntaxCheck {

    private final MainActivity mainActivity;
    private  String MainScreenText;

    SyntaxCheck (MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        MainScreenText = mainActivity.getMainScreenText();
    }

    public boolean IsNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean IsBracket (String symbol)
    {
        if (symbol.equals("(")) return  true;
        else if (symbol.equals(")")) return  true;
        else return  false;
    }

    public  boolean IsDot(String symbol){
        if(symbol.equals(".")) return true;
        else return  false;
    }



    public void getCurrentText()
    {
        MainScreenText =  mainActivity.getMainScreenText();
    }


    public boolean IsSymbolTheSameAsPrevious(String symbol){
        getCurrentText();
        String PreviousSymbol;
        int len = MainScreenText.length();
        PreviousSymbol= MainScreenText.substring(len-1);
        if(PreviousSymbol.equals(symbol)) return  true;
        else return false;
    }

    public  boolean IsOperator(String symbol) { return mainActivity.getOperatorList().contains(symbol); }

    public boolean IsLastSymbolOperator(){
        getCurrentText();
        int len  = MainScreenText.length();
        return IsOperator(MainScreenText.substring(len-1));
    }

    public void ChangeLastOperatorForNew(String symbol)
    {
        //Change operator except brackets
        if (IsBracket(getPreviousSymbol())) {mainActivity.setMainScreenText(symbol); return;}
        mainActivity.ClearLastSymbol();
        mainActivity.setMainScreenText(symbol);
    }

    public  String getPreviousSymbol()
    {
        getCurrentText(); int len = MainScreenText.length();
        return MainScreenText.substring(len-1);
    }

    public String getPenultimateSymbol()
    {
        getCurrentText();
        int len  = MainScreenText.length();
        try {
            return    MainScreenText.substring(len-2,len-1);
        }
        catch (Exception ex)
        {
            return "";
        }

    }





    public Number FromEndToFirstOperator()
    {

        String PreviousSymbol= getPreviousSymbol();
        boolean isOperator  = IsOperator(PreviousSymbol);
        String Num  = PreviousSymbol;
        mainActivity.ClearLastSymbol();
        int len = MainScreenText.length();

        while (!isOperator&&(len>0)){
            PreviousSymbol = getPreviousSymbol();
            isOperator = IsOperator(PreviousSymbol);
            len = MainScreenText.length();
            if (len==1 && (MainScreenText.equals("0"))) break;
            if (isOperator) break;
            Num += PreviousSymbol;
            mainActivity.ClearLastSymbol();

        }
        Num  = new StringBuilder(Num).reverse().toString();//reverse string
        int Value_int = 0;
        double Value_doub = 0;

        Number res =0;
        try{
            if (Num.contains(".")) {
                Value_doub = Double.parseDouble(Num); // parse to double and then change sign
                if(!PreviousSymbol.equals("-")) Value_doub = -Value_doub ;
                res = Value_doub;
                return Value_doub;
            }
            else  {
                Value_int = Integer.parseInt(Num);
                if(!PreviousSymbol.equals("-")) Value_int = -Value_int ;
                res = Value_int;
                return Value_int;
            }
        }
        catch (NumberFormatException fe)
        {
            System.out.println("UnReal to convert to float in line " + fe.getStackTrace()[0].getLineNumber());
        }
        return res;
    }



    public Number MakeNegative()
    {
        getCurrentText();
        String PreviousSymbol= getPreviousSymbol();
        boolean isOperator = IsOperator(PreviousSymbol);
        boolean isDigit = IsNumeric(PreviousSymbol);
        boolean isBracket = IsBracket(PreviousSymbol);
        boolean isDot = IsDot(PreviousSymbol);

        if (isOperator)
        {
            if(PreviousSymbol.equals("+")) ChangeLastOperatorForNew("-");
            else if (PreviousSymbol.equals("-")) ChangeLastOperatorForNew("+");
        }
        else if (isDigit) {
           return FromEndToFirstOperator();
        }
        else if (isDot) {
            return FromEndToFirstOperator();
        }
        return Double.MAX_VALUE;
    }


    public boolean DotAlreadyContains()
    {
        getCurrentText();
        int len = MainScreenText.length();
        String Num = "";
        String CurrentElem = "";
        boolean isDigit;
        for (int Elem = len-1; Elem>=0; Elem--) {
            CurrentElem = Character.toString(MainScreenText.charAt(Elem));
            isDigit = IsNumeric(CurrentElem);
            if (!isDigit && !CurrentElem.equals(".")) break;
            Num += CurrentElem;
        }
        return Num.contains(".");
    }

}
