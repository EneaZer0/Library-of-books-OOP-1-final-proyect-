import java.util.Arrays;
import java.util.Objects;

/**
 * Immutable class encapsulating data for a single book entry.
 */
public class BookEntry {



    private final String title;
    private final String[] authors;
    private final float rating;
    private final String ISBN;
    private final int pages;


    /** Constructor of the Class BookEntry, it gets values that form part of a book and checks if the
     *  inputs are valid, the title, authors and ISBN must not be null. If authors' array is not null,
     *  it must be also checked that any of the authors inside is null. Pages must be a non negative
     *  value and ratings must be between 0 and 5 to be valid.
     *
     * @param title as the title of the book
     * @param authors as the group of authors who wrote the book
     * @param rating as the ratings received for the book
     * @param ISBN as the identification of the book
     * @param pages as the number of pages that the book has
     */
    public BookEntry (String title, String[] authors, float rating, String ISBN, int pages) {

        /** _________________________ ERROR CHECKING _________________________ */
        Objects.requireNonNull(title,"The title" + Utils.NOT_NULL);
        Objects.requireNonNull(authors,"The authors" + Utils.NOT_NULL);
        Objects.requireNonNull(ISBN,"The ISBN" + Utils.NOT_NULL);
        for (int i = 0; i < authors.length; i++) {
            if (authors == null) {
                throw new NullPointerException(Utils.ERROR_NULL);
            }
        }
        if (!(rating >= 0 && rating <= 5)) {
            throw new IllegalArgumentException(Utils.ERROR_ILLEGAL);
        }
        if (pages < 0) {
            throw new IllegalArgumentException(Utils.ERROR_ILLEGAL);
        }

        /** ____________________ CONSTRUCTOR DEFINITIONS -____________________ */
        this.title = title;
        this.authors = authors;
        this.ISBN = ISBN;
        this.rating = rating;
        this.pages = pages;
    }


    /** _________________________ GET FUNCTIONS _________________________ */
    /**This is a function which returns the title of a BookEntry Object
     *
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /** This is a function which returns the authors of a BookEntry Object
     *
     * @return the author of the book
     */
    public String[] getAuthors() {
        return authors;
    }

    /** This is a function which returns the ratings of a BookEntry Object
     *
     * @return the rating of the book
     */
    public float getRating() {
        return rating;
    }

    /**This is a function which returns the ISBN of a BookEntry Object
     *
     * @return the ISBN of the book
     */
    public String getISBN() {
        return ISBN;
    }

    /** This is a function which returns the pages of a BookEntry Object
     *
     * @return the pages of the book
     */
    public int getPages() {
        return pages;
    }

    /** _________________________ OVERRIDE FUNCTIONS _________________________*/
    /** This is a function which compares a BookEntry Object to another one, returning a boolean value
     *
     * @param o get the Object BookEntry to check if it is equal
     * @return a boolean value determining
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookEntry)) return false;
        BookEntry bookEntry = (BookEntry) o;
        return Float.compare(bookEntry.getRating(), getRating()) == 0 &&
                getPages() == bookEntry.getPages() &&
                getTitle().equals(bookEntry.getTitle()) &&
                Arrays.equals(getAuthors(), bookEntry.getAuthors()) &&
                getISBN().equals(bookEntry.getISBN());
    }

    /** This is a function which returns the hashCode of a BookEntry Object
     *
     * @return the hashCode code of the object
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(getTitle(), getRating(), getISBN(), getPages());
        result = 31 * result + Arrays.hashCode(getAuthors());
        return result;
    }

    /** This is a function which returns the bock with all its information as a String
     *
     * @return a String with the information of the book
     */
    @Override
    public String toString() {
        return
                title + "\n" +
                        "by " + Utils.transformAuthor(authors) + "\n" +
                        "Rating: " + Utils.transformRating(rating) + "\n" +
                        "ISBN: " + ISBN + "\n" +
                        pages + " pages";

    }
}
