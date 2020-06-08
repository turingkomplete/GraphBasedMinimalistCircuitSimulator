package simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Circuit {
    private List<Link> allLinks;
    private List<Node> allNodes;
    private List<Node> clocks;
    private List<List<Node>> netList;
    private Map<Link, List<Node>> removed;

    public Circuit() {
        allLinks = new ArrayList<>();
        allNodes = new ArrayList<>();
        clocks = new ArrayList<>();
        netList = new ArrayList<>();
        netList.add(new ArrayList<>());
        removed = new HashMap<>();
    }

    public void addNode(Node node) {
        if(node instanceof ExplicitInput)
            netList.get(0).add(node);

        if (node instanceof Clock)
            clocks.add(node);

        allNodes.add(node);
    }

    public void start() {
        deleteLoop();
        initializeNetList();
        addLoop();
        evaluateNetList();
    }

    private void addLoop() {
        for (Link link: removed.keySet()) {
            for (Node node: removed.get(link)) {
                link.addDestination(node);
                node.addInput(link);
            }
        }
    }

    public Boolean DepthFirstSearch(Node node) {
        Boolean loopDetected = false;
        node.setVisitCondition(true);

        for (Link link: node.getOutputs()) {
            for (int i = 0; i < link.getDestinations().size(); ++i) {
                if (link.getDestinations().get(i).isVisited()) {
                    if (!removed.containsKey(link)) {
                        removed.put(link, new ArrayList<>());
                    }
                    removed.get(link).add(link.getDestinations().get(i));
                    link.getDestinations().get(i).getInputs().remove(link);
                    link.getDestinations().remove(i);
                    node.setVisitCondition(false);
                    return true;
                }
                loopDetected = DepthFirstSearch(link.getDestinations().get(i));
                if (loopDetected) {
                    node.setVisitCondition(false);
                    return true;
                }
            }
        }
        node.setVisitCondition(false);
        return false;
    }

    public void deleteLoop() {
        Boolean loopDetected = true;

        while (loopDetected) {
            loopDetected = false;
            for (Node node: netList.get(0)) {
                loopDetected = DepthFirstSearch(node);
            }
        }
    }

    public void initializeNetList() {
        int level = 0;
        while (netList.size() >= level + 1) {
            initializeLevel(level++);
        }
    }

    public void initializeLevel(int level) {
        for (Node node: netList.get(level)) {
            for (Link link: node.getOutputs()) {
                link.setValidity(true);
            }
        }

        for (Node node: netList.get(level)) {
            for (Link link: node.getOutputs()) {
                for (Node innerNode : link.getDestinations()) {
                    Boolean flag = true;
                    for (Link innerLink : innerNode.getInputs()) {
                        if (!innerLink.isValid()) {
                            flag = false;
                        }
                    }

                    if (flag) {
                        if (netList.size() < level + 2) {
                            netList.add(new ArrayList<>());
                        }

                        if (!netList.get(level + 1).contains(innerNode)) {
                            netList.get(level + 1).add(innerNode);
                        }
                    }
                }
            }
        }
    }

    public void evaluateNetList() {
        for (List<Node> list: netList) {
            for (Node node: list) {
                node.evaluate();
            }
        }
    }
}
