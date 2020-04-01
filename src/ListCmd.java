import java.util.List;
import java.util.Objects;

/**
 *  ListCmd is a class which defines the command LIST in the program and returns the
 *  information of the books which have been preloaded in a long form (with all its data)
 *  or short (just the titles)
 *
 *  The command options are:
 *      LIST            -Which is the same as using LIST short.
 *      LIST short      -Displays a list of just the titles loaded.
 *      LIST long       -Displays all the information about the books loaded.
 *
 */
public class ListCmd extends LibraryCommand {

    private String argumentInput;

    /** Constructor of the class ListCmd. It gets an argumentInput, which can be empty, short or long.
     *
     * @param argumentInput argument input as expected by the extending subclass.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public ListCmd(String argumentInput) {
        super(CommandType.LIST, argumentInput);
        this.argumentInput = argumentInput;
    }

    /** _________________________ OVERRIDE FUNCTIONS _________________________*/
    /** Override function of parseArguments which checks if the argumentInput is valid.
     * It can only be blank, long or short.
     *
     * @param argumentInput argument input for this command.
     * @return a boolean value which indicates if the input argument is valid.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        boolean isParsedArgument = false;
        if (argumentInput.equals(Utils.LONG)|| argumentInput.equals(Utils.SHORT) || argumentInput.isBlank()) {
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

        if ((argumentInput.equals(Utils.SHORT)  || argumentInput.equals(Utils.LONG) || argumentInput.isBlank()) && list_of_books.size() > 0) {
            System.out.println(list_of_books.size() + " books in library:");

            /** LIST short or LIST case*/
            if (argumentInput.isBlank() || argumentInput.equals(Utils.SHORT)) {
                for (int i = 0; i < list_of_books.size(); i++) {
                    System.out.println(list_of_books.get(i).getTitle());
                }
            /** LIST long case*/
            } else if (argumentInput.equals(Utils.LONG)) {
                for (int i = 0; i < list_of_books.size(); i++) {
                    System.out.println(list_of_books.get(i).toString() + "\n");
                }
            }
        }
    }
}