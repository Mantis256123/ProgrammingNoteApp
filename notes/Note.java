/**
 * Provides classes for creating, managing, and organizing different types of notes
 * in the Note Management System.
 * 
 * @since 1.0
 * @version 1.0
 * @author [Mantas Kalvinskas]
 */
package notes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Represents a basic note with title, description, text, author, creation date,
 * deadline and versioning functionality.
 * 
 * @author 
 */
public class Note implements Reversable, Cloneable, Serializable {
    private String title;
    private String description;
    protected String text;
    private String author;
    private Calendar date;
    private Calendar deadline;
    private static int count = 0;
    private static final int MAX_TITLE_LENGTH = 50;

    protected List<Note> versions = new ArrayList<>();
    protected int currentVersionIndex = -1;

    /**
     * Constructs a default note with placeholder content.
     * 
     * @throws TitleLengthExceededException if the title exceeds the maximum length
     */
    public Note() throws TitleLengthExceededException {
        this("Default Title", "Default Description", "Default Text", "Anonymous", Calendar.getInstance(),
                Calendar.getInstance());
    }

    /**
     * Constructs a new note with the specified properties.
     *
    */
    public Note(String title, String description, String text, String author, Calendar date, Calendar deadline)
            throws TitleLengthExceededException {
        setTitle(title);
        this.description = (description != null && !description.isEmpty()) ? description : "Default Description";
        this.text = (text != null && !text.isEmpty()) ? text : "Default Text";
        this.author = (author != null && !author.isEmpty()) ? author : "Anonymous";
        this.date = date;
        this.deadline = deadline;
        count++;
    }
    
    //Deep cloning
    /**
     * Returns a deep clone of the note including versions and tags.
     * 
     * @return deep-cloned note object
     * @throws CloneNotSupportedException if cloning fails
     */
    @Override
    public Note clone() throws CloneNotSupportedException {
        Note cloned = (Note) super.clone();
        cloned.versions = new ArrayList<>();
        for (Note version : versions) {
            cloned.versions.add(version);
        }
        return cloned;
    }

    @Override
    public Note nextVersion() {
        if (currentVersionIndex < versions.size() - 1) {
            currentVersionIndex++;
            return versions.get(currentVersionIndex);
        }
        return getCurrentVersion();
    }

    @Override
    public Note revertVersion() {
        if (currentVersionIndex > 0) {
            currentVersionIndex--;
            return versions.get(currentVersionIndex);
        }
        return getCurrentVersion();
    }

    public void saveVersion(Note version) {
        versions.add(version);
        currentVersionIndex = versions.size() - 1;
    }

    public List<Note> getAllVersions() {
        return new ArrayList<>(versions);
    }

    public Note getCurrentVersion() {
        return currentVersionIndex >= 0 ? versions.get(currentVersionIndex) : null;
    }

    public int getVersionSize() {
        return versions.size() - 1;
    }

    public int getCurrentVersionIndex() {
        return currentVersionIndex;
    }

    /**
     * Sets the note title.
     * 
     * @param title the new title
     * @throws TitleLengthExceededException if title exceeds limit
     */
    public void setTitle(String title) throws TitleLengthExceededException {
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new TitleLengthExceededException(
                    "Title '" + title + "' exceeds maximum length of " + MAX_TITLE_LENGTH + " characters.",
                    title.length());
        } else {
            this.title = (title != null && !title.isEmpty()) ? title : "Default Title";
        }
    }

     /**
     * Checks if two notes are the same based on title and creation date.
     * 
     * @param other another note
     * @return true if same, false otherwise
     */
     public boolean isSameNote(Note other) {
         return this.getTitle().equals(other.getTitle())
                 && this.getDateRaw().equals(other.getDateRaw());
     }

    /** @return main note content */
    public String getText() {
        return text;
    }

    /** @return note title */
    public String getTitle() {
        return title;
    }

    /** @return note description */
    public String getDescription() {
        return description;
    }

    /** @return author of the note */
    public String getAuthor() {
        return author;
    }

    /** @return deadline of the note */
    public Calendar getDeadline() {
        return deadline;
    }

    /** @return creation date */
    public Calendar getDateRaw() {
        return date;
    }

    /** @return total count of notes created */
    public static int getCount() {
        return count;
    }

     /**
     * Sets the note content.
     * 
     * @param text the new text
     */
     public void setText(String text) {
         this.text = text;
     }

    /**
     * Sets the note description.
     * 
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the note author.
     * 
     * @param author new author name
     */
    public final void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Sets the note deadline.
     * 
     * @param deadline the new deadline
     */
    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }

    /**
     * Returns a string representation of the note including metadata.
     * 
     * @return string with note details
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "Title: " + title + "\nDescription: " + description + "\nText: " + text +
                "\nAuthor: " + author + "\nCreation Date: " + dateFormat.format(this.date.getTime()) + "\nDeadline: "
                + dateFormat.format(this.deadline.getTime());
    }
}