package notes;

import java.util.Calendar;

/**
 * Represents a programming note with programming language and coding snippet fields.
 * This class extends the base Note class to provide specialized functionality for
 * storing and managing programming-related notes including code snippets and language information.
 * 
 * @author [Mantas Kalvinskas]
 * @version 1.0
 * @since 1.0
 * @see Note
 */
public class ProgrammingNote extends Note {
    
    /** The programming language associated with this note (e.g., "Java", "Python", "C++") */
    private String language;
    
    /** The code snippet or programming content stored in this note */
    private String codingSnippet;

    /**
     * Default constructor that creates a new ProgrammingNote with default values.
     * Initializes the note with "Unknown language" and an empty coding snippet.
     * 
     * @throws TitleLengthExceededException if the default title exceeds the maximum allowed length
     */
    public ProgrammingNote() throws TitleLengthExceededException {
        super();
        this.language = "Unknown language";
        this.codingSnippet = "";
    }

    /**
     * Constructs a new ProgrammingNote with the specified parameters.
     * 
     * @param title the title of the programming note (must not exceed maximum length)
     * @param description a brief description of the programming note
     * @param text the main text content of the note
     * @param author the author who created this note
     * @param date the creation date of the note
     * @param deadline the deadline associated with this note (can be null if no deadline)
     * @param codingSnippet the code snippet to be stored in this note
     * @param language the programming language of the code snippet
     * @throws TitleLengthExceededException if the title exceeds the maximum allowed length
     */
    public ProgrammingNote(String title, String description, String text, String author, Calendar date,
            Calendar deadline, String codingSnippet, String language) throws TitleLengthExceededException {
        super(title, description, text, author, date, deadline);
        this.language = language;
        setCodingSnippet(codingSnippet);
    }

    /**
     * Creates and returns a deep copy of this ProgrammingNote.
     * This method overrides the clone method from the parent Note class.
     * 
     * @return a new ProgrammingNote object that is a copy of this instance
     * @throws CloneNotSupportedException if the object cannot be cloned
     */
    @Override
    public ProgrammingNote clone() throws CloneNotSupportedException {
        return (ProgrammingNote) super.clone();
    }

    /**
     * Returns the programming language associated with this note.
     * 
     * @return the programming language as a String (e.g., "Java", "Python", "C++")
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the programming language for this note.
     * 
     * @param language the programming language to set (e.g., "Java", "Python", "C++")
     *                 Can be null, though it's recommended to use "Unknown" instead
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Sets the coding snippet for this programming note.
     * This method allows updating the code content stored in the note.
     * 
     * @param newSnippet the new code snippet to store in this note.
     *                   Can be null or empty string for notes without code content.
     */
    public void setCodingSnippet(String newSnippet) {
        this.codingSnippet = newSnippet;
    }

    /**
     * Returns the coding snippet stored in this programming note.
     * 
     * @return the code snippet as a String, or empty string if no code is stored
     */
    public String getCodingSnippet() {
        return codingSnippet;
    }

    /**
     * Sets the main text content of this note.
     * This method overrides the parent class method to provide direct access
     * to the text field for programming notes.
     * 
     * @param text the main text content to set for this note
     */
    @Override
    public void setText(String text) {
        super.text = text;
    }

    /**
     * Returns a string representation of this ProgrammingNote.
     * The string includes all the information from the parent Note class
     * plus the programming language and code snippet specific to this class.
     * 
     * @return a formatted string containing all note information including
     *         title, description, text, author, dates, language, and code snippet
     */
    @Override
    public String toString() {
        return super.toString() + "\nLanguage: " + language + "\nCode Snippet: " + getCodingSnippet();
    }
}
