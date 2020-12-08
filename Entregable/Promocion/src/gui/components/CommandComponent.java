package gui.components;

import gui.commands.Command;

public interface CommandComponent {
	
	/**
	 * Asocia un comando a la componente.
	 * @param command Comando asociado.
	 */
	public void setCommand(Command command);
	
	/**
	 * Envía el mensaje de ejecución al comando asociado.
	 */
	public void executeCommand();

}
