package de.thomasvogler.optimizeshareticks;

import java.security.SecureRandom;
import java.util.Random;

public class ShareTick {

    private static Random prng = new SecureRandom();

    int index;
    double price;

    private ShareTick(int index, double price) {
        this.index = index;
        this.price = price;
    }

    public static ShareTick randomShareTick(int index) {
        return new ShareTick(index, prng.nextDouble());
    }

    @Override
    public String toString() {
        return String.format("%3d %.3f", index, price);
    }
}
