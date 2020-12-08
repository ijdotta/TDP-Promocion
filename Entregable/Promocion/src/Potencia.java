import logica.PluginOperacion;

public class Potencia implements PluginOperacion {

	@Override
	public double calculate(double n1, double n2) {
		return Math.pow(n1, n2);
	}

	@Override
	public String toString() {
		return "Potencia";
	}

	@Override
	public boolean isUnary() {
		return false;
	}

}
