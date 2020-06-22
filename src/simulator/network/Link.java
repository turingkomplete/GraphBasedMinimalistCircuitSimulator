package simulator.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Link {
    private static long nextID = 0;
    private Long id;
    private Boolean signal;
    private Node source;
    private List<Node> destinations;
    private Boolean validity;

    public Link(Boolean signal) {
        id = nextID++;
        validity = false;
        destinations = new ArrayList<>();

        this.signal = signal;
    }

    public Boolean getSignal() {
        return signal;
    }

    public void setSignal(Boolean signal) {
        this.signal = signal;
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public List<Node> getDestinations() {
        return destinations;
    }

    public Node getDestination(int index) {
        return getDestinations().get(index);
    }

    public void addDestination(Node... destination) {
        getDestinations().addAll(Arrays.asList(destination));
    }

    public Boolean isValid() {
        return validity;
    }

    public void setValidity(Boolean valid) {
        this.validity = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        return id.equals(link.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
