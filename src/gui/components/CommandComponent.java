package gui.components;

import gui.commands.Command;

public interface CommandComponent {
	
	/**
	 * Asocia un comando a la componente.
	 * @param command Comando asociado.
	 */
	public void setCommand(Command command);
	
	/**
	 * Env�a el mensaje de ejecuci�n al comando asociado.
	 */
	public void executeCommand();

}
