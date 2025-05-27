package notes;

/**
 * Changes the current note version to the next newest version
 */
public interface Versionable {
    Note nextVersion();
}
