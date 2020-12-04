package gui.commands;

import gui.GUI_Calc_custom_loader;
import gui.components.CommandTextField;
import operaciones.PluginOperacion;

public class CalculateCommand implements Command {

	private GUI_Calc_custom_loader gui;
	private CommandTextField txt_input_n1;
	private CommandTextField txt_input_n2;

	public CalculateCommand(GUI_Calc_custom_loader gui, CommandTextField txt_input_n1, CommandTextField txt_input_n2) {

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
			
//			TODO if (!operacion.isUnary())
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
