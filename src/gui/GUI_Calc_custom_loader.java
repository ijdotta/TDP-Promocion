package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.commands.CalculateCommand;
import gui.commands.Command;
import gui.commands.RefreshCommand;
import gui.components.CommandButton;
import gui.components.CommandComponent;
import gui.components.CommandTextField;
import logica.OperacionesManager;
import operaciones.PluginOperacion;

/**
 * Class GUI - Interfaz gráfica de la calculadora.
 * @author Ignacio Joaquín Dotta
 *
 */
public class GUI_Calc_custom_loader extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger;

	private OperacionesManager operacionesManager;

	private JPanel contentPane;
	protected CommandTextField txt_output;
	protected JComboBox<PluginOperacion> combo_operaciones;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Calc_custom_loader frame = new GUI_Calc_custom_loader();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI_Calc_custom_loader() {

		inicializarLogger();
		operacionesManager = new OperacionesManager();

		setTitle("CalculadoraSimple");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Colors
		Color btn_bg_color = Color.WHITE;

		// Dimensions
		int dflt_comp_height = 27;
		int dflt_panel_width = 300;
		Dimension btn_aux_dim = new Dimension(35, dflt_comp_height);
		Dimension combo_dim = new Dimension(200, dflt_comp_height);

		// Paneles
		JPanel panel_operacion = new JPanel();
		JPanel panel_io = new JPanel();
		JPanel panel_main = new JPanel();

		// Componentes
		CommandTextField txt_input_n1 = new CommandTextField();
		CommandTextField txt_input_n2 = new CommandTextField();
		CommandButton btn_calculate = new CommandButton();
		CommandButton btn_refresh = new CommandButton();
		combo_operaciones = new JComboBox<PluginOperacion>();
		txt_output = new CommandTextField();

		// Commands
		Command cmd_calculate = new CalculateCommand(this, txt_input_n1, txt_input_n2);
		Command cmd_refresh = new RefreshCommand(operacionesManager, combo_operaciones, txt_input_n2);

		// btn refresh
		btn_refresh.setCommand(cmd_refresh);
		btn_refresh.setBackground(btn_bg_color);
		btn_refresh.setPreferredSize(btn_aux_dim);
		btn_refresh.setIcon(getScaledImageIcon(GUI_Calc_custom_loader.class.getResource("/img/refresh_icon.png")));
		btn_refresh.addActionListener(new DefaultCommandComponentListener());

		// btn Calculate
		btn_calculate.setCommand(cmd_calculate);
		btn_calculate.setText("CALC");
		btn_calculate.setBackground(btn_bg_color);
		btn_calculate.addActionListener(new DefaultCommandComponentListener());

		// combobox
		cmd_refresh.execute();
		combo_operaciones.setBackground(btn_bg_color);
		combo_operaciones.setPreferredSize(combo_dim);
		combo_operaciones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PluginOperacion operacion = (PluginOperacion) combo_operaciones.getSelectedItem();
				if (operacion.isUnary()) {
					txt_input_n2.setText("");
					txt_input_n2.setEnabled(false);
				} else {
					txt_input_n2.setEnabled(true);
				}
			}
		});

		// text fields
		txt_input_n1.setCommand(cmd_calculate);
		txt_input_n2.setCommand(cmd_calculate);
		txt_output.setEditable(false);

		// Panel operacion
		panel_operacion = new JPanel();
		panel_operacion.setLayout(new BorderLayout());
		panel_operacion.add(btn_refresh, BorderLayout.WEST);
		panel_operacion.add(combo_operaciones, BorderLayout.CENTER);
		panel_operacion.setPreferredSize(new Dimension(dflt_panel_width, dflt_comp_height));

		// Panel io
		panel_io.setLayout(new GridLayout(3, 1));
		panel_io.add(txt_input_n1);
		panel_io.add(txt_input_n2);
		panel_io.add(txt_output);
		panel_io.setPreferredSize(
				new Dimension(dflt_panel_width, ((GridLayout) panel_io.getLayout()).getRows() * dflt_comp_height));

		// Panel ppal
		panel_main.setLayout(new BorderLayout());
		panel_main.add(panel_operacion, BorderLayout.NORTH);
		panel_main.add(panel_io, BorderLayout.CENTER);
		panel_main.add(btn_calculate, BorderLayout.SOUTH);
		contentPane.add(panel_main);

		txt_input_n1.addKeyListener(new DefaultKeyListener());
		txt_input_n2.addKeyListener(new DefaultKeyListener());

		setBounds(400, 200, dflt_panel_width + 25, dflt_comp_height * 5 + 55);

	}

	public void setResult(double result) {
		if (Double.isNaN(result)) {
			txt_output.setText("ERROR");
		} else {
			txt_output.setText(Double.toString(result));
		}
	}

	public PluginOperacion getOperacion() {
		return (PluginOperacion) combo_operaciones.getSelectedItem();
	}

	/**
	 * Configura el logger de la GUI.
	 */
	private void inicializarLogger() {
		if (logger == null) {

			logger = Logger.getLogger(this.getClass().getName());

			Handler hnd = new ConsoleHandler();
			hnd.setLevel(Level.ALL);
			logger.addHandler(hnd);

			logger.setLevel(Level.ALL);

			Logger rootLoger = logger.getParent();
			for (Handler h : rootLoger.getHandlers())
				h.setLevel(Level.OFF);
		}
	}

	class DefaultCommandComponentListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CommandComponent source = (CommandComponent) e.getSource();
			source.executeCommand();
		}

	}

	class DefaultKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				CommandComponent source = (CommandComponent) e.getSource();
				source.executeCommand();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}

	private ImageIcon getScaledImageIcon(URL url) {
		ImageIcon icon = new ImageIcon(url);
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		icon.setImage(newImg);
		return icon;
	}

	public void enableOperationInputFields(PluginOperacion operacion) {

	}

}
