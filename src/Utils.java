import java.util.*;

/**
 *  This is a class Utils which contains constants used across the project
 */
public class Utils {


    /** __________________________________ SPACING __________________________________*/

    /**
     *  This is a constant of a white space
     */
    public static final String WHITE_SPACE = " ";
    /**
     *  This is a constant that sets the spaces at the GROUP command, in front of the titles or titles
     */
    public static final String SPACING_FOR_GROUPING = "    ";


    /** __________________________________ FORMAT __________________________________ */

    /**
     *  String which indicates the format of the file, which must be ".csv" (this allow changing the type of files that are being loaded)
     */
    public static final String FORMAT = ".csv";
    /**
     *  String which is used in the GROUP command and goes before titles of authors
     */
    public static final String GROUP_HASH = "## ";
    /**
     *  String which determines the number of decimals of a book ratings in the function transformRating used to create the toString function in BookEntry class
     */
    public static final String NUMBER_OF_DECIMALS_FORMAT = "%.2f";


    /** __________________________________ COMMON STRINGS USED__________________________________ */

    /**
     *  String which stores title
     */
    public static final String TITLE = "TITLE";
    /**
     *  String which stores author
     */
    public static final String AUTHOR = "AUTHOR";
    /**
     *  String used in ADD when the file loaded is empty
     */
    public static final String THE_LIBRARY_HAS_NO_BOOK_ENTRIES = "The library has no book entries.";
    /**
     *  String used when there is no book entries
     */
    public static final String PLEASE_IMPORT_LIBRARY = " Please import a library.";
    /**
     *  String used in GROUP command as a common message on the way data is displayed
     */
    public static final String GROUP_BY = "Grouped data by ";
    /**
     *  String used in ListCmd as an accepted command
     */
    public static final String SHORT = "short";
    /**
     *  String used in ListCmd as an accepted command
     */
    public static final String LONG = "long";
    /**
     *  String used in Group for those books that start with numbers
     */
    public static final String GROUP_NUMBERS = "[0-9]";

    /** __________________________________ LOGIC CONSTANTS__________________________________ */

    /**
     *  Integer which is equal to the length of the full alphabet
     */
    public static final int ALPHABET_LENGTH = 26;
    /**
     *  Integer which is equal to the number of the groups which organize titles with numeric values
     */
    public static final int GROUP_OF_NUMBERS = 1;
    /**
     *  Integer which is equal to the ascii value of the letter "A"
     */
    public static final int ASCII_CASE_FOR_A = 65;
    /**
     *  Integer which is equal to the ascii value of the letter "Z"
     */
    public static final int ASCII_CASE_FOR_Z = 90;

    /** __________________________________ ERROR MESSAGES __________________________________ */

    /**
     *  String which contains the Null Error Message used in most of throw new NullPointerExceptions
     */
    public static final String ERROR_NULL = "ERROR: NullPointerException";
    /**
     *  String which contains theIllegal Error Message used in most of the throw new IllegalArgumentExceptions
     */
    public static final String ERROR_ILLEGAL = "ERROR: IllegalArgumentException";
    /**
     *  String which is used commonly and indicates that something cannot be null
     */
    public static final String NOT_NULL = " cannot be null";
    /**
     *  Error string used in all the execute functions
     */
    public static final String ERROR_DATA_NULL = "ERROR, data cannot be null";

    /** ################################## HELPER FUNCTIONS ################################## */

    /** Helper function which is used in the classes: List, Remove & Search and display
     *  a feedback message to the user if there are no books loaded in the program.
     *
     * @param data contains all the information that has been loaded into the program.
     */
    protected static void emptyDataWarning (LibraryData data) {
        if (data.getBookData().isEmpty()) {
            System.out.println(Utils.THE_LIBRARY_HAS_NO_BOOK_ENTRIES + Utils.PLEASE_IMPORT_LIBRARY);
        }
    }

    /** ______________________________ HELPERS BOOK ENTRY CLASS ______________________________ */
    /** This function is in charged of transforming the authors array to be printed in the toString function
     *
     * @param authors correspond to the authors array who wrote the book
     * @return a String with all the authors who wrote it in the correct format for the function toString
     */
    protected static String transformAuthor (String[] authors) {
        String final_authors = Arrays.toString(authors);
        final_authors = final_authors.substring(1, final_authors.length()-1);
        return final_authors;
    }

