package simulator;

public class DFlipFlop extends Node implements FlipFlop {
    private Boolean memory;
    private Boolean edgeFlag;

    public DFlipFlop(String label, Link... links) {
        super(label);
        edgeFlag = true;
        memory = false;
        addOutputLink(false);
        addInput(links);
    }

    @Override
    public void setOutput() {
        outputs.get(0).setValue(memory);
    }

    @Override
    public void loadMemory() {
        memory = getInput(1).getValue();
    }

    @Override
    public void evaluate() {
        if(getInput(0).getValue() && edgeFlag) {
            setOutput();
            loadMemory();
            edgeFlag = false;
        } else if(!getInput(0).getValue() && !edgeFlag) {
            edgeFlag = true;
        }
    }
}