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

    public String calculate(String inString)
    {
        calc = new Calculator(inString);
        
        String outString = calc.startCalc();
        return outString;
    }
    public String errorCheck(String inString)
    {
        String outString = this.calculate(inString);
        return outString;
    }
}
