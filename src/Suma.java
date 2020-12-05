import logica.PluginOperacion;

public class Suma implements PluginOperacion {

	@Override
	public double calculate(double n1, double n2) {
		return n1 + n2;
	}

	@Override
	public String toString() {
		return "Suma";
	}

	@Override
	public boolean isUnary() {
		return false;
	}

}
