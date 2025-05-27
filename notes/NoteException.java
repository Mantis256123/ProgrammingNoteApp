package notes;

/**
 * Base exception class for all note-related exceptions in the Note Management System.
 * 
 * @author [Mantas Kalvinskas]
 * @version 1.0
 * @since 1.0
 */
public class NoteException extends Exception {
    
    /**
     * Constructs a new NoteException with no detail message.
     */
    public NoteException() {
        super();
    }

    /**
     * Constructs a new NoteException with the specified detail message.
     * 
     * @param message the detail message explaining the exception
     */
    public NoteException(String message) {
        super(message);
    }
}
