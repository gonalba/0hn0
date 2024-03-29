package es.ucm.vdm.logic.engine;

import java.util.List;

import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Image;
import es.ucm.vdm.engine.common.Input;
import es.ucm.vdm.logic.ResourcesManager;

/**
 * Clase que hace boton a una imagen.
 * Hereda de Button y al constructor se le pasa una imagen
 * <p>
 * Implementa el metodo recivesEvents() de la interfaz InteractiveObject donde recive una lista de eventos por parametro
 * En el constructor se da de alta al objeto en el InputManager para que se llame dicho metodo de la interfaz
 */
public class ImageButton extends Button {
    private Image _image;
    private float _scale = 0.5f;

    public ImageButton(ResourcesManager.ImagesID id) {
        _image = ResourcesManager.Instance().getImage(id);

        InputManager.Instance().addInteractObject(this);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Graphics g) {

        g.drawImage(_image, _position.x + (int) (_image.getWidth() * _scale) / 2, _position.y + (int) (_image.getWidth() * _scale) / 2,
                (int) (_image.getWidth() * _scale), (int) (_image.getHeight() * _scale));
    }

    @Override
    public void receivesEvents(List<Input.MyEvent> events) {
        for (Input.MyEvent e : events) {
            if (_behaviour != null && e._type == Input.Type.PRESS && inChoords(e._x, e._y)) {
                _behaviour.onClick();
            }
        }
    }

    private boolean inChoords(int x, int y) {
        int length = (int) (_image.getWidth() * _scale);

        return x > _position.x + ((_image.getWidth() - length) / 2) &&
                x < _position.x + ((_image.getWidth() - length) / 2) + length &&
                y > _position.y + ((_image.getWidth() - length) / 2) &&
                y < _position.y + ((_image.getWidth() - length) / 2) + length;
    }
}
