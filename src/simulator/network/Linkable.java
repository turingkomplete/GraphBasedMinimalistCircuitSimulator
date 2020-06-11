package simulator.network;

import java.util.List;

public interface Linkable {
    public List<Link> getInputs();
    Link getInput(int index);
    void addInput(Link... links);
    void setInput(int index, Link link);
    List<Link> getOutputs();
    Link getOutput(int index);
    void addOutput(Link... links);
    void setOutput(int index, Link link);
    long getId();
    String getLabel();
}
