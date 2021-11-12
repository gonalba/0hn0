package es.ucm.vdm.logic.behaviours;

import org.graalvm.compiler.replacements.Log;

import java.util.Stack;

import es.ucm.vdm.logic.Board;
import es.ucm.vdm.logic.engine.Behaviour;
import es.ucm.vdm.logic.engine.Position;
import es.ucm.vdm.logic.states.GameState;
import sun.security.util.Debug;


public class StepBackBehaviour implements Behaviour {
    private GameState _gs;
    private Board _board;

    public StepBackBehaviour(Board board,GameState gs) {

        _board = board;
        _gs = gs;
    }

    @Override
    public void onClick() {
        String a = _board.undoMove();
        _gs.showUndo(a);

    }
}

