import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GroupCmd extends LibraryCommand {



    private String argumentInput;
    private List<List<String>> list_of_titles;

    /**
     * Create the specified command and initialise it with
     * the given command argument.
     *
     * @param argumentInput argument input as expected by the extending subclass.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);

        this.argumentInput = argumentInput;
        this.list_of_titles = new ArrayList<>();        // LIST OF LISTS
        for (int i = 0; i < Utils.ALPHABET_LENGTH; i++) {
            list_of_titles.add(new ArrayList<>());      // DECLARE DE THE FULL LENGTH TO CLASSIFY THE FULL ALPHABET
        }
    }

    /**
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

    /**
     *
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {
        if (data != null) {
            if (data.getBookData().size() != 0) {
                List<BookEntry> list_of_books = data.getBookData();
                if(argumentInput.equals(Utils.TITLE)) {
                    System.out.println(Utils.GROUP_BY + Utils.TITLE);
                    groupTITLE(list_of_books);
                } else if (argumentInput.equals(Utils.AUTHOR)) {
                    System.out.println(Utils.GROUP_BY + Utils.AUTHOR);
                    groupAUTHOR(list_of_books);
                }
            } else {
                System.out.println(Utils.THE_LIBRARY_HAS_NO_BOOK_ENTRIES);
            }
        } else {
            throw new NullPointerException(Utils.ERROR_DATA_NULL);
        }
    }

    /** __________ FUNCTION TO GROUP BY TITLE __________*/
    private void groupTITLE (List<BookEntry> list_of_books){
        // ADD AND CLASSIFY IN LIST_OF_TITLES EACH TITLE
        for (int i = 0; i < list_of_books.size(); i++) {
            String title = list_of_books.get(i).getTitle();
            list_of_titles.get(title.charAt(0)-65).add(title);
        }

        for (int i = 0; i < list_of_titles.size(); i++) {
            if (list_of_titles.get(i).size() != 0) {
                System.out.println(Utils.GROUP_HASH + (char) (i+65));
                for (int k = 0; k < list_of_titles.get(i).size(); k++) {
                    System.out.println(Utils.SPACING_FOR_GROUPING + list_of_titles.get(i).get(k));
                }
            }
        }
    }
    /** __________ FUNCTION TO GROUP BY AUTHOR __________*/
    private void groupAUTHOR (List<BookEntry> list_of_books){
        List<String> list_of_authors = new ArrayList<>();
        for (int i = 0; i < list_of_books.size(); i++) {
            for (int k = 0; k < list_of_books.get(i).getAuthors().length; k++) {
                if (!list_of_authors.contains(list_of_books.get(i).getAuthors()[k])) {
                    list_of_authors.add(list_of_books.get(i).getAuthors()[k]);
                }
            }
        }
        Collections.sort(list_of_authors);
        List<List<String>> list_of_authors_and_titles = new ArrayList<>();
        for (int i = 0; i < list_of_authors.size(); i++) {
            list_of_authors_and_titles.add(new ArrayList<>());
        }
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
    }

}
