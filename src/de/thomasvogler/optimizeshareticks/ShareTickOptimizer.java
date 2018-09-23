package de.thomasvogler.optimizeshareticks;

import java.util.LinkedList;
import java.util.List;

public class ShareTickOptimizer implements Runnable {

    public static final boolean DEBUG = false;
    public static final int COUNT = DEBUG ? 10 : 1_000_000;

    @Override
    public void run() {

        var ticks = generateRundomShareTicks(COUNT);
        if (DEBUG) {
            printTicksList(ticks);
        }
        var actions = optimize(ticks);
        printActionList(actions);
    }

    private List<Action> optimize(List<ShareTick> ticks) {

        List<Action> actions = new LinkedList<>();
        boolean haveShares = false;
        ShareTick lastTick = null;

        for (ShareTick current : ticks) {

            if (lastTick != null) {

                if (lastTick.price < current.price) {

                    if (! haveShares) {
                        actions.add(new Action(Action.Type.BUY, lastTick));
                        haveShares = true;
                    }

                } else if (lastTick.price > current.price) {

                    if (haveShares) {
                        actions.add(new Action(Action.Type.SELL, lastTick));
                        haveShares = false;
                    }
                }
            }

            lastTick = current;
        }

        if (haveShares) {
            actions.add(new Action(Action.Type.SELL, lastTick));
        }

        return actions;
    }

    private List<ShareTick> generateRundomShareTicks(int count) {

        var list = new LinkedList<ShareTick>();

        for (int i = 0; i < count; i++) {
            list.add(ShareTick.randomShareTick(i));
        }

        return list;
    }

    private void printTicksList(List<ShareTick> ticks) {
        System.out.println("Input list of ticks");
        ticks.forEach(t -> System.out.println(t.toString()));
    }

    private void printActionList(List<Action> actions) {

        if (DEBUG) {
            double tempLastBuy = 0;
            System.out.println("List of actions");
            for (Action action : actions) {
                System.out.printf("%s", action.toString());
                if (action.getType() == Action.Type.BUY) {
                    tempLastBuy = action.getPrice();
                    System.out.println();
                } else {
                    System.out.printf(" %.3f%n", action.getPrice() - tempLastBuy);
                }
            }
        }

        double saldo = 0;
        double lastBuy = 0;

        for (Action action : actions) {
            if (action.getType() == Action.Type.BUY) {
                lastBuy = action.getPrice();
            } else {
                saldo += action.getPrice() - lastBuy;
            }
        }

        System.out.printf("saldo = %.3f%n", saldo);
    }
}
