package gui.components;

import javax.swing.JTextField;

import gui.commands.Command;

public class CommandTextField extends JTextField implements CommandComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Command command;

	@Override
	public void setCommand(Command command) {
		this.command = command;
	}

	@Override
	public void executeCommand() {
		command.execute();
	}
	

}
