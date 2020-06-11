package simulator.wrappers;

import simulator.network.Link;
import simulator.network.Linkable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Wrapper implements Linkable {
    private static long nextID = 0;

    protected long id;
    protected List<Link> inputs;
    protected List<Link> outputs;
    protected String label;

    public Wrapper(String label, Link... links) {
        id = nextID++;
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();

        this.label = label;
        addInput(links);
        initialize();
    }

    public abstract void initialize();

    @Override
    public List<Link> getInputs() {
        return inputs;
    }

    @Override
    public Link getInput(int index) {
        return getInputs().get(index);
    }

    @Override
    public void addInput(Link... links) {
        getInputs().addAll(Arrays.asList(links));
    }

    @Override
    public void setInput(int index, Link link) {
        getInputs().set(index, link);
    }

    @Override
    public List<Link> getOutputs() {
        return outputs;
    }

    @Override
    public Link getOutput(int index) {
        return getOutputs().get(index);
    }

    @Override
    public void addOutput(Link... links) {
        getOutputs().addAll(Arrays.asList(links));
    }

    @Override
    public void setOutput(int index, Link link) {
        getOutputs().set(index, link);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
