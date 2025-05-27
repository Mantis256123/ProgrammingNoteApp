package notes;

/**
 * Exception thrown when a note title exceeds the maximum allowed length.
 * 
 * @author [Mantas Kalvinskas]
 * @version 1.0
 * @since 1.0
 * @see NoteException
 */
public class TitleLengthExceededException extends NoteException {
    
    /** The actual length of the title that exceeded the maximum allowed length */
    private int titleLength;

    /**
     * Constructs a new TitleLengthExceededException with the specified detail message
     * and the length of the title that caused the exception.
     * 
     * @param message the detail message explaining the exception
     * @param titleLength the actual length of the title that exceeded the limit
     */
    public TitleLengthExceededException(String message, int titleLength) {
        super(message);
        this.titleLength = titleLength;
    }
    
    /**
     * Returns the length of the title that caused this exception to be thrown.
     * This value represents the actual character count of the title that
     * exceeded the maximum allowed length.
     * 
     * @return the length of the problematic title
     */
    public int getTitleLength() {
        return titleLength;
    }
}
