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
	
	// constructor
    public MovieDB() {
    	genreList = new MyLinkedList<Genre>();
    }
    // end constructor

    public void insert(MovieDBItem item) {
        Genre itemGenre = new Genre(item.getGenre());
        String itemTitle = new String(item.getTitle());
        boolean genreExist = false;
        boolean movieExist = false;
        
        for(Genre genre : genreList) {
        	if(genre.compareTo(itemGenre) == 0) {
        		// if the genre is already in the list
        		genreExist = true;
        		for(String title : genre.titleList) {
        			if(title.compareTo(itemTitle) == 0) {
        				movieExist = true;
        			}
        		}
        		if(!movieExist) {
        			genre.titleList.add(itemTitle);
        		}
        	}
        } // end for
        
        if(!genreExist) {
        	// if the genre doesn't exist in the list
        	genreList.add(itemGenre);
        	itemGenre.titleList.add(itemTitle);
        }
        
        // for debug-print list
        for(Genre genre : genreList) {
        	System.out.println(genre.getItem());
        	for(String title : genre.titleList) {
        		System.out.println(title);
        	}
        }
    
    }

    public void delete(MovieDBItem item) {
        Genre itemGenre = new Genre(item.getGenre());
        String itemTitle = new String(item.getTitle());
       
        MyLinkedListIterator<Genre> itrGenre = new MyLinkedListIterator<Genre>(genreList);
        
        for(Genre genre : genreList) {
        	if(genre.compareTo(itemGenre) == 0) {
        		// if the genre is in the list
        		MovieListIterator itrTitle = new MovieListIterator(genre.titleList);
        		for(String title : genre.titleList) {
        			itrTitle.next();
        			if(title.compareTo(itemTitle) == 0) {
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
    	
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        
        for(Genre genre : genreList) {
        	for(String title : genre.titleList) {
        		if(title.contains(term)) {
        			MovieDBItem item = new MovieDBItem(genre.getItem(), title);
        			results.add(item);
        		}
        			
        	}
        } // end for

        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {

        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        
        MyLinkedListIterator<Genre> itrGenre = new MyLinkedListIterator<Genre>(genreList);
            
        for(Genre genre : genreList) {
        	MovieListIterator itrTitle = new MovieListIterator(genre.titleList);
        	String genreName = new String(genre.getItem());
        	for(String movie : genre.titleList) {
        		itrTitle.next();
        		MovieDBItem item= new MovieDBItem(genreName, movie);
        		results.add(item);
        	}
        	itrGenre.next();
        }    
        
    	return results;
    }  
    
} // end class MovieDB

class Genre extends Node<String> implements Comparable<Genre> {
	MovieList titleList = new MovieList();
	
	// constructor
	public Genre(String name) {
		super(name);
		this.setNext(titleList.head);
	} // end constructor
	
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

} // end class Genre

class MovieList implements ListInterface<String> {	
	Node<String> head;
	int numItems;
	
	// constructor
	public MovieList() {
		head = new Node<String>(null);
	} // end constructor

	@Override
	public Iterator<String> iterator() {
		return new MovieListIterator(this);
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
		Node<String> curr = this.head;
		Node<String> prev = this.head;

		curr = curr.getNext();
		while((curr != null) && (item.compareTo(curr.getItem()) > 0)) {
			prev = curr;
			curr = curr.getNext();
			
		}
		Node<String> temp = curr;
		prev.insertNext(item);
		prev = prev.getNext();
		prev.setNext(temp);
	
	
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
} // end class MovieList

class MovieListIterator implements Iterator<String> {
	private MovieList list;
	private Node<String> curr;
	private Node<String> prev;

	// constructor
	public MovieListIterator(MovieList list) {
		this.list = list;
		this.curr = list.head;
		this.prev = null;
	} // end constructor

	@Override
	public boolean hasNext() {
		return curr.getNext() != null;
	}

	@Override
	public String next() {
		if (!hasNext())
			throw new NoSuchElementException();

		prev = curr;
		curr = curr.getNext();

		return curr.getItem();
	}

	@Override
	public void remove() {
		if (prev == null)
			throw new IllegalStateException("next() should be called first");
		if (curr == null)
			throw new NoSuchElementException();
		prev.removeNext();
		list.numItems -= 1;
		curr = prev;
		prev = null;
	}
}