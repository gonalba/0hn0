package es.ucm.ohno.engine.engineinterface;
/**Clase que proporciona las funcionalidades graficas minimas sobre la ventana de la aplicacion*/
public interface Graphics {
    /**Carga una imagen almacenada en el contenedor de recursos de la aplicacion a partir de su nombre*/
    Image newImage(String name);

    /**Crea una nueva fuente del tamaño especificado a partir de un fichero .ttf.
     * Se indica si se desea o no fuente en negrita.*/
    Font newFont(String filename, int size, boolean isBold);

    /**Borra el contenido completo de la ventana, rellenando un color recibido como parámetro*/
    void clear(int color);

    /**Traslada la matriz de transformacion*/
    void translate(int x, int y);

    /**Escala la matriz de transformacion*/
    void scale(int x, int y);

    /**Salva el estado de la matriz de transformacion*/
    void save();

    /**Reestablece al estado anterior de la matriz de transformacion*/
    void restore();

    /**recibe una imagen y la muestra en la pantalla. Se pueden necesitar diferentes versiones
     * de este método dependiendo de si se permite o no escalar la imagen, si se permite elegir
     * qué porción de la imagen original se muestra*/
    void drawImage(Image image);

    /**Establece el coloe a utilizar en las operaciones de dibujado*/
    void setColor(int color);

    /**Dibuja un circulo relleno del color activo*/
    void fillCircle(int cx, int cy, int r);

    /**Escribe el texto pasado por parametro con el color y la fuente activas*/
    void drawText(String text, int x, int y);

    /**Devuelve el ancho de la pantalla*/
    int getHeight();

    /**Devuelve el alto de la pantalla*/
    int getWidth();
}
