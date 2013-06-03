/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiccalc.ejb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tim Nielsen
 */
public  class Calculator 
{
    private static Matcher m;
    private static String inString;
    
    public Calculator(String calcInString)
    {
        inString = calcInString;
        String pattern = createPattern(inString.length());//Bygger pattern
        Pattern r = Pattern.compile(pattern);
        m = r.matcher(inString);
        m.find();
    }
    public String startCalc()
    {
       checkForBrackets(0);
       return inString;
    }
    public static void checkForBrackets(int startingPoint) // utför alla beräkningar
    {
       int i = startingPoint+1;//Måste ökas med ett så att den inte hittar sig själv 
       
       //Kollar om det finns ett ( anropar sig själv tills den inte hittar mer 
        while(i < m.groupCount())
        {
            if(m.group(i).equals("("))
            {                
               checkForBrackets(i);
            }
            i++;
        }
        //Utför logik beräkningar, while loop som körs tills den hittar EOF
        //Körs tills det bara finns ett tecken kvar tex 1 eller det bara finns kvar en siffra och två paranteser tex (1) 
        i = 0;
        boolean closingBracketFound = false;
        boolean notOperandFound = false;
        while(startingPoint+1 < m.groupCount() && closingBracketFound == false)
        {
            if(!m.group(startingPoint+2).equals(")"))
            {
                if(m.group(startingPoint+2).equals("&"))// AND
                {
                    if(m.group(startingPoint+1).equals("1") && m.group(startingPoint+3).equals("1"))
                    {//Om det är sant
                        if(notOperandFound) // Om ett NOT tecken fanns innan tex !1&1
                            updateStringAndPatter(startingPoint,'0');//Anropar en metod som updater inString och m så att grupperna stämmer
                        else
                            updateStringAndPatter(startingPoint,'1');
                        notOperandFound = false;
                    }
                    else
                    {//Om det är falskt
                        if(notOperandFound)
                            updateStringAndPatter(startingPoint,'1');
                        else
                            updateStringAndPatter(startingPoint,'0');
                    }
                }
                else if(m.group(startingPoint+2).equals("|")) // OR
                {
                    if(m.group(startingPoint+1).equals("1") || m.group(startingPoint+3).equals("1"))
                    {
                        if(notOperandFound)
                            updateStringAndPatter(startingPoint,'0');
                        else
                            updateStringAndPatter(startingPoint,'1');
                        notOperandFound = false; 
                    }
                     else
                    {
                        if(notOperandFound)
                            updateStringAndPatter(startingPoint,'1');
                        else
                            updateStringAndPatter(startingPoint,'0');
                        notOperandFound = false;  
                    }
                }
                else if(m.group(startingPoint+2).equals("^")) // XOR
                {
                    if((m.group(startingPoint+1).equals("1") && m.group(startingPoint+3).equals("1")) 
                                || (m.group(startingPoint+1).equals("0") && m.group(startingPoint+3).equals("0")))
                    {
                        if(notOperandFound)
                            updateStringAndPatter(startingPoint,'1');
                        else
                            updateStringAndPatter(startingPoint,'0');
                        notOperandFound = false; 
                    }
                     else
                    {
                        if(notOperandFound)
                            updateStringAndPatter(startingPoint,'0');
                        else
                            updateStringAndPatter(startingPoint,'1');
                        notOperandFound = false;  
                    }
                }
                else// NOT
                {
                   notOperandFound = true;
                   notOperandRemoval(startingPoint);
                }
            }
            else
            {//Om man hittat ett ')' så tas allt som finns på sidorna om det sista tecknet borts och while loopen bryts
                closingBracketFound = true;
                inString = inString.substring(0,startingPoint-1)+inString.charAt(startingPoint)+inString.substring(startingPoint+2);
                String pattern = createPattern(inString.length());
                Pattern r = Pattern.compile(pattern);
                m = r.matcher(inString);
                m.find();
            }
        }        
    }
    public static void updateStringAndPatter(int startPunkt,char value)//Updaterar inString och Matcher m
    {
        inString = inString.substring(0,startPunkt)+value+inString.substring(startPunkt+3);
        String pattern = createPattern(inString.length());
        Pattern r = Pattern.compile(pattern);
        m = r.matcher(inString);
        m.find();
    }
    public static void notOperandRemoval(int startPunkt)//Updaterar inString och Matcher m tar bort !
    {
        inString = inString.substring(0,startPunkt)+inString.substring(startPunkt+1);
        String pattern = createPattern(inString.length());
        Pattern r = Pattern.compile(pattern);
        m = r.matcher(inString);
        m.find();
    }
    public static String createPattern(int patternLength)//Tar antal tecken i inString som inparameter returnerar pattern som används i matchern
    {
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < patternLength;i++)
        {
            sb.append("(.)");
        }
        
        return sb.toString();
    }
    
    public static String errorCheck(String checkString)
    {
        String errorCheckResult = null;
        
        if(checkString.indexOf("00")>0 || checkString.indexOf("11")>0 || checkString.indexOf("&&")>0 ||
                checkString.indexOf("^^")>0 || checkString.indexOf("||")>0 || checkString.startsWith("00") || 
                checkString.startsWith("11") || checkString.startsWith("&&") || checkString.startsWith("||") || checkString.startsWith("^^"))
        {
            errorCheckResult += "You have placed too many of something in a sequence\n";
        }
        //kollar om ^ ,|  eller & är felplacerade
        if(checkString.indexOf("(&")>0 || checkString.indexOf("&)")>0 || checkString.indexOf("(|")>0 || checkString.indexOf("|)")>0 || 
                checkString.indexOf("^(")>0 || checkString.indexOf("^)")>0)
        {
            errorCheckResult += "You have placed a \"|\" , \"&\" or a \"^\" before or after a bracket\n";
        }
        else if(checkString.indexOf("1(")>0 || checkString.indexOf("0(")>0 || checkString.indexOf(")1")>0 || 
                checkString.indexOf(")0")>0 || checkString.startsWith("1(") || checkString.startsWith("0("))
        {
            errorCheckResult += "You have placed a \"1\" or a \"0\" before or after a bracket\n";
        }
        //kollar om NOT(!) ligger rätt
        else if(checkString.indexOf("!^")>0 || checkString.indexOf("!&")>0 || checkString.indexOf("!|")>0 || checkString.indexOf("!)")>0)
        {
            errorCheckResult += "You have placed \"!\" in a wrong position";
        }
        //kollar om kalkylatorn startar med fel tecken
        else if(checkString.startsWith("|") || checkString.startsWith("&") || checkString.startsWith(")") || checkString.startsWith("^"))
        {
            errorCheckResult += "You have to start the calculation with a digit, \"(\" or a \"!\"";
        }
             
        return errorCheckResult;     
    }
}
