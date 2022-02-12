package bullscows;

public class NotValidNumberFormatException extends IllegalArgumentException {
    private int length;
    private int symbols;

    public NotValidNumberFormatException(int length, int symbols) {
        super(String.format("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, symbols));
        this.length = length;
        this.symbols = symbols;
    }

    public NotValidNumberFormatException(String text) {
        super(String.format("Error: \"%s\" isn't a valid number.", text));
    }
}
