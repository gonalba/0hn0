package es.ucm.vdm.logic.states;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.State;

public class MenuState implements State {
    OhnoGame _game;
    Engine _engine;

    public MenuState(OhnoGame game) {
        _game = game;
    }

    @Override
    public boolean init(Engine engine) {
        _engine = engine;
        return true;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(0xFF1CC4E4);
        g.fillCircle(100, 100, 100);
    }
}