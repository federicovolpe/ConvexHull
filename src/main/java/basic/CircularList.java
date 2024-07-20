package basic;

import java.util.LinkedList;
import java.util.List;

public class CircularList<E> extends LinkedList<E> {

  // Constructor that takes a List
  public CircularList(List<E> list) {
    super(list);
  }

  public E getNext(E current) {
    int index = this.indexOf(current);
    if (index == -1) {
      throw new IllegalArgumentException("Element not found in the list.");
    }
    int nextIndex = (index + 1) % this.size();
    return this.get(nextIndex);
  }

  public E getPrevious(E current) {
    int index = this.indexOf(current);
    if (index == -1) {
      throw new IllegalArgumentException("Element not found in the list.");
    }
    int prevIndex = (index - 1 + this.size()) % this.size();
    return this.get(prevIndex);
  }

  @Override
  public E get(int index) {
    return super.get(index % size());
  }

  // Override the remove method to remove the element at index i
  @Override
  public E remove(int index) {
    return super.remove(index % size());
  }
}
