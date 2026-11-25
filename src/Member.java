import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Member {
    private final String id;
    private final String name;
    private final List<Loan> loans = new ArrayList<>();

    protected Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loans);
    }

    public abstract int getMaxItems();

    public abstract int getLoanPeriod();
}

class Student extends Member {
    public Student(String id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxItems() {
        return 3;
    }

    @Override
    public int getLoanPeriod() {
        return 14;
    }
}

class Staff extends Member {
    public Staff(String id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxItems() {
        return 4;
    }

    @Override
    public int getLoanPeriod() {
        return 21;
    }
}

class Faculty extends Member {
    public Faculty(String id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxItems() {
        return 5;
    }

    @Override
    public int getLoanPeriod() {
        return 30;
    }
}
