package uds.infiniquest.handler;

import java.io.*;

/**
 *
 * @author UndeadScythes
 */
public interface Handler {
    void inputPressed(final int keyCode) throws IOException;
    void inputReleased(final int keyCode);
}
