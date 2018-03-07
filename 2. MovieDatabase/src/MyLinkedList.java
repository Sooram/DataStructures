
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T extends Comparable<T>> implements ListInterface<T>  {
	// dummy head
	Node<T> head;
	int numItems;

	// constructor
	public MyLinkedList() {
		head = new Node<T>(null);
	} // end constructor

    /**
     * {@code Iterable<T>}를 구현하여 iterator() 메소드를 제공하는 클래스의 인스턴스는
     * 다음과 같은 자바 for-each 문법의 혜택을 볼 수 있다.
     * 
     * <pre>
     *  for (T item: iterable) {
     *  	item.someMethod();
     *  }
     * </pre>
     * 
     * @see PrintCmd#apply(MovieDB)
     * @see SearchCmd#apply(MovieDB)
     * @see java.lang.Iterable#iterator()
     */
    public final Iterator<T> iterator() {
    	return new MyLinkedListIterator<T>(this);
    }

	@Override
	public boolean isEmpty() {
		return head.getNext() == null;
	}

	@Override
	public int size() {
		return numItems;
	}

	@Override
	public T first() {
		return head.getNext().getItem();
	}

	@Override
	public void add(T item) {
		Node<T> curr = this.head;
		Node<T> prev = this.head;

		curr = curr.getNext();
		while((curr != null) && (item.compareTo(curr.getItem()) > 0)) {
			prev = curr;
			curr = curr.getNext();
			
		}
		Node<T> temp = curr;
		prev.insertNext(item);
		prev = prev.getNext();
		prev.setNext(temp);
	
	
		numItems += 1;
	}

	@Override
	public void removeAll() {
		head.setNext(null);
	}

}

class MyLinkedListIterator<T extends Comparable<T>> implements Iterator<T> {
	private MyLinkedList<T> list;
	private Node<T> curr;
	private Node<T> prev;

	// constructor
	public MyLinkedListIterator(MyLinkedList<T> list) {
		this.list = list;
		this.curr = list.head;
		this.prev = curr;
	} // end constructor

	@Override
	public boolean hasNext() {
		return curr.getNext() != null;
	}

	@Override
	public T next() {
		if (!hasNext())
			throw new NoSuchElementException();

		prev = curr;
		curr = curr.getNext();

		return curr.getItem();
	}

	@Override
	public void remove() {
		if (curr == null)
			throw new NoSuchElementException();
		prev.removeNext();
		list.numItems -= 1;
		curr = prev;
		prev = null;
	}
}