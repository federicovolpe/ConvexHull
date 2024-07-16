package basic;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * generic node class for a node in n dimension
 * depending by the dimension given in its constructor
 */
public class Node {
    
    protected int dim, index;
    protected List<Integer> coordinates ;
    protected Random rnd;

    /**
     * given a list of coordinates initializes the point
     * @param coordinates
     */
    public Node (final List<Integer> coordinates, int index){
      this.coordinates = new ArrayList<>(coordinates);
      dim = coordinates.size();
      this.index = index;
    }

    /**
     * new node with random coordinates in a dim-n space
     * @param dim dimension of the node
     * @param bound upper bound for the coordinates of the point
     * @param index of the node
     * @throws IllegalArgumentException if the dimension is < 1
     */
    public Node (int dim, int bound, int index) throws IllegalArgumentException {
      if(dim < 1) throw new IllegalArgumentException("dimension of random node should be >= 1");
      rnd = new Random();
      this.dim = dim;

      for (int i = 0; i < dim; i++) {
        coordinates.add(rnd.nextInt());
      }
    }

    /**
     * calculate the distance between two nodes
     * @param other should be of the same dimension as this one
     * @return the distance between the two
     */
    public double calcDistance(Node other) throws IllegalArgumentException{
      if(! sameDimension(other)) throw new IllegalArgumentException("points should be of the same dimension "+this.dim+" != " +other.dim );
      double sum = 0;

      for (int i = 0; i < dim; i++) 
        sum += Math.pow(coordinates.get(i), 2) - Math.pow(other.coordinates.get(i), 2);

      return Math.sqrt(sum);
    }
    
    /**
     * if two nodes have the same position
     * @param other other node to compare
     * @throws IllegalArgumentException if thw two nodes dont have the same dimension
     */
    public boolean samePos(Node other) throws IllegalArgumentException {
      if(! sameDimension(other)) throw new IllegalArgumentException("points should be of the same dimension "+this.dim+" != " +other.dim );

      for (int i = 0; i < dim; i++) 
        if( ! coordinates.get(i).equals(other.coordinates.get(i))) return false;

      return true;
    }

    public boolean equalsIndex(Node other) {
        return this.index == other.index;
      }

    /**
     * if this node has the same dimension as another one
     */
    public boolean sameDimension(Node other){
      return this.dim == other.dim;
    }

    @Override
    public String toString(){
      StringBuilder sb = new StringBuilder();
      sb.append("Node: " + index + "[");

      for (int i = 0; i < dim; i++)
        sb.append(coordinates.get(i)).append(", ");

      sb.delete(sb.length()-2, sb.length());
      return sb.append("]").toString();
    }

}
