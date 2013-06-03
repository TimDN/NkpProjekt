/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logiccalcclient;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Tim
 */
public class LogicCalcClient extends JFrame 
{
    public JTextField jTextFCalcWindow = new JTextField(35);
    public JButton jButtonDeleteLatest = new JButton("<-");
    public JButton jButtonClear = new JButton("CLR");
    public JButton jButtonSend = new JButton("SEND");
    public JButton jButton1 = new JButton("1");
    public JButton jButton0 = new JButton("0");
    public JButton jButtonNOT = new JButton("NOT");
    public JButton jButtonOR = new JButton("OR");
    public JButton jButtonAND = new JButton("AND");
    public JButton jButtonXOR = new JButton("XOR");
    public JButton jButtonLeftPar = new JButton("(");
    public JButton jButtonRightPar = new JButton(")");
    
    public LogicCalcClient()
    {
        setLayout(new BorderLayout());
        add(topPanel(), BorderLayout.NORTH);
        add(centerPanel(), BorderLayout.CENTER);

        setTitle("Tim och Tungs Awesome Webservice klient grej!!");
        setSize(400,300);
        setLocation(200,200);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    protected JComponent centerPanel()
    {
        JPanel inner = new JPanel();
        JLabel label;
        int fontSize = 18;
	inner.setLayout(new GridLayout(4,3,8,8));
        jButtonDeleteLatest.addActionListener(aListener);
        jButtonDeleteLatest.setFont(new Font("Serif", Font.PLAIN, fontSize));
        inner.add(jButtonDeleteLatest);
        jButtonClear.addActionListener(aListener);
        jButtonClear.setFont(new Font("Serif", Font.PLAIN, fontSize));
        inner.add(jButtonClear);
        jButtonSend.addActionListener(aListener);
        jButtonSend.setFont(new Font("Serif", Font.PLAIN, fontSize));
        inner.add(jButtonSend);
        jButton1.addActionListener(aListener);
        jButton1.setFont(new Font("Serif", Font.PLAIN, fontSize));
        inner.add(jButton1);
        jButton0.addActionListener(aListener);
        jButton0.setFont(new Font("Serif", Font.PLAIN, fontSize));
        inner.add(jButton0);
        jButtonNOT.addActionListener(aListener);
        jButtonNOT.setFont(new Font("Serif", Font.PLAIN, fontSize));
        inner.add(jButtonNOT);
        jButtonOR.addActionListener(aListener);
        jButtonOR.setFont(new Font("Serif", Font.PLAIN, fontSize));
        inner.add(jButtonOR);
        jButtonAND.addActionListener(aListener);
        jButtonAND.setFont(new Font("Serif", Font.PLAIN, fontSize));
        inner.add(jButtonAND);
        jButtonXOR.addActionListener(aListener);
        jButtonXOR.setFont(new Font("Serif", Font.PLAIN, fontSize));
        inner.add(jButtonXOR);
        jButtonLeftPar.addActionListener(aListener);
        jButtonXOR.setFont(new Font("Serif", Font.PLAIN, fontSize));
        inner.add(jButtonLeftPar);
        jButtonRightPar.addActionListener(aListener);
        jButtonXOR.setFont(new Font("Serif", Font.PLAIN, fontSize));
        inner.add(jButtonRightPar);
        inner.add(label = new JLabel(""));
        return inner;
    }
    protected JComponent topPanel()
    {
        JPanel inner = new JPanel();
        jTextFCalcWindow.setFont(new Font("Serif", Font.PLAIN, 16));
        inner.add(jTextFCalcWindow);
        return inner;
    }
    ActionListener aListener = new ActionListener() 
    {
      public void actionPerformed(ActionEvent event) 
      {
        JButton button = (JButton)event.getSource();
        if(button == jButtonSend)
        {
            //jTextFCalcWindow.setText(calculate(jTextFCalcWindow.getText()));
            
        }
        if(button == jButtonClear)
        {
            jTextFCalcWindow.setText("");
        }
        if(button == jButtonDeleteLatest)
        {
            String tmp = jTextFCalcWindow.getText();
            tmp = tmp.substring(0, tmp.length()-1);
            jTextFCalcWindow.setText(tmp);
        }
        if(button == jButton1)
        {
           String tmp = jTextFCalcWindow.getText();
            jTextFCalcWindow.setText(tmp+"1"); 
        }
        if(button == jButton0)
        {
           String tmp = jTextFCalcWindow.getText();
            jTextFCalcWindow.setText(tmp+"0"); 
        }
        if(button == jButtonNOT)
        {
            String tmp = jTextFCalcWindow.getText();
            jTextFCalcWindow.setText(tmp+"!"); 
        }
        if(button == jButtonOR)
        {
            String tmp = jTextFCalcWindow.getText();
            jTextFCalcWindow.setText(tmp+"|"); 
        }
        if(button == jButtonAND)
        {
            String tmp = jTextFCalcWindow.getText();
            jTextFCalcWindow.setText(tmp+"&"); 
        }
        if(button == jButtonLeftPar)
        {
            String tmp = jTextFCalcWindow.getText();
            jTextFCalcWindow.setText(tmp+"("); 
        }
        if(button == jButtonRightPar)
        {
            String tmp = jTextFCalcWindow.getText();
            jTextFCalcWindow.setText(tmp+")"); 
        }
      }
    };
    public static void main(String[] args) {
        new LogicCalcClient();
    }

    private static String calculate(java.lang.String inString) {
        logiccalc.ejb.LogicCalcWsdl_Service service = new logiccalc.ejb.LogicCalcWsdl_Service();
        logiccalc.ejb.LogicCalcWsdl port = service.getLogicCalcWsdlPort();
        return port.calculate(inString);
    }

    private static String errorCheck(java.lang.String checkString) {
        logiccalc.ejb.LogicCalcWsdl_Service service = new logiccalc.ejb.LogicCalcWsdl_Service();
        logiccalc.ejb.LogicCalcWsdl port = service.getLogicCalcWsdlPort();
        return port.errorCheck(checkString);
    }
}
