package simulator.gates.combinational;

import simulator.network.Link;
import simulator.network.Node;

public class Not extends Node {
    public Not(String label, Link... links) {
        super(label, links);
    }

    @Override
    public void addInput(Link... links) {
        super.addInput(links);

        for (int i = 0; i < links.length; ++i) {
            addOutputLink(false);
        }
    }

    @Override
    public void evaluate() {
        for (int i = 0; i < getInputs().size(); ++i) {
            getOutput(i).setSignal(!getInput(i).getSignal());
        }
    }
}
