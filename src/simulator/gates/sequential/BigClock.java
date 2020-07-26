package simulator.gates.sequential;

import simulator.network.Link;
import simulator.network.Node;

public class BigClock extends Node {
    public BigClock(String label) {
        super(label);
        addOutputLink(true);
    }

    public void toggle() {
        getOutput(0).setSignal(!getOutput(0).getSignal());
    }

    @Override
    public void evaluate() {
        //do nothing
    }
}
