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
        Utils.emptyDataWarning(data);
        /** _________________________ EXECUTE DEFINITION _________________________ */
        List<BookEntry> list_of_books = data.getBookData();
        Utils.generalRemove(list_of_books, argumentInputArray[0], argumentInput);

    }

}
