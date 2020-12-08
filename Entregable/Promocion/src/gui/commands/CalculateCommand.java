package gui.commands;

import gui.Main_CalculadoraSimple;
import gui.components.CommandTextField;
import logica.PluginOperacion;

/**
 * Command Calculate - Hace de intermediario en la obtenci�n de un resultado dada una operaci�n.
 * @author Ignacio Joaqu�n Dotta
 *
 */
public class CalculateCommand implements Command {

	private Main_CalculadoraSimple gui;
	private CommandTextField txt_input_n1;
	private CommandTextField txt_input_n2;

	public CalculateCommand(Main_CalculadoraSimple gui, CommandTextField txt_input_n1, CommandTextField txt_input_n2) {

		this.gui = gui;
		this.txt_input_n1 = txt_input_n1;
		this.txt_input_n2 = txt_input_n2;

	}

	@Override
	public void execute() {
		PluginOperacion operacion = gui.getOperacion();
		double n1, n2, res;
		
		try {
			
			n1 = Double.parseDouble(txt_input_n1.getText());
			
			if (!operacion.isUnary()) {
				n2 = Double.parseDouble(txt_input_n2.getText());
			}
			else {
				n2 = 0;
			}
			
			res = operacion.calculate(n1, n2);
			
		} catch (ArithmeticException | NumberFormatException e) {
			res = Double.NaN;
		}

		gui.setResult(res);
	}

}
