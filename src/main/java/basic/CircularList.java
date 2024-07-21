package basic;

import java.util.LinkedList;
import java.util.List;

public class CircularList<E> extends LinkedList<E> {

  // Constructor that takes a List
  public CircularList(List<E> list) {
    for (E element : list) {
      if (element instanceof Edge) {
        this.add((E) new Edge((Edge) element));
      } else {
        this.add(element);
      }
    }
  }

  public E getNext(E current) {
    int index = this.indexOf(current);
    if (index == -1) {
      throw new IllegalArgumentException("Element not found in the list.");
    }
    int nextIndex = (index + 1) % this.size();
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
