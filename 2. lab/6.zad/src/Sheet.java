import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio Bukovac
 */
public class Sheet {

    private Cell[][] table;

    private int rows;
    private int columns;

    public Sheet(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.table = new Cell[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                this.table[row][column] = new Cell(this);
            }
        }

    }

    public Cell cell(String ref) {
        String upperRef = ref.toUpperCase();

        char row = upperRef.charAt(0);
        char column = upperRef.charAt(1);

        int rowIndex;
        int columnIndex = Integer.parseInt(String.valueOf(column)) - 1;
        if (columnIndex < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (row < 'A' || row > 'Z') {
            throw new IndexOutOfBoundsException(row + " isn't valid (A-Z) or (a-z) only");
        } else {
            rowIndex = row - 'A';
        }

        return table[rowIndex][columnIndex];
    }

    public void set(String ref, String content) {
        Cell cell = cell(ref);
        //dohvati clanove i otkvaci se od njih
        getRefs(cell).forEach(
            c -> {
                c.unsubscribe(cell);
            }
        );
        cell.setExp(content);   // postavi izraz nakon odspajanja
        //provjeri je li broj
        if (isNumeric(content)) {
            cell.setValue(Integer.parseInt(content));
        } else {
            getRefs(cell).forEach(c -> c.subscribe(cell)); // pretplati se na nove celije
            //TODO: provjeri cirkularnost
            cell.setValue(evaluate(cell));
        }
    }

    public int evaluate(Cell cell) {
        int value = 0;

        if (isNumeric(cell.getExp())) {
            return cell.getValue();
        } else {
            List<Cell> refs = getRefs(cell);
            for (Cell ref : refs) {                // idi po svim celijama zbrajaj sva referencirana polja
                value += evaluate(ref);
            }
        }
        return value;
    }

    public List<Cell> getRefs(Cell cell) {
        String exp = cell.getExp();
        List<Cell> refs = new ArrayList<>();

        //provjeri je li null ili broj
        if (exp == null || isNumeric(exp)) {
            return refs;
        }

        //ako nije broj, rastavi i dohvati clanove
        String[] args = exp.split("\\+");

        for (String ref : args) {
            refs.add(cell(ref));
        }

        return refs;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void printSheet() {
        System.out.printf("%5s", "");
        for (int c = 0; c < columns; c++) {
            System.out.printf("%-5s", c + 1);
        }
        System.out.println();
        for (int row = 0; row < rows; row++) {
            System.out.printf("%-5s", Character.toString('A' + row));
            for (int column = 0; column < columns; column++) {

                System.out.printf("%-5s", this.table[row][column].getValue());
            }
            System.out.println();
        }
        System.out.println();
    }

}
