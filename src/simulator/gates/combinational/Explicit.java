package simulator.gates.combinational;

import simulator.network.Node;

public class Explicit extends Node {
    public Explicit(String label, Boolean value) {
        super(label);
        addOutputLink(value);
    }

    @Override
    public final void evaluate() {
        //do nothing
    }
}
