package huffman;

public class Node implements Comparable<Node>{
    private int weight;
    private char code;
    private char ch;
    private String name;
    private Node parent;
    private Object graphObject;

    public Node(int weight, String name){
        this.weight = weight;
        this.name = name;
    }

    public Node(char key, int weight){
        this.ch = key;
        this.weight = weight;
        name = String.valueOf(key);
    }

    public char getCode() {
        return code;
    }


    @Override
    public int compareTo(Node node) {
        return -(node.weight - weight);
    }

    @Override
    public String toString() {
        return name + " - " + weight;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getWeight() {
        return weight;
    }

    public char getChar() {
        return ch;
    }

    public String getName() {
        return name;
    }

    public void setCode(char code) {
        this.code = code;
    }

    public Object getGraphObject() {
        return graphObject;
    }

    public void setGraphObject(Object graphObject) {
        this.graphObject = graphObject;
    }
}
