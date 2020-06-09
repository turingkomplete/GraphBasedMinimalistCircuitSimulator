package simulator.gates.combinational;

import simulator.network.Link;
import simulator.network.Node;

public class Nor extends Node {
    public Nor(String label, Link... links) {
        super(label, links);
        addOutputLink(false);
    }

    @Override
    public void evaluate() {
        boolean result = false;

        for (Link link: getInputs()) {
            result = result || link.getSignal();
        }

        getOutput(0).setSignal(!result);
    }
}
