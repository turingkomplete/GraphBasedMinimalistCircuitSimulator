package simulator.gates.sequential;

import simulator.network.Node;

import java.util.Date;

public class Clock extends Node {
    private Date startTime;
    private long delay;

    public Clock(String label, long delay) {
        super(label);
        addOutputLink(false);
        this.delay = delay;
    }

    public void startClock() {
        startTime = new Date();
    }

    @Override
    public void evaluate() {
        Date currentTime = new Date();
        long spent = startTime.getTime() - currentTime.getTime();
        getOutput(0).setSignal(!((spent / delay) % 2 == 0));
    }
}
