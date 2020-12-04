package gui.components;

import gui.commands.Command;

public interface CommandComponent {
	
	/**
	 * Establece el comando asociado a la componente.
	 * @param command Comando asociado.
	 */
	public void setCommand(Command command);
	
	/**
	 * Ejecuta el comando asociado.
	 */
	public void executeCommand();

}
