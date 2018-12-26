package com.example.shen.myfirstapp;

import java.util.Stack;

/**
 * Created by shen on 6/16/18.
 */

import java.lang.Math;

public class Calculation {

    private String input = "";
    private String specialCharactors = "0123456789.qwer()";

    public Calculation(String input) {
        this.input = input;
    }

    private boolean errorDetection() {
        int nparenthesL=0, nparenthesR=0;
        for (int i = 0; i < input.length(); i++) {

            //check special charactors
            boolean included = false;
            for (int j = 0; j < specialCharactors.length(); j++) {
                if (input.charAt(i) == specialCharactors.charAt(j))
                    included = true;
            }
            if (!included)
                return false;

            if (Character.isLetter(input.charAt(i))) {
                if (i == 0 || i == input.length() - 1 ||
                        Character.isLetter(input.charAt(i + 1)))
                    return false;
            }

            else if (input.charAt(i) == '.') {
                if (i == input.length() - 1 ||
                        (!Character.isDigit(input.charAt(i + 1))))
                    return false;
            }

            else if (input.charAt(i) == '(')
                nparenthesL++;

            else if (input.charAt(i) == ')') {
                nparenthesR++;
                if(i == input.length()-1)
                    continue;
                if(Character.isDigit(input.charAt(i+1)))
                    input = input.substring(0, i) + "e" +input.substring(i+1, input.length()-1);
                else if(input.charAt(i+1)=='.')
                    return false;
            }

        }
        return nparenthesL>=nparenthesR;
    }

    private void loadExpression() {
        for (int i = 0; i < input.length(); i++) {
            Character current = input.charAt(i);
            if(Character.isDigit(current))
                continue;
            if(current=='.')
                continue;
            if(Character.isLetter(current) || current == '(' || current == ')'){
                input = input.substring(0,i)+" "+current+" "+input.substring(i+1,input.length());
                i++;
            }
        }
        //make tree

    }

    public String convert2postfix(){
        Stack<String> opStack = new Stack<String>();
        Stack<String> valStack = new Stack<String>();
        String[]  inputs = this.input.split(" ");
        System.out.println("|"+inputs[0]+"|");
        for(int i=0; i<inputs.length; i++){
            if (inputs[i].equals(""))
                continue;

            if(isNumeric(inputs[i]))
                valStack.push(inputs[i]);
            else if(inputs[i].equals("("))
                opStack.push(inputs[i]);
            else if(inputs[i].equals(")")){
                while(!opStack.peek().equals("("))
                    valStack.push(applyOp(opStack.pop(),valStack.pop(),
                            valStack.pop()));
                opStack.pop();
            }
            else {
                while(!opStack.empty() && hasPrecedence(inputs[i], opStack.peek())){
                    valStack.push(applyOp(opStack.pop(), valStack.pop(),valStack.pop()));
                }

                opStack.push(inputs[i]);
            }
        }

        while(!opStack.empty())
            valStack.push(applyOp(opStack.pop(), valStack.pop(),valStack.pop()));

        return valStack.pop();
    }

    private static boolean hasPrecedence(String op1, String op2)
    {
        if (op2.equals("(") || op2.equals(")"))
            return false;
        if ((op1.equals("e") || op1.equals("r")) && (op2.equals("q") || op2.equals("w")))
            return false;
        else
            return true;
    }


    private static String applyOp(String operator, String n2, String n1){
        double a = Double.parseDouble(n1);
        double b = Double.parseDouble(n2);
        switch (operator) {
            case "q":
                return (a + b)+"";
            case "w":
                return (a - b)+"";
            case "e":
                return (a * b)+"";
            case "r":
                if(Integer.parseInt(n2) == 0)
                    return "Wrong input, cannot be divided to Zero";
                return (a / b)+"";
        }
        return 0 + "";
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }




    public String display() {
        if (errorDetection()) {
            loadExpression();
            return convert2postfix();
        }
        return "Wrong input";
    }

}
