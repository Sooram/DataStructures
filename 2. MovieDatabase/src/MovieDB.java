
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
        	if(genre.equals(itemGenre)) { // if the genre is already in the list   		
        		genreExist = true;
        		for(String title : genre.titleList) { // examine whether we have the movie in the list     			
        			if(title.equals(itemTitle)) {
        				movieExist = true;
        			}
        		}
        		if(!movieExist) {
        			genre.titleList.add(itemTitle);
        		}
        	}
        } // end for
       
        if(!genreExist) { 
        	genreList.add(itemGenre);
    		itemGenre.titleList.add(itemTitle);
        }
    
    }

    public void delete(MovieDBItem item) {
        Genre itemGenre = new Genre(item.getGenre());
        String itemTitle = new String(item.getTitle());
       
        MyLinkedListIterator<Genre> itrGenre = new MyLinkedListIterator<>(genreList);
          
        for(Genre genre : genreList) {
        	itrGenre.next();
        	if(genre.equals(itemGenre)) { // if the genre is in the list
        		MyLinkedListIterator<String> itrTitle = new MyLinkedListIterator<>(genre.titleList);
        		for(String title : genre.titleList) { // examine whether we have the movie in the list	
        			itrTitle.next();
        			if(title.equals(itemTitle)) {
        				itrTitle.remove();
        			}
        		}
        	} // end if
          	if(genre.titleList.isEmpty()) { // if the genre has no movie in it then delete it     		
        		itrGenre.remove();
        	}     	
        } // end for

    }

    public MyLinkedList<MovieDBItem> search(String term) {
    	
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        
        for(Genre genre : genreList) {
        	for(String title : genre.titleList) {
        		if(title.contains(term)) { // insert movies containing the given term into the result array
        			MovieDBItem item = new MovieDBItem(genre.getItem(), title);
        			results.add(item);
        		}    			
        	}
        } // end for

        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {

        MyLinkedList<MovieDBItem> results = new MyLinkedList<>();
        
        MyLinkedListIterator<Genre> itrGenre = new MyLinkedListIterator<>(genreList);
            
        for(Genre genre : genreList) { // copy 'genreList' to 'results'      	
        	MyLinkedListIterator<String> itrTitle = new MyLinkedListIterator<>(genre.titleList);
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
	MyLinkedList<String> titleList = new MyLinkedList<>();
	
	// constructor
	public Genre(String name) {
		super(name);
		this.setNext(titleList.head);
	} // end constructor
	
	@Override
	public int compareTo(Genre o) {
	 	int comp;
	 	String currGenre = this.getItem();
	 	String newGenre = o.getItem();
    	
  
	 	if(currGenre.compareTo(newGenre) > 0) {
	 		// original value > new value
    		comp = 1;
    	}
    	else if(currGenre.compareTo(newGenre) == 0) { 
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

	    result = prime * result + ((this.getItem() == null) ? 0 : this.getItem().hashCode());

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