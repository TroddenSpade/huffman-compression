package sample;


public class Node implements  Comparable{
    private final int freq;
    private final Character ch;

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    private  Node left, right;

    public Node(int freq, Character ch, Node left, Node right) {
        this.freq = freq;
        this.ch = ch;
        this.left = left;
        this.right = right;
    }
    public Node(){
        this.freq=0;
        this.ch=null;
        this.left=null;
        this.right=null;
    }

    public Integer getFreq() {
        return freq;
    }

    public Character getCh() {
        return ch;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
    public boolean isLeaf(){
        return this.left==null && this.right==null;
    }

    @Override
    public int compareTo(Object o) {
        return this.freq-((Node)o).freq;
    }
    public String toString(){
        return this.ch.toString();
    }
}
