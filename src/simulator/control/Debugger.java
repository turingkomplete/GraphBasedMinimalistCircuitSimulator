package simulator.control;

import simulator.network.Link;
import simulator.network.Linkable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Debugger {
    private List<Linkable> trackList;
    private Date startTime;
    private long delay;
    private boolean edgeFlag;

    public Debugger(long delay) {
        this.delay = delay;
        trackList = new ArrayList<>();
        edgeFlag = true;
    }

    public void startDebugger() {
        startTime = new Date();
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

    public void run() {
        Date currentTime = new Date();
        long spent = startTime.getTime() - currentTime.getTime();
        if (delay != 0) {
            if ((spent / delay) % 2 == 0 && edgeFlag) {
                if (!trackList.isEmpty()) {
                    printState();
                }
                edgeFlag = false;
            } else if ((spent / delay) % 2 != 0 && !edgeFlag) {
                edgeFlag = true;
            }
        } else {
            printState();
        }
    }
}