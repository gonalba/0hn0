package es.ucm.vdm.engine.pc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import es.ucm.vdm.engine.common.AbstractInput;

public class PCInput extends AbstractInput {
    private ClickEvents _clickEvents;
    private MotionEvents _motionEvents;
    private PCGraphics _graphics;

    PCInput(JFrame jFrame, PCGraphics e) {

        _clickEvents = new ClickEvents();
        _motionEvents = new MotionEvents();
        _graphics = e;

        jFrame.addMouseListener(_clickEvents);
        jFrame.addMouseMotionListener(_motionEvents);
    }

    class MotionEvents implements MouseMotionListener {

        /**
         * Arrastrar el ratón con el botón pulsado
         */
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            addEvent(new MyEvent(PCInput.Type.MOVED, (int) ((mouseEvent.getX() - _graphics.getWidthBar()) / _graphics.getScaleFactor()),
                    (int) ((mouseEvent.getY() - _graphics.getHeightBar() - _graphics.hightBarOffset) / _graphics.getScaleFactor()), mouseEvent.getButton()));
        }

        /**
         * Evento que salta cuando el raton se mueve
         */
        @Override
        public void mouseMoved(MouseEvent mouseEvent) {

        }
    }

    class ClickEvents implements MouseListener {

        /**
         * Si pulsas un botón, arrastras fuera del botón y sueltas no se ejecutara el click()
         */
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        /**
         * mouseEvent.getButton() => NOBUTTON (0) - sin pulsación, BUTTON1 (1) - derecho,
         * BUTTON2 (2) - centro, BUTTON3 (3) - izquierdo
         */
        @Override
        synchronized public void mousePressed(MouseEvent mouseEvent) {
            addEvent(new MyEvent(PCInput.Type.PRESS, (int) ((mouseEvent.getX() - _graphics.getWidthBar()) / _graphics.getScaleFactor()),
                    (int) ((mouseEvent.getY() - _graphics.getHeightBar() - _graphics.hightBarOffset) / _graphics.getScaleFactor()), mouseEvent.getButton()));
        }

        /**
         * La acción de pulsar se ejecuta al sontar
         */
        @Override
        synchronized public void mouseReleased(MouseEvent mouseEvent) {
            addEvent(new MyEvent(PCInput.Type.RELEASE, (int) ((mouseEvent.getX() - _graphics.getWidthBar()) / _graphics.getScaleFactor()),
                    (int) ((mouseEvent.getY() - _graphics.getHeightBar() - _graphics.hightBarOffset) / _graphics.getScaleFactor()), mouseEvent.getButton()));
        }

        /**
         * Evento que se lanza cuando el raton pasa por encima
         */
        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        /**
         * Evento que se lanza cuando el raton deja de estar encima
         */
        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }
}
