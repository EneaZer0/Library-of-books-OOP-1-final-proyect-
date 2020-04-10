import java.nio.file.Paths;
import java.util.Objects;

/**
 *  AddCmd is a class which defines the command ADD in the program. It
 *  adds or loads new files in csv format to the library to work with.
 *  It is followed by a path to the file.
 *
 *  The command options are:
 *      ADD path
 *
 *     Example files have been added in data, in src and in the main folder
 *     to test different path alternatives.
 *
 *     i.e: ADD data/books01.csv
 *     i.e: ADD src/books01.csv
 *     i.e: ADD books01.csv
 */
public class AddCmd extends LibraryCommand {

    private String argumentInput;

    /** Constructor of the class AddCmd. It gets an argumentInput, which must be
     * a valid path to a file of the format csv.
     * This input cannot be null or empty.
     *
     * @param argumentInput argument input as expected by the extending subclass.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public AddCmd(String argumentInput) {
        super(CommandType.ADD, argumentInput);
    }

    /** _________________________ OVERRIDE FUNCTIONS _________________________*/
    /** Override function of parseArguments which checks if the argumentInput is valid.
     *  It cannot be blank and it must end in the correct format csv (which could be
     *  changed later)
     *
     * @param argumentInput argument input for this command
     * @return a boolean value indicating if the input argument is valid
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        boolean isArgumentParsed = false;
        if (!argumentInput.isBlank()) {
            // Substring which just takes last characters corresponding to the format
            String formatString = argumentInput.strip().substring(argumentInput.strip().length() - Utils.FORMAT.length());
            if (formatString.equals(Utils.FORMAT)) {
                this.argumentInput = argumentInput;
                isArgumentParsed = true;
            }
        }
        return isArgumentParsed;
    }

    /** Override function of execute which defines what the ADD command does.
     *  Data cannot be null.
     *
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, Utils.ERROR_DATA_NULL);
        Objects.requireNonNull(argumentInput, Utils.ERROR_NULL);

        try {
            data.loadData(Paths.get(argumentInput.strip()));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }

    }

}
