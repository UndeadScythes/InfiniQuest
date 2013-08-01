package uds.infiniquest.clock;

import java.awt.event.*;
import java.util.*;

/**
 *
 * @author UndeadScythes
 */
public class Clock implements ActionListener {
    private final static ArrayList<GameProcess> PROCESSES = new ArrayList<GameProcess>();

    private long lastClock = System.currentTimeMillis();

    @Override
    public void actionPerformed(final ActionEvent event) {
        try {
            for(GameProcess g : PROCESSES) {
                g.clock(System.currentTimeMillis() - lastClock);
            }
        } catch(ConcurrentModificationException ex) {
            System.out.println("CME on clock");
        }
        lastClock = System.currentTimeMillis();
    }

    public static void addProcess(final GameProcess g) {
        PROCESSES.add(g);
        System.out.println("Adding process " + g.getClass().getSimpleName() + " - (" + PROCESSES.size() + ")");
    }

    public static void removeProcess(final GameProcess g) {
        PROCESSES.remove(g);
        System.out.println("Removing process " + g.getClass().getSimpleName() + " - (" + PROCESSES.size() + ")");
    }

    public static void clear() {
        System.out.println("Clearing processes");
        PROCESSES.clear();
    }
}
