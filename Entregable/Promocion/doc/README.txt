El presente documento es una copia del README de un repositorio en GitHub y se sugiere consultarlo allí por el formato.
https://github.com/ijdotta/TDP-Promocion (puede accederse al mismo enlace con el acceso directo incluido en esta carpeta).


# CalculadoraSimple
Proyecto de promoción de la materia Tecnología de Programación, DCIC - UNS. 


## Convenciones para el uso de plugins personalizados :handshake:
* Todo plugin debe implementar la siguiente interface:
```
package logica;
public interface PluginOperacion {

  /**Calcula el resultado*/
	public double calculate(double n1, double n2);
  
  /**Determina si usa 1 o 2 operandos*/
	public boolean isUnary();
  
  /**Nombre de la operación*/
	public String toString();

}
```
* La interface debe estar dentro de un package ```logica``` , i.e. su nombre será ```logica.PluginOperacion```
* Se asume que los plugins se desarrollan dentro del ```default package```
* Si no se respetan las mencionadas convenciones, la carga del plugin fallará.


## Observaciones :eyes:

### Sobre el ```ClassLoader``` :thinking:
En un primer momento intenté reutilizar la clase PluginClassLoader que obtuve de [Adding Plugins to a Java Application](https://javaranch.com/journal/200607/Plugins.html).
Todo funcionaba bien dentro del IDE Eclipse, pero fallaba cuando lo exportaba a jar. 
El problema principal era la excepción:
```
java.lang.ClassCastException: class operaciones.Suma cannot be cast to class operaciones.PluginOperacion (operaciones.Suma is in unnamed module of loader logica.PluginClassLoader @daf4db4; operaciones.PluginOperacion is in unnamed module of loader java.net.URLClassLoader @6576fe71)
        at logica.OperacionesManager.loadOperaciones(OperacionesManager.java:75)
        at gui.commands.RefreshCommand.execute(RefreshCommand.java:28)
        at gui.GUI_CalcSimple.<init>(GUI_CalcSimple.java:124)
        at gui.GUI_CalcSimple$1.run(GUI_CalcSimple.java:60)
```
Interpreté que el problema estaba en los diferentes classLoaders que Java utiliza para cargar las clases (y que además las clases no se distinguen solo por su nombre completo, sino también por su classLoader).
En resumen, la interface ```PluginOperacion``` era cargada por un classLoader del sistema y los plugins por el ```PluginClassLoader``` personalizado. Entonces java no reconocía a los plugins como subclases de ```PluginOperacion```.
Como los ```classLoader``` delegan primero en sus padres, la solución (basada en una de las fuentes de más abajo) era definir al padre de ```PluginClassLoader``` con el mismo de la app principal y no redefinir el
método ```loadClass``` sino el método ```findClass```.

Asímismo, para enteder la dinámica de los plugins de la fuente mencionada anteriormente, me encargué de exportar el código proveído a ```.jar``` pero **nunca funcionó**.

### Sobre las operaciones :nerd_face: 
Cabe destacar que:
* La primera solución funcionaba perfectamente cuando se dejaban los archivos ```.java``` de las operaciones dentro del ```.jar``` porque así prescindía del uso del método
```loadClass```. En otras palabras, si se dejan los archivos ```.java``` de las operaciones dentro del ```.jar``` y no se añaden operaciones personalizadas posteriormente, entonces podría parecer equivocadamente que la app funciona.


## Fuentes :speaking_head:
Todo el código del proyecto es de mi autoría pero está basado en información de las siguientes fuentes:
* Una respuesta a mi consulta [Java ClassLoaders - Cast class to an interface](https://stackoverflow.com/questions/65100898/java-classloaders-cast-class-to-an-interface/65101060#65101060) en StackOverFlow por [rzwitserloot](https://stackoverflow.com/users/768644/rzwitserloot)
* El artículo [Adding Plugins to a Java Application](https://javaranch.com/journal/200607/Plugins.html) de [Ulf Dittmer](http://www.javaranch.com/contact.jsp#UlfDittmer)