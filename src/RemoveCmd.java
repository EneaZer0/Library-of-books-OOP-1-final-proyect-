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
        this.argumentInputArray = argumentInput.split(Utils.WHITE_SPACE);
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
        String[] arrayArgumentInput = argumentInput.split(Utils.WHITE_SPACE);
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
        generalRemove(list_of_books, argumentInputArray[0]);

        /**
        if(argumentInputArray[0].equals(Utils.TITLE)) {
            generalHelperRemoveTitle(list_of_books);
        } else if (argumentInputArray[0].equals(Utils.AUTHOR)) {
            generalHelperRemoveAuthors(list_of_books);
        }*/

    }

    /** ######################### HELPER FUNCTIONS ######################### */
    /** Helper function which organizes the elimination of books.
     *  It gets a reference string to remove it from the argument input and
     *  used the function remover to determine the number of books that have
     *  been eliminated and at the same time, that remover is the one in
     *  charge of removing them.
     *
     * @param list_of_books takes the list with all the books loaded.
     * @param typeRemove it is always equal to AUTHOR or TITLE and it determines
     *                   which eliminating method is used.
     */
    private void generalRemove (List<BookEntry> list_of_books, String typeRemove) {
        String removeReferenceString = "";
        String cleanArgument;
        int counter = 0;

        switch (typeRemove) {
            case Utils.TITLE:
                removeReferenceString = Utils.TITLE + Utils.WHITE_SPACE;
                break;
            case Utils.AUTHOR:
                removeReferenceString = Utils.AUTHOR + Utils.WHITE_SPACE;
                break;
        }

        cleanArgument = argumentInput.substring(removeReferenceString.length());
        counter = remover(list_of_books, typeRemove, cleanArgument, counter);
        printerOfResults(typeRemove, cleanArgument, counter);
    }

    /** Helper function which searches and eliminates depending on the type of removed that it is
     *  asked (TITLE or AUTHOR).
     *
     * @param list_of_books takes the list with all the books loaded.
     * @param typeRemove it is always equal to AUTHOR or TITLE and it determines
     *                   which eliminating method is used.
     * @param cleanArgument takes the cleaned title of the book which
     *                   is going to be eliminated.
     * @param counter this is the number of books that have been eliminated.

     * @return the number of books that have been eliminated and updates
     *                   the list of books loaded.
     */
    private int remover (List<BookEntry> list_of_books, String typeRemove, String cleanArgument, int counter) {
        Iterator<BookEntry> removerFromListOfBooks = list_of_books.iterator();
        switch (typeRemove) {
            case Utils.TITLE:
                while (removerFromListOfBooks.hasNext()) {
                    if (cleanArgument.equals(removerFromListOfBooks.next().getTitle())) {
                        removerFromListOfBooks.remove();
                        System.out.printf(cleanArgument + ": removed successfully.");
                        counter++;
                        break;
                    }
                }
                break;

            case Utils.AUTHOR:
                while (removerFromListOfBooks.hasNext()) {
                    String[] authors = removerFromListOfBooks.next().getAuthors();
                    for (int k = 0; k < authors.length; k++) {
                        if (cleanArgument.equals(authors[k])) {
                            removerFromListOfBooks.remove();
                            counter++;
                        }
                    }
                }
                break;

        }

        return counter;
    }

    /** Helper function which prints the final message after removing books of the library
     *
     * @param typeRemove it is always equal to AUTHOR or TITLE and it determines
     *                   which eliminating method is used.
     * @param cleanArgument takes the cleaned title of the book which
     *                   is going to be eliminated.
     * @param counter this is the number of books that have been eliminated.
     */
    private void printerOfResults(String typeRemove, String cleanArgument, int counter) {
        /** ____________________ PRINTING THE RESULTS ____________________ */
        if (counter == 0) {
            switch (typeRemove) {
                case Utils.TITLE:
                    System.out.println(cleanArgument + ": not found.");
                    break;
                case Utils.AUTHOR:
                    System.out.printf("0 books removed for author: " + cleanArgument);
                    break;
            }
        } else if (typeRemove.equals(Utils.AUTHOR)) {
            System.out.println(counter + " books removed for author: " + cleanArgument);
        }
    }

}
