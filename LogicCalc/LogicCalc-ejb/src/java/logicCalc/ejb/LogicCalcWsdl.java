/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logicCalc.ejb;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import logiccalc.ejb.LogicCalc;

/**
 *
 * @author Tim
 */
@WebService(serviceName = "LogicCalcWsdl")
@Stateless()
public class LogicCalcWsdl {
    @EJB
    private LogicCalc ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "calculate")
    public String calculate(@WebParam(name = "inString") String inString) {
        return ejbRef.calculate(inString);
    }

    @WebMethod(operationName = "finalErrorCheck")
    public String finalErrorCheck(@WebParam(name = "checkString") String checkString) {
        return ejbRef.finalErrorCheck(checkString);
    }

    @WebMethod(operationName = "errorCheck")
    public String errorCheck(@WebParam(name = "checkString") String checkString) {
        return ejbRef.errorCheck(checkString);
    }
    
}
