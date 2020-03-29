import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *  SearchCmd is a class which defines the command SEARCH in the program and returns
 *  a list of books which have the word searched, independently of being upper or
 *  lower case. It can ONLY search one word.
 *
 *  The command options are:
 *      SEARCH value
 *
 *      i.e: SEARCH Harry
 */
public class SearchCmd extends LibraryCommand {

    private String argumentInput;

    /** Constructor of the class SearchCmd. It gets an argumentInput, which must be just one word
     *
     * @param argumentInput argument input as expected by the extending subclass.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public SearchCmd(String argumentInput) {
        super(CommandType.SEARCH, argumentInput);
        this.argumentInput = argumentInput;
    }

    /** _________________________ OVERRIDE FUNCTIONS _________________________*/
    /** Override functions of parseArguments which checks if the argumentInput is valid.
     *  It can only accept one word.
     *
     *  It will be consider invalid inputs of one word such as:
     *                 "a "           - Adding an space at the end
     *                 " a"           - Adding an space at the beginning
     *                 " a "          - Adding an space at the beginning & end
     *
     * @param argumentInput argument input for this command
     * @return a boolean value that indicates if the input argument is valid
     */
    @Override
    protected boolean parseArguments(String argumentInput){
        boolean isParseArgument = false;

        if (!argumentInput.endsWith(Utils.EMPTY_SPACE)) {
            String[] numberOfWords = argumentInput.split(Utils.EMPTY_SPACE);

            if (numberOfWords.length == 1 && !argumentInput.isBlank()) {
                isParseArgument = true;
            }
        }

        return isParseArgument;
    }

    /** Override function of execute which defines what this command does.
     *  Data cannot be null.
     *
     *  In case the library is empty it returns "The library has no book entries" and
     *      "Please import a library"
     *
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {

        /** _________________________ ERROR CHECKING _________________________ */
        Objects.requireNonNull(data, Utils.ERROR_DATA_NULL);

        /** _________________________ EXECUTE DEFINITION _________________________ */

        List<BookEntry> list_of_books = data.getBookData();
        List<String> booksFound = new ArrayList<>();

        if (data.getBookData().isEmpty()) {
            System.out.println(Utils.THE_LIBRARY_HAS_NO_BOOK_ENTRIES + Utils.PLEASE_IMPORT_LIBRARY);
        }

        /* Add to booksFound list, the results of the search of titles which are valid */
        searchBooks(list_of_books, booksFound);

        if (booksFound.size() == 0) {
            System.out.println("No hits found for search term: " + argumentInput);
        } else {
            for (int i = 0; i < booksFound.size(); i++) {
                System.out.println(booksFound.get(i));
            }
        }
    }

    /** _________________________ HELPER FUNCTIONS _________________________*/
    /** Helper function which does the search on the list of books, checking word by word
     *  Transforming everything to Upper case, in order to make it case insensitive.
     *  The results are added to booksFound list.
     *
     * @param list_of_books
     * @param booksFound
     */
    private void searchBooks (List<BookEntry> list_of_books, List<String> booksFound) {
        for (int i = 0; i < list_of_books.size(); i++) {
            /* Get each of the titles splitted and in uppercase */
            String[] split_title = list_of_books.get(i).getTitle().toUpperCase().split(Utils.EMPTY_SPACE);
            String searchedTitle = argumentInput.toUpperCase();
            for (int k = 0; k < split_title.length; k++) {
                if (searchedTitle.equals(split_title[k])) {
                    booksFound.add(list_of_books.get(i).getTitle());
                    break;
                }
            }
        }
    }

}
