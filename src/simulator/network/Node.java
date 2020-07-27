package simulator.network;

import simulator.control.Simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Node implements Linkable{
    private static long nextID = 0;

    protected long id;
    protected List<Link> inputs;
    protected List<Link> outputs;
    protected String label;
    protected Boolean visited;
    protected Boolean loop;
    protected Boolean latch;
    protected Boolean latchValidity;

    public Node(String label, Link... links) {
        id = nextID++;
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        visited = false;
        loop = true;
        latch = false;
        latchValidity = true;

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
        for (Link link: links) {
                link.addDestination(this);
                getInputs().add(link);
        }
    }

    @Override
    public void setInput(int index, Link link) {
        link.addDestination(this);
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

    public void setVisited(Boolean state) {
        visited = state;
    }

    public Boolean isVisited() {
        return visited;
    }

    public Boolean getLoop() {
        return loop;
    }

    public void setLoop(Boolean loop) {
        this.loop = loop;
    }

    public Boolean getLatch() {
        return latch;
    }

    public void setLatch(Boolean latch) {
        this.latch = latch;
        latchValidity = !latch;
    }

    public Boolean getLatchValidity() {
        return latchValidity;
    }

    public void setLatchValidity(Boolean latchValidity) {
        this.latchValidity = latchValidity;
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
        int result = 11;
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }
}