package simulator.procedures;

import simulator.network.Link;
import simulator.network.Node;

public abstract class Procedure extends Node {
    public Procedure(String label, Link... links) {
        super(label, links);
        initialize();
    }
    
    public abstract void initialize();

    @Override
    public final void evaluate() { 
        //do nothing
    }
}
