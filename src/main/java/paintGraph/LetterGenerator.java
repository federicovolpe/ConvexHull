package paintGraph;

public class LetterGenerator {
    private int index;
    
    public LetterGenerator() {
        this.index = 0;
    }

    /**
     * Returns the next Excel-style column label.
     * @return The next column label.
     */
    public String getNext() {
        index++;
        return getExcelColumnLabel(index);
    }

    /**
     * Converts a 1-based index to an Excel-style column label.
     * @param index The 1-based index.
     * @return The Excel-style column label.
     */
    private String getExcelColumnLabel(int index) {
        StringBuilder label = new StringBuilder();

        while (index > 0) {
            index--; // Decrement index to make it 0-based
            label.insert(0, (char) ('A' + index % 26));
            index /= 26;
        }

        return label.toString();
    }
}
