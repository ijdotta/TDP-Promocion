package logica;

public interface PluginOperacion {

	/**
	 * Computa la operación
	 * @param n1 Primer operando
	 * @param n2 Segundo operando
	 * @return resultado de operar n1 y n2
	 */
	public double calculate(double n1, double n2);

	/**
	 * @return true si la operación es unaria.
	 */
	public boolean isUnary();

	/**
	 * @return nombre que representa a la operación.
	 */
	public String toString();

}
