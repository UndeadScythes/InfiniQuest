package uds.infiniquest.display;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import uds.infiniquest.geometry.*;
import uds.infiniquest.handler.Handler;

/**
 *
 * @author UndeadScythes
 */
public class Display extends JComponent implements ActionListener, KeyListener {
    private final static ArrayList<Layer> LAYERS = new ArrayList<Layer>();
    private final static ArrayList<Handler> HANDLERS = new ArrayList<Handler>();

    private final ArrayList<Integer> downedKeys = new ArrayList<Integer>();

    @Override
    public final void paint(final Graphics g) {
        try {
            final Vector2 v = new Vector2(getWidth(), getHeight());
            for(final Layer l : LAYERS) {
                l.draw((Graphics2D)g, v);
            }
        } catch(ConcurrentModificationException ex) {
            System.out.println("CME on paint");
        }
    }

    @Override
    public final void actionPerformed(final ActionEvent event) {
        repaint();
    }

    @Override
    public final void keyTyped(final KeyEvent e) {}

    @Override
    public final void keyPressed(final KeyEvent e) {
        try {
            final int k = e.getKeyCode();
            if(!downedKeys.contains(k)) {
                downedKeys.add(k);
                for(final Handler h : HANDLERS) {
                    try {
                        h.inputPressed(k);
                    } catch (IOException ex) {
                        Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch(ConcurrentModificationException ex) {
            System.out.println("CME on handle");
        }
    }

    @Override
    public final void keyReleased(final KeyEvent e) {
        final int k = e.getKeyCode();
        for(final Handler h : HANDLERS) {
            h.inputReleased(k);
        }
        downedKeys.remove(Integer.valueOf(k));
    }

    public static void addLayer(final Layer l) {
        System.out.println("Adding layer " + l.getClass().getSimpleName() + " - (" + LAYERS.size() + ")");
        LAYERS.add(l);
    }

    public static void addHandler(final Handler h) {
        System.out.println("Adding handler " + h.getClass().getSimpleName() + " - (" + HANDLERS.size() + ")");
        HANDLERS.add(h);
    }

    public static void removeLayer(final Layer l) {
        System.out.println("Removing layer " + l.getClass().getSimpleName() + " - (" + LAYERS.size() + ")");
        LAYERS.remove(l);
    }

    public static void removeHandler(final Handler h) {
        System.out.println("Removing handler " + h.getClass().getSimpleName() + " - (" + HANDLERS.size() + ")");
        HANDLERS.remove(h);
    }

    public static void clear() {
        for(Layer l : LAYERS) {
            System.out.println("Removing layer " + l.getClass().getSimpleName() + " - (" + LAYERS.size() + ")");
        }
        LAYERS.clear();
        for(Handler h : HANDLERS) {
            System.out.println("Removing handler " + h.getClass().getSimpleName() + " - (" + HANDLERS.size() + ")");
        }
        HANDLERS.clear();
    }
}
