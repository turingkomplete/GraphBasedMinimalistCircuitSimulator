package simulator.gates.sequential.flipflops;

import simulator.network.Link;
import simulator.network.Node;

public class JKFlipFlop extends Node implements FlipFlop {
    private Boolean memory;
    private Boolean edgeFlag;

    public JKFlipFlop(String label, Link... links) {
        super(label, links);
        edgeFlag = true;
        memory = false;
        addOutputLink(false);
        addOutputLink(false);
    }

    @Override
    public void setOutput() {
        outputs.get(0).setSignal(memory);
        outputs.get(1).setSignal(!memory);
    }

    @Override
    public void loadMemory() {
        Boolean j = getInput(1).getSignal();
        Boolean k = getInput(2).getSignal();

        if (j && !k) {
            memory = true;
        } else if (!j && k) {
            memory = false;
        } else if (j && k) {
            memory = !memory;
        }
    }

    @Override
    public void evaluate() {
        if(getInput(0).getSignal() && edgeFlag) {
            setOutput();
            loadMemory();
            edgeFlag = false;
        } else if(!getInput(0).getSignal() && !edgeFlag) {
            edgeFlag = true;
        }
    }
}