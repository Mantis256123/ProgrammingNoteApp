package notes;

/**
 * Reverses a specific note to a previous version 
 */
public interface Reversable extends Versionable {
    Note revertVersion();
}