    /** This function is in charged of transforming the rating into a String and with a determine number of decimals
     * which can be changed in Utils class (in case it is needed). It is used in the toString function.
     *
     * @param rating correspond to the rating which the book received
     * @return a String with the rating value in the format that is wanted in the toString function
     */
    protected static String transformRating (float rating) {
        String final_rating = String.format(Utils.NUMBER_OF_DECIMALS_FORMAT, rating);
        return final_rating;
    }

    /** _______________________________ HELPERS SEARCH CMD CLASS _____________________________ */
    /** Helper function which does the search on the list of books, checking word by word
     *  Transforming everything to Upper case, in order to make it case insensitive.
     *  The results are added to booksFound list.
     *
     * @param list_of_books takes the list with all the books loaded.
     * @param booksFound the list which contains just the books that satisfy
     *                   the search.
     */
    protected static void searchBooks(List<BookEntry> list_of_books, List<String> booksFound, String searchedTitle) {
        Iterator<BookEntry> titlesIterator = list_of_books.iterator();

        while (titlesIterator.hasNext()) {
            String title = titlesIterator.next().getTitle();
            String workTitle = title.toUpperCase();
            if (workTitle.contains(searchedTitle)) {
                booksFound.add(title);
            }
        }
    }

    /** ______________________________ HELPERS REMOVE CMD CLASS ______________________________ */
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
    protected static void generalRemove (List<BookEntry> list_of_books, String typeRemove, String argumentInput) {
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
        counter = Utils.remover(list_of_books, typeRemove, cleanArgument, counter);
        Utils.printerOfResults(typeRemove, cleanArgument, counter);
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
    private static int remover (List<BookEntry> list_of_books, String typeRemove, String cleanArgument, int counter) {
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
    private static void printerOfResults(String typeRemove, String cleanArgument, int counter) {
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

    /** ______________________________ HELPERS GROUP CMD CLASS _______________________________ */
    /**
     * Helper function which is in charged of printing the initial messages used in the
     * Group by command.
     */
    protected static void printInitialGroupMessage(String argumentInput) {
        switch (argumentInput) {
            case Utils.TITLE:
                System.out.println(Utils.GROUP_BY + Utils.TITLE);
                break;
            case Utils.AUTHOR:
                System.out.println(Utils.GROUP_BY + Utils.AUTHOR);
                break;
        }
    }

    /** Helper function which is in charged of managing the Group process of the list
     *  depending on the type of grouping that is being done (determined by the typeCommand).
     *
     * @param list_of_books takes the list with all the books loaded.
     * @param typeCommand determines the type of group that is being done (TITLE or AUTHOR)
     */
    protected static void mainGroup (List<BookEntry> list_of_books, String typeCommand, List<List<String>> list_of_titles) {

        List<String> list_of_authors = new ArrayList<>();
        List<List<String>> list_of_authors_and_titles = new ArrayList<>();

        Utils.classifier(list_of_books,list_of_authors, typeCommand, list_of_titles);

        if (typeCommand.equals(Utils.AUTHOR)) {
            Collections.sort(list_of_authors);
            for (int i = 0; i < list_of_authors.size(); i++) {
                list_of_authors_and_titles.add(new ArrayList<>());
            }
        }

        Utils.printGroupResults(list_of_books, list_of_authors, list_of_authors_and_titles, typeCommand, list_of_titles);
    }

    /** Helper function which is in charged of classifying or ordering the titles by
     *  alphabetical order or by author.
     *
     * @param list_of_books takes the list with all the books loaded.
     * @param list_of_authors takes the list of all the authors of the library
     * @param typeCommand determines the type of group that is being done (TITLE or AUTHOR)
     */
    private static void classifier (List<BookEntry> list_of_books, List<String> list_of_authors, String typeCommand,
                                    List<List<String>> list_of_titles) {
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

    /** Helper function which is in charged of printing all the Group results after being
     *  classified.
     *
     * @param list_of_books takes the list with all the books loaded.
     * @param list_of_authors takes the list of all the authors of the library
     * @param list_of_authors_and_titles takes the list of all the titles organized by author (2 dimension list)
     * @param typeCommand determines the type of group that is being done (TITLE or AUTHOR)
     */
    private static void printGroupResults(List<BookEntry> list_of_books, List<String> list_of_authors,
                                          List<List<String>> list_of_authors_and_titles, String typeCommand,
                                          List<List<String>> list_of_titles) {
        switch (typeCommand) {

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
