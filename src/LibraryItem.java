public abstract class LibraryItem {
    private final String id;
    private final String title;
    private Loan currentLoan;

    protected LibraryItem(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public abstract String getType();

    public boolean isBorrowable() {
        return this instanceof Borrowable;
    }

    public boolean isOnLoan() {
        return currentLoan != null;
    }

    public Loan getCurrentLoan() {
        return currentLoan;
    }

    void checkAvailable() throws ItemNotAvailableException {
        if (!isBorrowable()) {
            throw ItemNotAvailableException.notBorrowable(id);
        }
        if (isOnLoan()) {
            throw ItemNotAvailableException.onLoan(id);
        }
    }

    void setCurrentLoan(Loan loan) {
        currentLoan = loan;
    }
}

interface Borrowable {
    int getFineRate();
}

class Book extends LibraryItem implements Borrowable {
    private final String author;

    public Book(String id, String title, String author) {
        super(id, title);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String getType() {
        return "BOOK";
    }

    @Override
    public int getFineRate() {
        return 2;
    }
}

class Magazine extends LibraryItem implements Borrowable {
    private final String issue;

    public Magazine(String id, String title, String issue) {
        super(id, title);
        this.issue = issue;
    }

    public String getIssue() {
        return issue;
    }

    @Override
    public String getType() {
        return "MAGAZINE";
    }

    @Override
    public int getFineRate() {
        return 1;
    }
}

class ReferenceBook extends LibraryItem {
    private final String author;

    public ReferenceBook(String id, String title, String author) {
        super(id, title);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String getType() {
        return "REFERENCE";
    }
}
