/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiccalc.ejb;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Tim
 */
@Stateless
@LocalBean
public class LogicCalc {
    
public static Calculator calc;
    //Anropar kalkylatorn svaret från den är en sträng med svaret
    public String calculate(String inString)
    {
        String outString = new String();
        String errors = null;
        
        errors = finalErrorCheck(inString);
        if(errors == null)
        {
            calc = new Calculator(inString);
            outString = calc.startCalc();
        }
        else
        {
          outString = errors;  
        }
        
        return outString;
    }
    //Kollar igenom hela strängen efter fel
    public String finalErrorCheck(String checkString)
    {
        String errorCheckResult = null;
        
        for(int i=0 ; i<(checkString.length()-1) ; i++)
        {
            if(checkString.charAt(i) == checkString.charAt(i+1))
            {
                errorCheckResult += "You have placed a \"1\" / \"0\" / \"|\" / \"&\" after each other\n";
            }
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
    public String errorCheck(String checkString)
    {
        return "";
    }
}
