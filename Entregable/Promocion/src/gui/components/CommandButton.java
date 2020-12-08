package gui.components;

import javax.swing.JButton;

import gui.commands.Command;

public class CommandButton extends JButton implements CommandComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Command command;
	
	public CommandButton() {
		super();
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
