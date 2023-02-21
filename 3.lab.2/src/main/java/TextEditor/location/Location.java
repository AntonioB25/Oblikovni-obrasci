package TextEditor.location;

import java.util.Objects;

/**
 * @author Antonio Bukovac
 */
public class Location implements Comparable<Location> {

    private int row;
    private int column;

    public Location(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Location getStartLocation() {
        return new Location(0, 0);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Increases column by 1
     */
    public void increaseColumn() {
        this.column++;
    }

    /**
     * Increases row by 1
     */
    public void increaseRow() {
        this.row++;
    }

    /**
     * Decreases column by 1
     */
    public void decreaseColumn() {
        this.column--;
    }

    /**
     * Decreases row by 1
     */
    public void decreaseRow() {
        this.row--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return row == location.row &&
            column == location.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "Location{" +
            "row=" + row +
            ", column=" + column +
            '}';
    }

    /**
     * @param o object to compare
     * @return 1 if <code>o</code> is smaller, -1 if <code>o</code> is bigger, else 0
     */
    @Override
    public int compareTo(Location o) {
        if (this.row == o.row) {
            if (this.column == o.column) {
                return 0;
            } else {
                return this.column > o.column ? 1 : -1;
            }
        } else if (this.row > o.row) {
            return 1;
        } else {
            return -1;
        }
    }

}
