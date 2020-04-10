import java.util.*;

/**
 *  GroupCmd is a class which defines the command GROUP in the program and returns
 *  a list of books which have been sorted and divided by the first letter of their
 *  title or by their author.
 *
 *  It displays an organized list of all the books in the library
 *
 *  The command options are:
 *      GROUP TITLE         - Which displays the titles grouped by the first letter of the title.
 *      GROUP AUTHOR        - Which displays the titles grouped by author, if a book is
 *                              written by two or more authors, it will appear more than once.
 *                              Not all the authors start with capital but it is fixed to be
 *                              case insensitive so that order is kept.
 */
public class GroupCmd extends LibraryCommand {

    private String argumentInput;
    private List<List<String>> list_of_titles;

    /** Constructor of the class GroupCmd. It gets an argumentInput which must be just one word
     *  and it must be only equal to TITLE or AUTHOR
     *
     * @param argumentInput argument input as expected by the extending subclass.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);
        this.argumentInput = argumentInput.strip();
        this.list_of_titles = new ArrayList<>();        // LIST OF LISTS
        for (int i = 0; i < Utils.ALPHABET_LENGTH + Utils.GROUP_OF_NUMBERS; i++) {
            list_of_titles.add(new ArrayList<>());      // DECLARE DE THE FULL LENGTH TO CLASSIFY THE FULL ALPHABET
        }
    }

    /** _________________________ OVERRIDE FUNCTIONS _________________________*/
    /** Override function of parseArgument which checks if the argumentInput is valid
     *  It can only accept one word equal to TITLE or AUTHOR
     *
     * @param argumentInput argument input for this command
     * @return a boolean value indicating if the input argument is valid
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        boolean isArgumentParsed = false;
        String argumentStriped = argumentInput.strip();
        if (argumentStriped.equals(Utils.TITLE) || argumentStriped.equals(Utils.AUTHOR)) {
            isArgumentParsed = true;
        }
        return isArgumentParsed;
    }

    /** Override functions of execute which defines what this command does.
     *  Data cannot be null.
     *
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {

        /** _________________________ ERROR CHECKING _________________________ */
        Objects.requireNonNull(data, Utils.ERROR_DATA_NULL);

        /** _________________________ ERROR CHECKING _________________________ */
        if (data.getBookData().isEmpty()) {
            System.out.println(Utils.THE_LIBRARY_HAS_NO_BOOK_ENTRIES);
        } else {
            List<BookEntry> list_of_books = data.getBookData();
            Utils.printInitialGroupMessage(argumentInput);
            Utils.mainGroup(list_of_books, argumentInput, list_of_titles);
        }
    }

}
