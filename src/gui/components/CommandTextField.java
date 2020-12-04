package gui.components;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import gui.commands.Command;

public class CommandTextField extends JFormattedTextField implements CommandComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Command command;

	/**
	 * TODO VER FORMATTERS
	 * @param nf
	 */
	public CommandTextField(NumberFormat nf) {
		super(nf);
	}

	//TODO FORMATTERS
	public CommandTextField(MaskFormatter mf) {
		super(mf);
	}

	public CommandTextField() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setCommand(Command command) {
		this.command = command;
	}

	@Override
	public void executeCommand() {
		command.execute();
	}
	

}
