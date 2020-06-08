package simulator.gates.combinational;

import simulator.network.Link;
import simulator.network.Node;

public class And extends Node {
    public And(String label, Link... links) {
        super(label);
        addOutputLink(false);
        addInput(links);
    }

    @Override
    public void evaluate() {
        Boolean result = true;

        for (Link l: getInputs()) {
            result = result && l.getValue();
        }

        getOutput(0).setValue(result);
    }
}
