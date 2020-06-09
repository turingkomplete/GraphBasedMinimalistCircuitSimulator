package simulator.control;

import simulator.network.Link;
import simulator.network.Node;

import java.util.ArrayList;
import java.util.List;

public class Debugger implements Runnable {
    private List<Node> trackList;
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

    public void addTrackItem(Node... trackList) {
        for (Node node : trackList) {
            if (!this.trackList.contains(node)) {
                this.trackList.add(node);
            }
        }
    }

    public void printState() {
        if (!trackList.isEmpty()) {
            for (Node node : trackList) {
                System.out.print(node.getLabel() + "[" + node.getId() + "]" + ": ");
                for (Link link : node.getOutputs()) {
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