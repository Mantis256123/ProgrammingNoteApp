package notes;

import java.util.Calendar;

/**
 * Represents a testing note with test case, test status, and mandatory test option fields.
 * This class extends the base Note class to provide specialized functionality for
 * managing software testing documentation, test cases, and test execution tracking.
 * 
 * @author [Mantas Kalvinskas]
 * @version 1.0
 * @since 1.0
 * @see Note
 * @see TestStatus
 */
public class TestingNote extends Note {
    
    /** The test case identifier or description associated with this testing note */
    private String testCase;
    
    /** Flag indicating whether this test is mandatory for release or optional */
    private boolean isMandatoryTest;
    
    /** Current execution status of the test case */
    private TestStatus testStatus;

    /**
     * Enumeration representing the possible statuses for a test case execution.
     * 
     * <p>This enum provides a type-safe way to represent test execution states
     * and ensures consistency across the testing workflow.</p>
     * 
     * @since 1.0
     */
    public enum TestStatus {
        /** Test case has been created but not yet executed */
        PENDING,
        
        /** Test case has been executed and all assertions passed */
        PASSED,
        
        /** Test case has been executed and one or more assertions failed */
        FAILED
    }

    /**
     * Default constructor that creates a new TestingNote with default values.
     * Initializes the note with empty test case, non-mandatory status, and PENDING status.
     * 
     * @throws TitleLengthExceededException if the default title exceeds the maximum allowed length
     */
    public TestingNote() throws TitleLengthExceededException {
        super();
        this.testCase = "";
        this.isMandatoryTest = false;
        this.testStatus = TestStatus.PENDING;
    }

    /**
     * Constructs a new TestingNote with the specified parameters.
     * 
     * @param title the title of the testing note (must not exceed maximum length)
     * @param description a brief description of the test purpose
     * @param text detailed test description, steps, or expected results
     * @param author the author who created this test note (typically QA engineer or developer)
     * @param date the creation date of the test note
     * @param deadline the deadline for test execution (can be null if no deadline)
     * @param testCase the test case identifier, name, or detailed test case description
     * @param testStatus the initial status of the test (typically PENDING for new tests)
     * @param isMandatoryTest true if this test must pass before release, false for optional tests
     * @throws TitleLengthExceededException if the title exceeds the maximum allowed length
     */
    public TestingNote(String title, String description, String text, String author, Calendar date, Calendar deadline,
            String testCase, TestStatus testStatus, boolean isMandatoryTest) throws TitleLengthExceededException {
        super(title, description, text, author, date, deadline);
        this.testCase = testCase;
        this.isMandatoryTest = isMandatoryTest;
        this.testStatus = testStatus;
    }

    /**
     * Creates and returns a deep copy of this TestingNote.
     * This method overrides the clone method from the parent Note class.
     * The cloned note will have identical content but be a separate object instance.
     * 
     * @return a new TestingNote object that is a copy of this instance
     * @throws CloneNotSupportedException if the object cannot be cloned
     */
    @Override
    public TestingNote clone() throws CloneNotSupportedException {
        return (TestingNote) super.clone();
    }
    
    /**
     * Returns whether this test is marked as mandatory for release.
     * Mandatory tests typically represent critical functionality that must work
     * before software can be released to production.
     * 
     * @return true if this is a mandatory test, false if it's optional
     */
    public boolean getIsMandatoryTest() {
        return isMandatoryTest;
    }

    /**
     * Returns the test case identifier or description.
     * This typically contains the test case ID, name, or detailed description
     * of what the test is designed to verify.
     * 
     * @return the test case information as a String, or empty string if not set
     */
    public String getTestcase() {
        return testCase;
    }

    /**
     * Sets whether this test is mandatory for release.
     * 
     * @param isMandatoryTest true to mark this test as mandatory (must pass for release),
     *                       false to mark as optional
     */
    public void setIsMandatoryTest(boolean isMandatoryTest) {
        this.isMandatoryTest = isMandatoryTest;
    }

    /**
     * Sets the test case identifier or description.
     * 
     * @param testCase the test case information to store. Can include test ID,
     *                name, steps, or any relevant test case details.
     *                Can be null or empty string.
     */
    public void setTestcase(String testCase) {
        this.testCase = testCase;
    }

    /**
     * Updates the execution status of this test case.
     * This method should be called after test execution to record the result.
     * 
     * <p>Typical workflow:</p>
     * <ol>
     *   <li>Create test with PENDING status</li>
     *   <li>Execute the test case</li>
     *   <li>Call updateStatus() with PASSED or FAILED based on results</li>
     * </ol>
     * 
     * @param status the new test status to set. Should reflect the actual
     *              execution result of the test case.
     * @see TestStatus
     */
    public void updateStatus(TestStatus status) {
        this.testStatus = status;
    }

    /**
     * Returns the current execution status of this test case.
     * 
     * @return the current TestStatus (PENDING, PASSED, or FAILED)
     * @see TestStatus
     */
    public TestStatus getTestStatus() {
        return testStatus;
    }

    /**
     * Returns a string representation of this TestingNote.
     * The string includes all information from the parent Note class
     * plus the testing-specific fields: test case, mandatory flag, and test status.
     * 
     * <p>The format includes:</p>
     * <ul>
     *   <li>All base Note information (title, description, author, dates, etc.)</li>
     *   <li>Test Case: [test case information]</li>
     *   <li>Is Mandatory: [true/false]</li>
     *   <li>Test Status: [PENDING/PASSED/FAILED]</li>
     * </ul>
     * 
     * @return a formatted string containing all test note information
     */
    @Override
    public String toString() {
        return super.toString() +
                "\nTest Case: " + testCase +
                "\nIs Mandatory: " + isMandatoryTest + 
                "\nTest Status: " + testStatus;
    }
}
