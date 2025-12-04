package game.strategy;

public enum AIMovementType {
    DEFAULT,
    ZIGZAG;

    public static AIMovementType getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
