//
//	Name:			Haddox, Anthony
//	Project:		2
//	Due:			10/30/2014
//	Course:			CS-245-01-w14
//
//	Description:	A simple integer calculator

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator implements ActionListener{
	private JLabel calculatorDisplay;
	private int leftOperand;
	private int total;
	private char currentOperator;
	private boolean error;
	private boolean firstTimeFlag;
	
	
	public Calculator()	{
		JFrame mainFrame = new JFrame("Calcualtor");
		leftOperand = 0;
		total = 0;
		currentOperator = '\0';
		firstTimeFlag = true;
		error = false;
		
		//2x2 GridLayout to be further divided by JPanes
		//Top row of grid is the calculatorDisplay
		//Bottom row of Grid will contain the buttons
		mainFrame.getContentPane().setLayout(new GridLayout(2, 1, 1, 1));
		mainFrame.setSize(185, 225);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		calculatorDisplay = new JLabel("0", JLabel.RIGHT);
		mainFrame.add(calculatorDisplay);
		
		JPanel mainButtonPanel = new JPanel();
		mainButtonPanel.setLayout(new GridLayout(4, 4, 1, 1));
		
		
		JButton numberOneButton = new JButton("1");		numberOneButton.addActionListener(this);
		JButton numberTwoButton = new JButton("2"); 	numberTwoButton.addActionListener(this);
		JButton numberThreeButton = new JButton("3");	numberThreeButton.addActionListener(this);
		JButton numberFourButton = new JButton("4");	numberFourButton.addActionListener(this);
		JButton numberFiveButton = new JButton("5");	numberFiveButton.addActionListener(this);
		JButton numberSixButton = new JButton("6");		numberSixButton.addActionListener(this);
		JButton numberSevenButton = new JButton("7");	numberSevenButton.addActionListener(this);
		JButton numberEightButton = new JButton("8");	numberEightButton.addActionListener(this);
		JButton numberNineButton = new JButton("9");	numberNineButton.addActionListener(this);
		JButton numberZeroButton = new JButton("0");	numberZeroButton.addActionListener(this);
		
		JButton plusButton = new JButton("+");			plusButton.addActionListener(this);
		JButton minusButton = new JButton("-");			minusButton.addActionListener(this);
		JButton multiplyButton = new JButton("*");		multiplyButton.addActionListener(this);
		JButton divideButton = new JButton("/");		divideButton.addActionListener(this);
		JButton equalsButton = new JButton("=");		equalsButton.addActionListener(this);
		JButton clearButton = new JButton("<HTML><U>C</U></HMTL>");			clearButton.addActionListener(this);
		clearButton.setActionCommand("C");
		
		
		mainButtonPanel.add(numberSevenButton);
		mainButtonPanel.add(numberEightButton);
		mainButtonPanel.add(numberNineButton);
		mainButtonPanel.add(divideButton);
		
		mainButtonPanel.add(numberFourButton);
		mainButtonPanel.add(numberFiveButton);
		mainButtonPanel.add(numberSixButton);
		mainButtonPanel.add(multiplyButton);
		
		mainButtonPanel.add(numberOneButton);
		mainButtonPanel.add(numberTwoButton);
		mainButtonPanel.add(numberThreeButton);
		mainButtonPanel.add(minusButton);
		
		mainButtonPanel.add(numberZeroButton);
		mainButtonPanel.add(clearButton);
		mainButtonPanel.add(equalsButton);
		mainButtonPanel.add(plusButton);
		
		mainFrame.getRootPane().setDefaultButton(equalsButton);
		
		mainFrame.add(mainButtonPanel);
		mainFrame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)	{
		try
		{
			numberHandler(Integer.parseInt(ae.getActionCommand()));
		}
		catch(NumberFormatException e)
		{
			operationHandler(ae.getActionCommand(), ae);
		}
	}
	
	public void numberHandler(int num)	{
		if(error)
			return;
		if(isFull())
			return;
		if(num == 0 && calculatorDisplay.getText().equals("0"))
			return;
		if(firstTimeFlag)	{
			calculatorDisplay.setText("" + num);
			firstTimeFlag = false;
		}
		else if(calculatorDisplay.getText().equals("0"))	{
			calculatorDisplay.setText("" + num);
			return;
		}	
		else
			calculatorDisplay.setText(calculatorDisplay.getText() + num);
				
	}
	
	public void operationHandler(String op, ActionEvent ae)	{
		//Characters in case of older Java versions
		switch(op.charAt(0))
		{
		case '\0':
			break;
		case 'C':
			//18 is button press + CTRL_MASK constant
			if(ae.getModifiers() == 18)	{
				if(error)
					return;
				calculatorDisplay.setText("(c) 2014 Anthony Haddox");
			}
			else	{
				leftOperand = 0;
				total = 0;
				error = false;
				firstTimeFlag = true;
				currentOperator = '\0';
				calculatorDisplay.setText("0");
			}
			break;
		case '+':
			if(error)
				break;
			leftOperand += Integer.parseInt(calculatorDisplay.getText());
			currentOperator = '+';
			firstTimeFlag = true;
			break;
		case '-':
			if(error)
				break;
			if(currentOperator == '\0')
				leftOperand = Integer.parseInt(calculatorDisplay.getText());
			else
				leftOperand -= Integer.parseInt(calculatorDisplay.getText());
			currentOperator = '-';
			firstTimeFlag = true;
			break;
		case '*':
			if(error)
				break;
			if(currentOperator == '\0')
				leftOperand = Integer.parseInt(calculatorDisplay.getText());
			else
				leftOperand *= Integer.parseInt(calculatorDisplay.getText());
			currentOperator = '*';
			firstTimeFlag = true;
			break;
		case '/':
			if(error)
				break;
			if(currentOperator == '\0')
				leftOperand = Integer.parseInt(calculatorDisplay.getText());
			else	{
				try
				{
					leftOperand /= Integer.parseInt(calculatorDisplay.getText());
				}
				catch(ArithmeticException e)
				{
					//Handeled in "case ="
				}
			}
			currentOperator = '/';
			firstTimeFlag = true;
			break;
		case '=':
			if(error)
				return;
			operationHandler("" + currentOperator, ae);
			if(calculatorDisplay.getText().equals("0"))	{
				error = true;
				calculatorDisplay.setText("DIVIDE BY ZERO ERROR");
			}
			else	{
				currentOperator = '\0';
				total = leftOperand;
				
				String testAnswer = "" + total;
				if(testAnswer.length() > 9)	{
					error = true;
					calculatorDisplay.setText("NUMBER OVERFLOW");
					return;
				}
				
				calculatorDisplay.setText("" + total);
				total = 0;
				leftOperand = 0;
				firstTimeFlag = true;
			}
			break;
		}
	}
	public boolean isFull()	{
		return (calculatorDisplay.getText().length() >= 9);
	}
	
	public static void main(String[] args)	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()	{
				new Calculator();
			}
		});
		
	}
}
