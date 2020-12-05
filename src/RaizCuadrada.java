import logica.PluginOperacion;

public class RaizCuadrada implements PluginOperacion {

	@Override
	public double calculate(double n1, double n2) {
		return Math.sqrt(n1);
	}

	@Override
	public boolean isUnary() {
		return true;
	}
	
	@Override
	public String toString() {
		return "Raíz cuadrada";
	}

}
