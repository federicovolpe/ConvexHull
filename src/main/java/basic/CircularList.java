package basic;

import java.util.LinkedList;
import java.util.List;

public class CircularList<E> extends LinkedList<E> {

  public CircularList() {}

  /**
   * constructor of a circular list starting from another list
   * the elements are references to the element of the original list
   * if the starting list is a list of edges the circular one has copies of the og elems
   * @param list starting list
   */
  public CircularList(List<E> list) {
    for (E element : list) {
      if (element instanceof Edge) {
        this.add((E) new Edge((Edge) element));
      } else if (element instanceof Point2D) {
        this.add((E) new Point2D((Point2D) element));
      }else{
        this.add(element);
      }
    }
  }

  /**
   * gets the next element of the list in a circular way
   * @param current current element
   * @return the next element from the given one
   */
  public E getNext(E current) {
    int index = this.indexOf(current);
    if (index == -1) throw new IllegalArgumentException("Element not found in the list.");

    int nextIndex = (index + 1) % this.size();
    return this.get(nextIndex);
  }

  /**
   * gets the previous element of the list in a circular way
   * @param current current element
   * @return the previous element from the given one
   */
  public E getPrev(E current) {
    int index = this.indexOf(current);
    if (index == -1) throw new IllegalArgumentException("Element"+current.toString()+" not found in the list.");

    int nextIndex = (index - 1) % this.size();
    return this.get(nextIndex);
  }

  @Override
  public E get(int index) {
    return super.get(((index % size()) + size()) % size());
  }

  // Override the remove method to remove the element at index i
  @Override
  public E remove(int index) {
    return super.remove(index % size());
  }

  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();
    for (E e: this )
      sb.append(e).append("\n");
    return sb.toString();
  }

}
