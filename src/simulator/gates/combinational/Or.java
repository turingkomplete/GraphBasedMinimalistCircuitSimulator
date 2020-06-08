package simulator.gates.combinational;

import simulator.network.Link;
import simulator.network.Node;

public class Or extends Node {
    public Or(String label, Link... links) {
        super(label);
        addOutputLink(false);
        addInput(links);
    }

    @Override
    public void evaluate() {
        boolean result = false;

        for (Link l: getInputs()) {
            result = result || l.getValue();
        }

        getOutput(0).setValue(result);
    }
}
