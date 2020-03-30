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
        this.argumentInput = argumentInput;
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
        if (argumentInput.equals(Utils.TITLE) || argumentInput.equals(Utils.AUTHOR)) {
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
            printInitialGroupMessage();
            mainGroup(list_of_books, argumentInput);
        }
    }

    /** ######################### HELPER FUNCTIONS ######################### */
    /** Helper function which is in charged of managing the Group process of the list
     *  depending on the type of grouping that is being done (determined by the typeCommand).
     *
     * @param list_of_books takes the list with all the books loaded.
     * @param typeCommand determines the type of group that is being done (TITLE or AUTHOR)
     */
    private void mainGroup (List<BookEntry> list_of_books, String typeCommand) {

        List<String> list_of_authors = new ArrayList<>();
        List<List<String>> list_of_authors_and_titles = new ArrayList<>();

        classifier(list_of_books,list_of_authors, typeCommand);

        if (typeCommand.equals(Utils.AUTHOR)) {
            Collections.sort(list_of_authors);
            for (int i = 0; i < list_of_authors.size(); i++) {
                list_of_authors_and_titles.add(new ArrayList<>());
            }
        }

        printGroupResults(list_of_books, list_of_authors, list_of_authors_and_titles, typeCommand);
    }

    /** Helper function which is in charged of classifying or ordering the titles by
     *  alphabetical order or by author.
     *
     * @param list_of_books takes the list with all the books loaded.
     * @param list_of_authors takes the list of all the authors of the library
     * @param typeCommand determines the type of group that is being done (TITLE or AUTHOR)
     */
    private void classifier (List<BookEntry> list_of_books, List<String> list_of_authors, String typeCommand) {
        switch (typeCommand){
            case Utils.TITLE:
                for (int i = 0; i < list_of_books.size(); i++) {
                    String title = list_of_books.get(i).getTitle();
                    if ((int) title.toUpperCase().charAt(0) >= (int) 'A' && (int) title.toUpperCase().charAt(0) <= (int) 'Z') {
                        list_of_titles.get((int)title.toUpperCase().charAt(0)- Utils.ASCII_CASE_FOR_A).add(title);
                    } else {
                        list_of_titles.get(list_of_titles.size()-Utils.GROUP_OF_NUMBERS).add(title);
                    }
                }
                break;

            case Utils.AUTHOR:
                for (int i = 0; i < list_of_books.size(); i++) {
                    for (int k = 0; k < list_of_books.get(i).getAuthors().length; k++) {
                        if (!list_of_authors.contains(list_of_books.get(i).getAuthors()[k])) {
                            list_of_authors.add(list_of_books.get(i).getAuthors()[k]);
                        }
                    }
                }
                break;
        }
    }

    /**
     * Helper function which is in charged of printing the initial messages used in the
     * Group by command.
     */
    private void printInitialGroupMessage() {
        switch (argumentInput) {
            case Utils.TITLE:
                System.out.println(Utils.GROUP_BY + Utils.TITLE);
                break;
            case Utils.AUTHOR:
                System.out.println(Utils.GROUP_BY + Utils.AUTHOR);
                break;
        }
    }

    /** Helper function which is in charged of printing all the Group results after being
     *  classified.
     *
     * @param list_of_books takes the list with all the books loaded.
     * @param list_of_authors takes the list of all the authors of the library
     * @param list_of_authors_and_titles takes the list of all the titles organized by author (2 dimension list)
     * @param typeCommad determines the type of group that is being done (TITLE or AUTHOR)
     */
    private void printGroupResults(List<BookEntry> list_of_books, List<String> list_of_authors,
                                     List<List<String>> list_of_authors_and_titles, String typeCommad) {
        switch (typeCommad) {

            case Utils.TITLE:
                for (int i = 0; i < list_of_titles.size(); i++) {
                    if (!list_of_titles.get(i).isEmpty()) {
                        if (i < (list_of_titles.size() - Utils.GROUP_OF_NUMBERS)) {
                            System.out.println(Utils.GROUP_HASH + (char) (i + Utils.ASCII_CASE_FOR_A));
                            for (int k = 0; k < list_of_titles.get(i).size(); k++) {
                                System.out.println(Utils.SPACING_FOR_GROUPING + list_of_titles.get(i).get(k));
                            }
                        } else {
                            System.out.println(Utils.GROUP_HASH + Utils.GROUP_NUMBERS);
                            for (int k = 0; k < list_of_titles.get(i).size(); k++) {
                                System.out.println(Utils.SPACING_FOR_GROUPING + list_of_titles.get(i).get(k));
                            }
                        }
                    }
                }
                break;

            case Utils.AUTHOR:
                for (int i = 0; i < list_of_authors_and_titles.size(); i++){
                    String author = list_of_authors.get(i);
                    System.out.println(Utils.GROUP_HASH + author);
                    for (int k = 0; k < list_of_books.size(); k++) {
                        if (Arrays.asList(list_of_books.get(k).getAuthors()).contains(author)) {
                            // SAVING THE TITLES IN ORDER IN THE LIST OF AUTHORS AND TITLES
                            list_of_authors_and_titles.get(i).add(list_of_books.get(k).getTitle());
                            System.out.println(Utils.SPACING_FOR_GROUPING + list_of_books.get(k).getTitle());
                        }
                    }
                }
                break;
        }
    }

}
