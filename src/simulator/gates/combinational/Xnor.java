package simulator.gates.combinational;

import simulator.network.Link;
import simulator.network.Node;

public class Xnor extends Node {
    public Xnor(String label, Link... links) {
        super(label, links);
        addOutputLink(false);
    }

    @Override
    public void evaluate() {
        int ones = 0;
        for (Link link: getInputs()) {
            if (link.getValue()) {
                ones++;
            }
        }

        getOutput(0).setValue(ones % 2 == 0);
    }
}
