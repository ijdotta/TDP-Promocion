import logica.PluginOperacion;

public class Resta implements PluginOperacion {

	@Override
	public double calculate(double n1, double n2) {
		return n1 - n2;
	}

	@Override
	public String toString() {
		return "Resta";
	}

	@Override
	public boolean isUnary() {
		return false;
	}

}
