package simulator.control;

import simulator.network.Link;
import simulator.network.Linkable;
import simulator.network.Node;

import java.util.ArrayList;
import java.util.List;

public class Debugger implements Runnable {
    private List<Linkable> trackList;
    private long delay;
    private Thread thread;

    public Debugger(long delay) {
        this.delay = delay;
        trackList = new ArrayList<>();
        thread = new Thread(this);
    }

    public void startDebugger() {
        thread.start();
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void addTrackItem(Linkable... trackList) {
        for (Linkable linkable : trackList) {
            if (!this.trackList.contains(linkable)) {
                this.trackList.add(linkable);
            }
        }
    }

    public void printState() {
        if (!trackList.isEmpty()) {
            for (Linkable linkable : trackList) {
                System.out.print(linkable.getLabel() + "[" + linkable.getId() + "]" + ": ");
                for (Link link : linkable.getOutputs()) {
                    System.out.print(link.getSignal() + " ");
                }
                System.out.println();
            }
            System.out.println("--------------------------------");
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!trackList.isEmpty()) {
                printState();

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}