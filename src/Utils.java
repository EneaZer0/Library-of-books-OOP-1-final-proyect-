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


}
