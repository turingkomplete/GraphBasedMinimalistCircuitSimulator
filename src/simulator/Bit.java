package simulator;

public class Bit extends Node implements ExplicitInput {
    public Bit(String label, Boolean value) {
        super(label);
        addOutputLink(value);
    }

    @Override
    public void evaluate() {
        //do nothing
    }
}
