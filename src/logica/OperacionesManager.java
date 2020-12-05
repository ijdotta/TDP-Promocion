package logica;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class OperacionesManager - Administra la carga de plugins.
 * 
 * @author Ignacio Joaqu�n Dotta
 * 
 */
public class OperacionesManager {

	protected static Logger logger;

	protected List<PluginOperacion> operaciones;

	/**
	 * Crea un administrador de operaciones.
	 */
	public OperacionesManager() {
		inicializarLogger();
		operaciones = new ArrayList<PluginOperacion>();
	}

	/**
	 * Carga las operaciones. Atenci�n: si se remueve alg�n archivo del directorio
	 * de plugins y se actualizan las operaciones, entonces dicha operaci�n ya no
	 * estar� disponible para el usuario.
	 */
	public void loadOperaciones() {
		// Permite determinar la ruta relativa al directorio de plugins seg�n etapa
		// desarrollo o despliegue.
		boolean export_to_jar = true;
		logger.info("Modo exportar a jar? " + export_to_jar);

		/*
		 * Por simpleza, remuevo todas las operaciones viejas para: 
		 * 	1. Quitar posibles operaciones que el usuario elimin� del directorio "plugins" 
		 * 	2. Evitar operaciones duplicadas S� que puede hacerse sin el clear para evitar remover
		 * 	   operaciones que deben conservarse, pero no considero relevante ese nivel de
		 *     eficiencia en este proyecto.
		 */
		logger.fine("Limpiando operaciones antiguas... ");
		operaciones.clear();

		String pluginsDir = export_to_jar ? ".\\plugins" : "src\\plugins";
		File dir = new File(System.getProperty("user.dir") + File.separator + pluginsDir);
		logger.info(System.getProperty("user.dir") + File.separator + pluginsDir);

		ClassLoader classLoader = new PluginClassLoader(dir, this.getClass().getClassLoader());

		try {

			if (dir.exists() && dir.isDirectory()) {
				String[] files = dir.list();
				
				for (String fileName : files) {
					if (fileName.endsWith(".class")) {

						String name = fileName.substring(0, fileName.lastIndexOf("."));
						logger.info("Cargando " + fileName);
						Class<?> c = classLoader.loadClass(name);

						if (PluginOperacion.class.isAssignableFrom(c)) {
							PluginOperacion plugin = (PluginOperacion) c.getConstructor().newInstance();
							operaciones.add(plugin);
							logger.fine("Se carg� el plugin " + plugin.toString() + " con �xito. ");
						} else {
							logger.info("Class " + c.getName() + " no es un PluginOperacion. ");
						}
					}
				}
			} else {
				logger.warning("No puede hallarse el directorio de plugins. ");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.warning("Error al buscar y cargar los plugins. ");
		}

	}

	public List<PluginOperacion> getOperaciones() {
		return operaciones;
	}

	/**
	 * Configura el logger de la clase.
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

}
