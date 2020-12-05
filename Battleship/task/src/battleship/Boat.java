package battleship;

public class Boat {
    private final String name;
    private final int length;
    private boolean isSank;

    public Boat(String name, int length) {
        this.name = name;
        this.length = length;
        this.isSank = false;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public boolean isSank() {
        return isSank;
    }

    public void setSank(boolean sank) {
        isSank = sank;
    }
}
