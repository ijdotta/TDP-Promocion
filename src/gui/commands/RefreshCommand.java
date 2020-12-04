package gui.commands;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import gui.components.CommandTextField;
import logica.OperacionesManager;
import operaciones.PluginOperacion;

public class RefreshCommand implements Command {

	protected OperacionesManager operacionesManager;
	protected JComboBox<PluginOperacion> combo_operaciones;
	protected CommandTextField txt_input_n2;


	public RefreshCommand(OperacionesManager operacionesManager, JComboBox<PluginOperacion> combo_operaciones,
			CommandTextField txt_input_n2) {
		this.operacionesManager = operacionesManager;
		this.combo_operaciones = combo_operaciones;
		this.txt_input_n2 = txt_input_n2;
	}

	@Override
	public void execute() {
		operacionesManager.loadOperaciones();
		List<PluginOperacion> operaciones = operacionesManager.getOperaciones();
		combo_operaciones.setModel(getComboBoxModel(operaciones));
		
		PluginOperacion selected_operacion = (PluginOperacion) combo_operaciones.getSelectedItem();
		if (selected_operacion != null && selected_operacion.isUnary()) {
			txt_input_n2.setText("");
			txt_input_n2.setEnabled(false);
		}
		else {
			txt_input_n2.setEnabled(true);
		}
	}

	private DefaultComboBoxModel<PluginOperacion> getComboBoxModel(List<PluginOperacion> operaciones) {
		PluginOperacion[] comboBoxModel = operaciones.toArray(new PluginOperacion[0]);
		return new DefaultComboBoxModel<>(comboBoxModel);
	}

}
