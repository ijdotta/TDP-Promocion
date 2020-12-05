import logica.PluginOperacion;

public class Division implements PluginOperacion {

	@Override
	public double calculate(double n1, double n2) throws ArithmeticException {
		if (n2 == 0)
			throw new ArithmeticException("ERROR. División por 0.");
		return n1 / n2;
	}

	@Override
	public String toString() {
		return "División";
	}

	@Override
	public boolean isUnary() {
		return false;
	}

}
