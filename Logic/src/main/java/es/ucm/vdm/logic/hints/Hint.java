package es.ucm.vdm.logic.hints;

import java.util.ArrayList;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.engine.GameObject;
import es.ucm.vdm.logic.engine.Position;

/**
 * Clase es un gameObject y contiene la fuente con la que se va a pintar el texto,
 * las coordenadas de la casilla donde se aplica la pista y se es visible o no
 */
public abstract class Hint extends GameObject {

    protected static final Position[] directions = new Position[]{
            new Position(0, 1), //down
            new Position(0, -1),//up
            new Position(1, 0), //right
            new Position(-1, 0) //left
    };

    // atributo que usarán las pistas para devolver la posicion en executeHint()
    protected static Position _pointToReturn = new Position(0, 0);

    public String _text;
    private Font _font;
    private boolean _visible = false;

    private int _indexTileX;
    private int _indexTileY;


    Hint(String text, Font font) {
        _text = text;
        _font = font;
    }


    /**
     * @return Devuelve la posicion de la casilla que hay que cambiar para que se resuelva la pista.
     * null significa que no hay pista en esa casilla
     */
    public abstract Position executeHint(int x, int y, ArrayList<Tile> board);


    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Graphics g) {
        if (g.save() && _visible) {
            g.translate(_position.x, _position.y);

            g.setColor(0xFF000000);
            g.setFont(_font);

            int i = 0, initChar = 0, line = 1;

            while (i < _text.length()) {
                if (_text.charAt(i) == '\n' || i == _text.length() - 1) {
                    String s = _text.substring(initChar, i + 1);
                    int w = (int) (g.getWidth() - g.getWidthText(s)) / 2;
                    g.drawText(s, w, line * 30);
                    initChar += i;
                    line++;
                }
                i++;
            }
        }
        g.restore();
    }


    /**
     * Hace visible el texto de la pista o no, segun si le pasas true o false
     */
    public void showText(boolean b) {
        _visible = b;
    }

    /**
     * Asigna la casilla sobre la que se aplica la pista
     */
    public void setIndexTile(int indexTileX, int indexTileY) {
        _indexTileX = indexTileX;
        _indexTileY = indexTileY;
    }

    public int getIndexTileX() {
        return _indexTileX;
    }

    public int getIndexTileY() {
        return _indexTileY;
    }

    // METODOS AUXILIARES COMUNES A ALGUNAS PISTAS Y QUE SON DE UTILIDAD

    // Devuelve FULL si una casilla azul ve las VALOR casillas que tiene que ver
    // Devuelve EXCEEDED si ves demasiadas casillas azules
    // Devuelve NOTENOUGH si no ve suficientes
    public static int blueVisibles(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        int countVisibles = 0;

        int currentX, currentY;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (Position direction : directions) {
            currentX = x;
            currentY = y;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (currentY + direction.y < dimension && currentY + direction.y >= 0 &&
                    currentX + direction.x < dimension && currentX + direction.x >= 0 &&
                    board.get((dimension * (currentY + direction.y)) + currentX + direction.x).getState() == Tile.State.DOT) {
                currentY += direction.y;
                currentX += direction.x;
                countVisibles++;
            }
        }

        return countVisibles;
    }

    ///Metodo auxiliar para pista 6 y 7
    protected static boolean aisled(int x, int y, ArrayList<Tile> board) {
        //No esta contemplado que la casilla proporcionada este fuera del tablero
        int dimension = (int) Math.sqrt(board.size());

        int posible = 0;
        int walls = 0;

        for (Position d : directions) {
            if (y + d.y < dimension && y + d.y >= 0 &&
                    x + d.x < dimension && x + d.x >= 0) {
                posible++;
                Tile t = board.get((dimension * (y + d.y)) + x + d.x);
                if (t.getState() == Tile.State.WALL)
                    walls++;
            }
        }

        //Si todas las casillas cercanas que estan dentro del tablero son muros
        //se devuelve true, si no, es que NO esta aislada
        return posible == walls;
    }

    //Metodo auxiliar para pistas 9 y 10
    //Cuenta azules y vacias alcanzables desde la posicion x,y
    protected static int onSight(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        int fullOnSight = 0;
        int currentX;
        int currentY;

        for (Position direction : directions) {
            currentX = x;
            currentY = y;

            while (currentY + direction.y < dimension && currentX + direction.x < dimension
                    && currentY + direction.y >= 0 && currentX + direction.x >= 0
                    && board.get((dimension * (currentY + direction.y)) + currentX + direction.x).getState() != Tile.State.WALL) {
                fullOnSight++;
                currentY += direction.y;
                currentX += direction.x;
            }
        }
        return fullOnSight;
    }

}
