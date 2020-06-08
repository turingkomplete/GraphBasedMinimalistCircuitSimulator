package simulator.gates.combinational;

import simulator.network.Node;

public class ExplicitInput extends Node {
    public ExplicitInput(String label, Boolean value) {
        super(label);
        addOutputLink(value);
    }

    @Override
    public final void evaluate() {
        //do nothing
    }
}
