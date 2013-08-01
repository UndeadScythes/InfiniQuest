package uds.infiniquest.quest;

/**
 *
 * @author UndeadScythes
 */
public class Quest {
    public enum Type {
        FETCH,
        COLLECT,
        RESCUE,
        CLEAR,
        ESCORT,
        ASSASINATE,
        EXPLORE,
        BUY;
    }

    private final Type type;

    public Quest() {
        this.type = Type.FETCH;
    }
}
