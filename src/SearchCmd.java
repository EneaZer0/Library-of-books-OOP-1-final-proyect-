import java.util.ArrayList;
import java.util.Iterator;
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

    /** Constructor of the class SearchCmd. It gets an argumentInput which must be just one word.
     *
     * @param argumentInput argument input as expected by the extending subclass.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public SearchCmd(String argumentInput) {
        super(CommandType.SEARCH, argumentInput);
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

        String[] numberOfWords = argumentInput.strip().split(Utils.WHITE_SPACE);

        if ((numberOfWords.length == 1 && !argumentInput.contains(Utils.NEXT_LINE)  && !argumentInput.contains(Utils.NEXT_TAB)) && !argumentInput.isBlank()) {
            this.argumentInput = argumentInput;
            isParseArgument = true;
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
        Utils.emptyDataWarning(data);
        /** _______________________ EXECUTE DEFINITION _______________________ */
        List<BookEntry> list_of_books = data.getBookData();
        List<String> booksFound = new ArrayList<>();

        /* Add to booksFound list, the results of the search of titles which are valid */
        String searchedTitle = argumentInput.strip().toUpperCase();

        Utils.searchBooks(list_of_books, booksFound, searchedTitle);

        if (booksFound.size() == 0) {
            System.out.println("No hits found for search term: " + argumentInput);
        } else {
            for (int i = 0; i < booksFound.size(); i++) {
                System.out.println(booksFound.get(i));
            }
        }
    }




}
