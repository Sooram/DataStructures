
/******************************************************************************
 * MovieDB의 인터페이스에서 공통으로 사용하는 클래스.
 */
public class MovieDBItem implements Comparable<MovieDBItem> {

    private final String genre;
    private final String title;

    public MovieDBItem(String genre, String title) {
        if (genre == null) throw new NullPointerException("genre");
        if (title == null) throw new NullPointerException("title");

        this.genre = genre;
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int compareTo(MovieDBItem other) {
	 	int comp;
	 	String originalGenre = this.genre;
	 	String newGenre = other.genre;
	 	String originalTitle = this.title;
	 	String newTitle = other.title;
    	
    	if((originalGenre.length() > newGenre.length()) || 
    			((originalGenre.length() == newGenre.length()) && (originalGenre.compareTo(newGenre) > 0))) { 
    		// original genre > new genre
    		comp = 1;
    	}
    	else if(originalGenre.compareTo(newGenre) == 0) { 
    		// original genre = new genre
    		if(originalTitle.compareTo(newTitle) == 0) {
    			comp = 0;
    		}
    		else comp = 1;
    	}
    	else { 
    		// original value < new value
    		comp = -1;
    	}
    	
    	return comp;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MovieDBItem other = (MovieDBItem) obj;
        if (genre == null) {
            if (other.genre != null)
                return false;
        } else if (!genre.equals(other.genre))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

}
