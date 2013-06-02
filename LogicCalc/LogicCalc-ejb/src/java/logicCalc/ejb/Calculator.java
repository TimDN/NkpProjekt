/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiccalc.ejb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tim
 */
public  class Calculator 
{
    private static Matcher m;
    private static String inString;
    
    public Calculator(String calcInString)
    {
        inString = calcInString;
        String pattern = createPattern(inString.length());
        Pattern r = Pattern.compile(pattern);
        m = r.matcher(inString);
        m.find();
       checkForBrackets(0); 
    }
    public static void checkForBrackets(int startingPoint)
    {
       int i = startingPoint+1;
       
       //Kollar om det finns ett ( anropar sig själv tills den inte hittar mer 
        while(i < m.groupCount())
        {
            if(m.group(i).equals("("))
            {                
               checkForBrackets(i);
            }
            i++;
        }
        //Utför logick beräkningar nån sorts while loops som körs tills den hittar ett ) eller EOF
        //Om man ökar i med 1 varje gång kan man plussa den på grupperna allltså startPunkt+2+i
        i = 0;
        boolean closingBracketFound = false;
        boolean notOperandFound = false;
        while(startingPoint+1 < m.groupCount() && closingBracketFound == false)
        {
            if(!m.group(startingPoint+2).equals(")"))
            {
                if(m.group(startingPoint+2).equals("&"))
                {
                    if(m.group(startingPoint+1).equals("1") && m.group(startingPoint+3).equals("1"))
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
                    }
                }
                else if(m.group(startingPoint+2).equals("|"))
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
                else if(m.group(startingPoint+2).equals("^"))
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
            {
                closingBracketFound = true;
                inString = inString.substring(0,startingPoint-1)+inString.charAt(startingPoint)+inString.substring(startingPoint+2);
                String pattern = createPattern(inString.length());
                Pattern r = Pattern.compile(pattern);
                m = r.matcher(inString);
                m.find();
            }
        }
        //Finns det inte en opernad som andra tecken måste det alltså finnas ett !0&1 före det vikitga den görs här efter som ()<&<|<!
        //hur det skall göras vet jag inte
        
    }
    public static void updateStringAndPatter(int startPunkt,char value)
    {
        inString = inString.substring(0,startPunkt)+value+inString.substring(startPunkt+3);
        String pattern = createPattern(inString.length());
        Pattern r = Pattern.compile(pattern);
        m = r.matcher(inString);
        m.find();
    }
    public static void notOperandRemoval(int startPunkt)
    {
        inString = inString.substring(0,startPunkt)+inString.substring(startPunkt+1);
        String pattern = createPattern(inString.length());
        Pattern r = Pattern.compile(pattern);
        m = r.matcher(inString);
        m.find();
    }
    public static String createPattern(int patternLength)
    {
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < patternLength;i++)
        {
            sb.append("(.)");
        }
        
        return sb.toString();
    }
}
