import java.util.HashMap;
import java.util.Map;

public class Library {
    private final Map<String, LibraryItem> items = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();
    private int today = 0;

    public int getToday() {
        return today;
    }
}
