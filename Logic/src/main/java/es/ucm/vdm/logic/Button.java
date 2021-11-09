package es.ucm.vdm.logic;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.logic.engine.GameObject;
import es.ucm.vdm.logic.engine.Position;

public class Button extends GameObject {
    private Font _font;
    private Engine _engine;
    String _text;

    public Button(int x, int y, String text, ResourcesManager.FontsID id) {
        super(new Position(x,y));
        _font = ResourcesManager.getInstance().getFont(id);
        _text = text;
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Graphics g) {
        _engine.getGraphics().drawText(_text, _position.x, _position.y);
    }
}