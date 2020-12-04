package logica;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import operaciones.PluginOperacion;

/**
 * Class OperacionesManager - Administra la carga de plugins de operaciones.
 * 
 * @author https://javaranch.com/journal/200607/Plugins.html
 * @author Ignacio Joaquín Dotta
 * 
 * El código del método loadOperaciones() está basado en código obtenido de la siguiente fuente:
 * https://javaranch.com/journal/200607/Plugins.html
 */
public class OperacionesManager {

	protected static Logger logger;

	protected List<PluginOperacion> operaciones;

	public OperacionesManager() {
		inicializarLogger();
		operaciones = new ArrayList<PluginOperacion>();
	}
	
	public void loadOperaciones() {
		//Permite determinar la ruta relativa al directorio de plugins según etapa desarrollo o despliegue.
		boolean export_to_jar = false;
		logger.info("Modo exportar a jar? " + export_to_jar);
		
		/*
		 * Por simpleza, remuevo todas las operaciones viejas para:
		 * 	1. Quitar posibles operaciones que el usuario eliminó del directorio "plugins"
		 * 	2. Evitar operaciones duplicadas
		 * Sé que puede hacerse sin el clear para evitar remover operaciones que deben conservarse,
		 * pero no considero relevante ese nivel de eficiencia en este proyecto. 
		 */
		logger.fine("Limpiando operaciones antiguas... ");
		operaciones.clear();
		
		String pluginsDir = export_to_jar? ".\\plugins" : "src\\plugins";
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
							logger.fine("Se cargó el plugin " + plugin.toString() + " con éxito. ");
						}
						else {
							logger.info("Class " + c.getName() + " no es un PluginOperacion. ");
						}
					}
				}
			}
			else {
				logger.warning("Error. No puede hallarse el directorio de plugins. ");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<PluginOperacion> getOperaciones() {
		return operaciones;
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

}
