package TextEditor.location;

/**
 * @author Antonio Bukovac
 */
public class LocationRange {

    private Location start;
    private Location end;

    public LocationRange(Location start, Location end) {
        this.start = start;
        this.end = end;
    }

    //    public LocationRange getOrderedRange(){
    //        if(start.compareTo(end) <= 0){
    //            return new LocationRange(start, end);
    //        } else {
    //            return new LocationRange(end, start);
    //        }
    //    }

    public boolean isEmpty() {
        return start.equals(end);
    }

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
        sortPositions();
    }

    public Location getEnd() {
        return end;
    }

    public void setEnd(Location end) {
        this.end = end;
        sortPositions();
    }

    @Override
    public String toString() {
        return "LocationRange{" +
            "start=" + start +
            ", end=" + end +
            '}';
    }

    //end must be bigger than start
    private void sortPositions() {
        if (end.compareTo(start) < 0) {  // if end is smaller, switch values
            Location pom = start;
            start = end;
            end = pom;
        }
    }

}
