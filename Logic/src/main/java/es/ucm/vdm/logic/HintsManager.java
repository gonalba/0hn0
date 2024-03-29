package es.ucm.vdm.logic;

import java.util.ArrayList;
import java.util.Random;

import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.logic.engine.Position;
import es.ucm.vdm.logic.hints.AisledBlue;
import es.ucm.vdm.logic.hints.AisledIdle;
import es.ucm.vdm.logic.hints.ForceBlue;
import es.ucm.vdm.logic.hints.ForcedBlueSolved;
import es.ucm.vdm.logic.hints.ForcedBlueUniqueDirection;
import es.ucm.vdm.logic.hints.FullVisionOpen;
import es.ucm.vdm.logic.hints.Hint;
import es.ucm.vdm.logic.hints.TooMuchBlue;
import es.ucm.vdm.logic.hints.TooMuchRed;
import es.ucm.vdm.logic.hints.TooMuchRedOpen;
import es.ucm.vdm.logic.hints.TotalBlueTiles;

/**
 * Clase que se encarga de gestionar las pistas
 */
public class HintsManager {
    // Array con todas las pistas
    private ArrayList<Hint> _hints;
    // Array con las pistas actuales del tablero (desde la ultima vez que se llamo al getHints())
    private ArrayList<Hint> _currentHints;

    // Pista visible en este momento
    private Hint _currentVisibleHint;

    private enum HintsName {
        FULLVISIONOPEN,
        TOOMUCHBLUE,
        FORCEBLUE,
        TOTALBLUETILES,
        TOOMUCHRED,
        AISLEDIDLE,
        AISLEDBLUE,
        FORCEDBLUEUNIQUEDIRECTION,
        FORCEDBLUESOLVED,
        TOOMUCHREDOPEN
    }


    public HintsManager() {
        init();
    }

    private void init() {
        _currentHints = new ArrayList<>();

        _hints = new ArrayList<>();
        _hints.add(new FullVisionOpen("Este número ve todos\nsus puntos",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _hints.add(new TooMuchBlue("Extender en una dirección\nsuperará el máximo permitido",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _hints.add(new ForceBlue("Este número puede\nampliarse en una dirección",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));

        _hints.add(new TotalBlueTiles("Este punto ve demasiados",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _hints.add(new TooMuchRed("Este punto no ve suficientes",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _hints.add(new AisledIdle("Esto debería ser fácil :)",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _hints.add(new AisledBlue("Esto debería ser fácil :)",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));

        _hints.add(new ForcedBlueUniqueDirection("Este número solo puede\nampliarse en una direccón",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _hints.add(new ForcedBlueSolved("Este punto debería ver\nal menos a otro",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _hints.add(new TooMuchRedOpen("Este punto debe ver\nal menos a otro",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
    }

    public void resetHint(Board board) {
        if (_currentVisibleHint != null) {
            _currentVisibleHint.showText(false);
            board.getTile(_currentVisibleHint.getIndexTileX(), _currentVisibleHint.getIndexTileY()).showHintMark(false);
        }
    }

    /**
     * Devuelve FALSE si no hay pistas que mostrar. TRUE en caso contrario
     */
    public boolean showHint(Board board) {

        int dimension = (int) Math.sqrt(board.getBoard().size());

        if (_currentVisibleHint != null) {
            for (Hint h : _hints) {
                h.showText(false);
                board.getBoard().get((dimension * h.getIndexTileY()) + h.getIndexTileX()).showHintMark(false);
            }
        }


        Random r = new Random();
        ArrayList<Hint> hints = getCurrentHints(board.getBoard(), dimension);

        if (!hints.isEmpty()) {
            _currentVisibleHint = hints.get(r.nextInt(hints.size()));

            _currentVisibleHint.showText(true);
            board.getBoard().get((dimension * _currentVisibleHint.getIndexTileY()) + _currentVisibleHint.getIndexTileX()).showHintMark(true);
        } else
            resetHint(board);

        return !hints.isEmpty();
    }

    private ArrayList<Hint> getCurrentHints(ArrayList<Tile> board, int dimension) {
        _currentHints.clear();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                for (Hint h : _hints) {
                    if (h.executeHint(j, i, board) != null) {
                        h.setIndexTile(j, i);
                        _currentHints.add(h);
                    }
                }
            }
        }
        return _currentHints;
    }

    public void update(double delta) {
        if (_currentVisibleHint != null)
            _currentVisibleHint.update(delta);
    }

    public void render(Graphics g) {
        if (_currentVisibleHint != null)
            _currentVisibleHint.render(g);
    }

    private boolean ApplyHintsInPosition(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        Position t = _hints.get(HintsName.FULLVISIONOPEN.ordinal()).executeHint(x, y, board);
        if (t != null) {
            board.get((dimension * t.y) + t.x).setState(Tile.State.WALL);
            return true;
        }

        t = _hints.get(HintsName.TOOMUCHBLUE.ordinal()).executeHint(x, y, board);
        if (t != null) {
            board.get((dimension * t.y) + t.x).setState(Tile.State.WALL);
            return true;
        }

        t = _hints.get(HintsName.FORCEBLUE.ordinal()).executeHint(x, y, board);
        if (t != null) {
            board.get((dimension * t.y) + t.x).setState(Tile.State.DOT);
            return true;
        }

        t = _hints.get(HintsName.AISLEDIDLE.ordinal()).executeHint(x, y, board);
        if (t != null) {
            board.get((dimension * t.y) + t.x).setState(Tile.State.WALL);
            return true;
        }

        return false;
    }

    // recorre el tablero para dar pistas sobre casillas
    boolean resolvePuzzle(ArrayList<Tile> b) {
        int dimension = (int) Math.sqrt(b.size());
        ArrayList<Tile> board = new ArrayList<>();

        for (Tile t : b) {
            board.add(new Tile(t));
        }

        int length = dimension * dimension;
        int i = 0;
        boolean isHint;
        int count = 0;

        while (count < length) {
            i %= length;
            int x = i % dimension;
            int y = i / dimension;

            isHint = ApplyHintsInPosition(x, y, board);

            if (!isHint)
                count++;
            else
                count = 0;

            i++;
        }
        int aux = countEmpty(board);
        board.clear();

        return aux == 0;
    }

    // devuelve el numero de casillas vacias del tablero
    private int countEmpty(ArrayList<Tile> board) {
        int _dimension = (int) Math.sqrt(board.size());

        int length = _dimension * _dimension;
        int count = 0;

        for (int i = 0; i < length; i++) {
            if (board.get(i).getState() == Tile.State.EMPTY)
                count++;
        }

        return count;
    }
}
