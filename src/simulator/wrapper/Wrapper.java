package simulator.wrapper;

import simulator.network.Link;
import simulator.network.Linkable;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public abstract class Wrapper implements Linkable {
    private static long nextID = 0;

    protected long id;
    protected String label;
    protected int inputSize;
    protected int outputSize;
    protected DataStream inputStream;
    protected DataStream outputStream;


    public Wrapper(String label, String stream, Link... links) {
        id = nextID++;

        this.label = label;

        String pattern = "(\\d+)([xX])(\\d+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(stream);

        if (m.find()) {
            inputSize = Integer.parseInt(String.valueOf(m.group(1)));
            outputSize = Integer.parseInt(String.valueOf(m.group(3)));
        }

        inputStream = new DataStream("INPUT_STREAM", inputSize);
        outputStream = new DataStream("OUTPUT_STREAM", outputSize);

        addInput(links);

        initialize();
    }

    public abstract void initialize();

    @Override
    public List<Link> getInputs() {
        return inputStream.getOutputs();
    }

    @Override
    public Link getInput(int index) {
        return inputStream.getOutput(index);
    }

    @Override
    public void addInput(Link... links) {
        inputStream.addInput(links);
    }

    @Override
    public void setInput(int index, Link link) {
        inputStream.setInput(index, link);
    }

    @Override
    public List<Link> getOutputs() {
        return outputStream.getOutputs();
    }

    @Override
    public Link getOutput(int index) {
        return outputStream.getOutput(index);
    }

    @Override
    public void addOutput(Link... links) {
        outputStream.addInput(links);
    }

    @Override
    public void setOutput(int index, Link link) {
        outputStream.setInput(index, link);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public int getInputSize() {
        return inputSize;
    }

    public int getOutputSize() {
        return outputSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wrapper wrapper = (Wrapper) o;

        return id == wrapper.id;
    }

    @Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }
}
