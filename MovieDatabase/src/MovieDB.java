import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB {
	private MyLinkedList<Genre> genreList;	
	
    public MovieDB() {
        // FIXME implement this
    	
    	// HINT: MovieDBGenre 클래스를 정렬된 상태로 유지하기 위한 
    	// MyLinkedList 타입의 멤버 변수를 초기화 한다.
    	genreList = new MyLinkedList<Genre>();
    }

    public void insert(MovieDBItem item) {

        System.err.printf("[trace] MovieDB: INSERT [%s] [%s]\n", item.getGenre(), item.getTitle());
        
        Genre itemGenre = new Genre(item.getGenre());
        String itemTitle = new String(item.getTitle());
        boolean alreadyExist = false;
        
        for(Genre genre : genreList) {
        	if(genre.compareTo(itemGenre) == 0) {
        		// if the genre is already in the list
        		alreadyExist = true;
        		for(String title : genre.titleList) {
        			if(title.compareTo(itemTitle) != 0)
        			genre.titleList.add(itemTitle);
        		}
        	}
        }
        if(!alreadyExist) {
        	genreList.add(itemGenre);
        	itemGenre.titleList.add(itemTitle);
        }
        
        for(Genre genre : genreList) {
        	System.out.println(genre.getItem());
        	for(String title : genre.titleList) {
        		System.out.println(title);
        	}
        }
    
    }

    public void delete(MovieDBItem item) {
        // FIXME implement this
        // Remove the given item from the MovieDB.
    	
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: DELETE [%s] [%s]\n", item.getGenre(), item.getTitle());
        
        Genre itemGenre = new Genre(item.getGenre());
        String itemTitle = new String(item.getTitle());
        MyLinkedListIterator<Genre> itrGenre = new MyLinkedListIterator<Genre>(genreList);
        
        
        for(Genre genre : genreList) {
        	if(genre.compareTo(itemGenre) == 0) {
        		// if the genre is in the list
        		MyLinkedListIterator<String> itrTitle = new MyLinkedListIterator<String>(genre.titleList);
        		itrTitle.next();
        		for(String title : genre.titleList) {
        			if(title.compareTo(itemTitle) != 0) {
        				itrTitle.next();
        			}
        			else {
        				itrTitle.remove();
        			}
        		}
        	} // end if
        	
        	itrGenre.next();
        	
        	if(genre.titleList.isEmpty()) {
        		itrGenre.remove();
        	}
        } // end for
       
        for(Genre gen : genreList) {
        	System.out.println(gen.getItem());
        	for(String tit : gen.titleList) {
        		System.out.println(tit);
        	}
        }
    }

    public MyLinkedList<MovieDBItem> search(String term) {
        // FIXME implement this
        // Search the given term from the MovieDB.
        // You should return a linked list of MovieDBItem.
        // The search command is handled at SearchCmd class.
    	
    	// Printing search results is the responsibility of SearchCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.
    	
        // This tracing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
    	System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);
    	
    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();

        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
        // FIXME implement this
        // Search the given term from the MovieDatabase.
        // You should return a linked list of QueryResult.
        // The print command is handled at PrintCmd class.

    	// Printing movie items is the responsibility of PrintCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: ITEMS\n");

    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        
    	return results;
    }
}

class Genre extends Node<String> implements Comparable<Genre> {
	MyLinkedList<String> titleList = new MyLinkedList<>();
	
	public Genre(String name) {
		super(name);
		this.setNext(titleList.head);
	}
	
	@Override
	public int compareTo(Genre o) {
	 	int comp;
	 	String originalGenre = this.getItem();
	 	String newGenre = o.getItem();
    	
    	if((originalGenre.length() > newGenre.length()) || 
    			((originalGenre.length() == newGenre.length()) && (originalGenre.compareTo(newGenre) > 0))) { 
    		// original value > new value
    		comp = 1;
    	}
    	else if(originalGenre.compareTo(newGenre) == 0) { 
    		// original value = new value
    		comp = 0;
    	}
    	else { 
    		// original value < new value
    		comp = -1;
    	}
    	
    	return comp;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;

	    result = prime * result + ((getItem() == null) ? 0 : getItem().hashCode());

	    return result;
	}

	@Override
	public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Genre other = (Genre) obj;
        if (this.getItem() == null) {
            if (other.getItem() != null)
                return false;
        } else if (!this.getItem().equals(other.getItem()))
            return false;     
        return true;
	}
}

class MovieList implements ListInterface<String> {	
	Node<String> head;
	int numItems;
	
	public MovieList() {
		head = new Node<String>(null);
	}

	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>(this);
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
	public void add(String item) {
		Node<String> last = head;
		while (last.getNext() != null) {
			last = last.getNext();
		}
		last.insertNext(item);
		numItems += 1;
	}

	@Override
	public String first() {
		return head.getNext().getItem();
	}

	@Override
	public void removeAll() {
		head.setNext(null);
	}
}