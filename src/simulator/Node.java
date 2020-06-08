package simulator;

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

    public Node(String label) {
        id = nextID++;
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        visited = false;

        this.label = label;

        Circuit.addNode(this);
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

    public void addInput(Link... in) {
        for (Link link: in)
            if (!link.getDestinations().contains(this))
                link.addDestination(this);

        getInputs().addAll(Arrays.asList(in));
    }

    public void setInput(int index, Link in) {
        in.addDestination(this);
        getInputs().set(index, in);
    }

    public List<Link> getOutputs() {
        return outputs;
    }

    public Link getOutput(int index) {
        return getOutputs().get(index);
    }

    public void addOutput(Link... in) {
        getOutputs().addAll(Arrays.asList(in));
    }

    public void setOutput(int index, Link in) {
        getOutputs().set(index, in);
    }

    public long getId() {
        return id;
    }

    public void setVisitCondition(Boolean state) {
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