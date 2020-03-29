import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/** RemoveCmd is a class which defines the command REMOVE in the program. It
 *  allows the user to remove specific books from the library. It has two
 *  functionalities, removing an author or a title. The value removed must be
 *  exactly the same as the input. The argument must also start by the word
 *  "AUTHOR" or "TITLE" to indicate what do we want to eliminate.
 *
 *  The command options are:
 *      REMOVE TITLE title_of_book  - Which searches for that title in the list and eliminates it
 *      REMOVE AUTHOR author        - Which searches all the books of that author and eliminate them all
 *
 *      i.e: REMOVE TITLE Animal Farm
 *      i.e: REMOVE AUTHOR J.K. Rowling
 */
public class RemoveCmd extends LibraryCommand {

    private String[] argumentInputArray;
    private String argumentInput;

    /** Constructor of the class RemoveCmd. It gets an argumentInput, which must have
     *  a first word equal to "TITLE" or "AUTHOR" and followed by a title or an author.
     *
     * @param argumentInput argument input as expected by the extending subclass.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public RemoveCmd(String argumentInput) {
        super(CommandType.REMOVE, argumentInput);
        this.argumentInput = argumentInput;
        this.argumentInputArray = argumentInput.split(Utils.EMPTY_SPACE);
    }

    /** Override function of parseArgument which checks if the argumentInput is valid.
     *  It can only accept argumentInputs which start with "TITLE" or "AUTHOR" and
     *  have more than just one word.
     *
     * @param argumentInput argument input for this command
     * @return a boolean value which indicates if the input argument is valid
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        boolean isParsedArgument = false;
        String[] arrayArgumentInput = argumentInput.split(Utils.EMPTY_SPACE);
        if ((arrayArgumentInput[0].equals(Utils.TITLE) || arrayArgumentInput[0].equals(Utils.AUTHOR)) && (arrayArgumentInput.length > 1)) {
            isParsedArgument = true;
        }
        return isParsedArgument;
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

        if (data.getBookData().isEmpty()) {
            System.out.println(Utils.THE_LIBRARY_HAS_NO_BOOK_ENTRIES + Utils.PLEASE_IMPORT_LIBRARY);
        }

        if(argumentInputArray[0].equals(Utils.TITLE)) {
            generalHelperRemoveTitle(list_of_books);
        } else if (argumentInputArray[0].equals(Utils.AUTHOR)) {
            generalHelperRemoveAuthors(list_of_books);
        }

    }

    /** ######################### HELPER FUNCTIONS ######################### */

    /** __________________ FUNCTIONS TO ELIMINATE BY TITLE __________________*/
    /** Helper function which organizes the elimination of the title in the list
     *  which is exactly equal to the one input. It is case sensitive and it
     *  assumes there are no books repeated (it eliminates just one and the
     *  first that matches.
     *
     *  It uses a string called removeTitle which is used as reference to eliminate
     *  the first command word and the first space to get the clean title of the
     *  book that wants to be eliminated.
     *
     * @param list_of_books takes a list with all the books loaded
     */
    private void generalHelperRemoveTitle(List<BookEntry> list_of_books) {
        String removeTitle = Utils.TITLE + Utils.EMPTY_SPACE;
        String cleanArgument = argumentInput.substring(removeTitle.length());
        int counter = removeTitle(list_of_books, cleanArgument);

        if(counter == 0) {
            System.out.println(cleanArgument + ": not found.");
        }
    }

    /** Helper function which searches and eliminates the titles.
     *
     * @param list_of_books takes the list with all the books loaded
     * @param cleanArgument takes the cleaned title of the book which
     *                      is going to be eliminated
     * @return the number of books that have been eliminated and updates
     *                      the list of books loaded.
     */
    private int removeTitle(List<BookEntry> list_of_books, String cleanArgument) {
        int counter = 0;
        /** _________________________ ITERATOR VERSION _________________________*/
        Iterator<BookEntry> titleRemover = list_of_books.iterator();
        while (titleRemover.hasNext()) {
            if (cleanArgument.equals(titleRemover.next().getTitle())) {
                titleRemover.remove();
                System.out.printf( cleanArgument + ": removed successfully.");
                counter++;
                break;
            }
        }

        /** _________________________ FOR LOOP VERSION  _________________________*/
        /**
        for (int i = 0; i < list_of_books.size(); i++) {
            if (cleanArgument.equals(list_of_books.get(i).getTitle())) {
                list_of_books.remove(i);
                System.out.printf( cleanArgument + ": removed successfully.");
                counter++;
                break;
            }
        } */

        return counter;
    }

    /** ________________ FUNCTIONS TO ELIMINATE BY AUTHOR _________________*/
    /** Helper function which organizes the elimination of all the books in the list
     *  that have been written by the author who was input. It assumes there could be
     *  more that one book from the same author. It is case sensitive.
     *
     *  It uses a string called removeAuthor which is used as reference to
     *  clean the argument and remove the word AUTHOR and the following space.
     *
     * @param list_of_books takes a list with all the books loaded
     */
    private void generalHelperRemoveAuthors(List<BookEntry> list_of_books) {
        String removeAuthor = Utils.AUTHOR + Utils.EMPTY_SPACE;
        String cleanArgument = argumentInput.substring(removeAuthor.length());

        int counter = removeAuthors(cleanArgument.toUpperCase(), list_of_books);

        if (counter == 0) {
            System.out.printf("0 books removed for author: " + cleanArgument);
        } else {
            System.out.println(counter + " books removed for author: " + cleanArgument);
        }
    }

    private int removeAuthors(String cleanArgument, List<BookEntry> list_of_books) {
        /** _________________________ ITERATOR VERSION _________________________*/
        Iterator<BookEntry> authorRemover = list_of_books.iterator();
        int counter = 0;
        while (authorRemover.hasNext()) {
            String[] authors = authorRemover.next().getAuthors();
            authors = authorsToCapital(authors);
            for (int k = 0; k < authors.length; k++) {
                if (cleanArgument.equals(authors[k])) {
                    authorRemover.remove();
                    counter++;
                }
            }
        }

        /** _________________________ FOR LOOP VERSION  _________________________*/
        /**
         for (int i = 0; i < list_of_books.size(); i++) {
            String[] authors = list_of_books.get(i).getAuthors();
            authors = authorsToCapital(authors);
            for (int k = 0; k < authors.length; k++) {
                if (cleanArgument.toUpperCase().equals(authors[k])) {
                    list_of_books.remove(i);
                    counter++;
                }
            }
         }*/
        return counter;
    }






    /** __________ FUNCTION TO CAPITALIZE ALL THE AUTHORS __________*/
    /**
     *
     * @param authors gets and authors array
     * @return the same authors array with all elements capitalize
     */
    private String[] authorsToCapital (String[] authors) {
        for (int i = 0; i < authors.length; i++) {
            authors[i] = authors[i].toUpperCase();
        }
        return authors;
    }


}
