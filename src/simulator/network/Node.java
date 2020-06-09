package simulator.network;

import simulator.control.Simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Node {
    private static long nextID = 0;

    protected long id;
    protected List<Link> inputs;
    protected List<Link> outputs;
    protected String label;
    protected Boolean visited;

    public Node(String label, Link... links) {
        id = nextID++;
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        visited = false;

        this.label = label;
        addInput(links);

        Simulator.circuit.addNode(this);
    }

    public abstract void evaluate();

    public void addOutputLink(Boolean value) {
        Link link = new Link(value);
        link.setSource(this);
        addOutput(link);
    }

    public List<Link> getInputs() {
        return inputs;
    }

    public Link getInput(int index) {
        return getInputs().get(index);
    }

    public void addInput(Link... links) {
        for (Link link: links) {
                link.addDestination(this);
                getInputs().add(link);
        }
    }

    public void setInput(int index, Link link) {
        link.addDestination(this);
        getInputs().set(index, link);
    }

    public List<Link> getOutputs() {
        return outputs;
    }

    public Link getOutput(int index) {
        return getOutputs().get(index);
    }

    public void addOutput(Link... links) {
        getOutputs().addAll(Arrays.asList(links));
    }

    public void setOutput(int index, Link link) {
        getOutputs().set(index, link);
    }

    public long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setVisited(Boolean state) {
        visited = state;
    }

    public Boolean isVisited() {
        return visited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return id == node.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}