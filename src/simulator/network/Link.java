package simulator.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Link {
    private static long nextID = 0;
    private Long id;
    private Boolean value;
    private Node source;
    private List<Node> destinations;
    private Boolean validity;

    public Link(Boolean value) {
        id = nextID++;
        validity = false;
        destinations = new ArrayList<>();

        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
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
