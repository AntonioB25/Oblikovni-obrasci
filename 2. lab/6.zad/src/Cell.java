import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio Bukovac
 */
public class Cell implements Observer {

    private String exp;
    private int    value;

    private Sheet      sheet;
    private List<Cell> observers = new ArrayList<>();

    public Cell(Sheet sheet) {
        this.sheet = sheet;
    }

    public String getExp() {
        return exp;
    }

    public int getValue() {
        return value;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public void setValue(int value) {
        this.value = value;
        observers.forEach(o -> o.update(o));
    }

    public void subscribe(Cell cell) {
        observers.add(cell);
    }

    public void unsubscribe(Cell cell) {
        observers.remove(cell);
    }

    @Override
    public void update(Cell cell) {
        cell.setValue(sheet.evaluate(cell));
    }

    @Override
    public String toString() {
        return "" + value;
    }

}
