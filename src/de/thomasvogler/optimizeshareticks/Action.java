package de.thomasvogler.optimizeshareticks;

public class Action {

    enum Type { BUY, SELL };

    private Type type;
    private ShareTick tick;

    Action(Type type, ShareTick tick) {
        this.type = type;
        this.tick = tick;
    }

    public Type getType() {
        return type;
    }

    public double getPrice() {
        return tick.price;
    }

    @Override
    public String toString() {

        return String.format("%4s - %s", type.toString(), tick.toString());
    }
}
