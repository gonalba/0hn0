package es.ucm.vdm.engine.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.State;

public class AndroidEngine implements Engine {
    private SurfaceView _surfaceView;
    private MainLoop _mainLoop;
    private AndroidGraphics _graphics;
    private AndroidInput _input;
    private AssetManager _assetManager;

    /**
     * Constructor que recibe un Contexto en el que se integrará la vista (normalmente una actividad).
     */
    public AndroidEngine(Context context) {
        _surfaceView = new SurfaceView(context);
        _assetManager = context.getAssets();
        _graphics = new AndroidGraphics(_assetManager);
        _input = new AndroidInput(_surfaceView, _graphics);
        _mainLoop = new MainLoop(this);
    }

    /**
     * Método llamado para solicitar que se continue con el
     * active rendering. El "juego" se vuelve a poner en marcha
     * (o se pone en marcha por primera vez).
     */
    public void resume() {
        _mainLoop.resume();
    } // resume

    /**
     * Método llamado cuando el active rendering debe ser detenido.
     * Puede tardar un pequeño instante en volver, porque espera a que
     * se termine de generar el frame en curso.
     * <p>
     * Se hace así intencionadamente, para bloquear la hebra de UI
     * temporalmente y evitar potenciales situaciones de carrera (como
     * por ejemplo que Android llame a resume() antes de que el último
     * frame haya terminado de generarse).
     */
    public void pause() {
        _mainLoop.pause();
    } // pause

    @Override
    public AndroidGraphics getGraphics() {
        return _graphics;
    }

    @Override
    public AndroidInput getInput() {
        return _input;
    }

    @Override
    public InputStream openInputStream(String filename) throws IOException {
        return _assetManager.open(filename);
    }

    /**
     * Devuelve la surfaceView actual
     */
    public SurfaceView getSurfaceView() {
        return _surfaceView;
    }

    @Override
    public void setState(State s) {
        _mainLoop.setState(s);
    }

    /**
     * Devuelve el estado actual
     */
    protected State getState() {
        return _mainLoop.getState();
    }

}
