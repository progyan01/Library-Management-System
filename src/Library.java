import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Library {
    private static final Comparator<Loan> BY_DUE_DAY = Comparator.comparingInt(Loan::getDueDay)
            .thenComparing(loan -> loan.getItem().getId());

    private final Map<String, LibraryItem> items = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();
    private int today = 0;

    public void addBook(String id, String title, String author) {
        items.put(id, new Book(id, title, author));
    }

    public void addMagazine(String id, String title, String issue) {
        items.put(id, new Magazine(id, title, issue));
    }

    public void addReference(String id, String title, String author) {
        items.put(id, new ReferenceBook(id, title, author));
    }

    public void addMember(String id, String name, String type) {
        Member member;
        switch (type) {
            case "STUDENT":
                member = new Student(id, name);
                break;
            case "STAFF":
                member = new Staff(id, name);
                break;
            case "FACULTY":
                member = new Faculty(id, name);
                break;
            default:
                throw new IllegalArgumentException("Unknown member type: " + type);
        }
        members.put(id, member);
    }

    private LibraryItem findItem(String id) throws ItemNotFoundException {
        LibraryItem item = items.get(id);
        if (item == null) {
            throw new ItemNotFoundException(id);
        }
        return item;
    }

    private Member findMember(String id) throws MemberNotFoundException {
        Member member = members.get(id);
        if (member == null) {
            throw new MemberNotFoundException(id);
        }
        return member;
    }

    public Loan borrow(String memberId, String itemId) throws LibraryException {
        Member member = findMember(memberId);
        LibraryItem item = findItem(itemId);
        item.checkAvailable();
        member.checkCanBorrow();

        int fineRate = ((Borrowable) item).getFineRate();
        Loan loan = new Loan(item, member, today, fineRate);
        item.setCurrentLoan(loan);
        member.addLoan(loan);
        return loan;
    }

    public int returnItem(String itemId) throws LibraryException {
        LibraryItem item = findItem(itemId);
        Loan loan = item.getCurrentLoan();
        if (loan == null) {
            throw new ItemNotOnLoanException(itemId);
        }
        int fine = loan.fineAsOf(today);
        loan.getMember().removeLoan(loan);
        item.setCurrentLoan(null);
        return fine;
    }

    public void advance(int days) {
        today += days;
    }

    public List<LibraryItem> search(String text) {
        String query = text.toLowerCase(Locale.ROOT);
        List<LibraryItem> matches = new ArrayList<>();
        for (LibraryItem item : items.values()) {
            if (item.getTitle().toLowerCase(Locale.ROOT).contains(query)) {
                matches.add(item);
            }
        }
        matches.sort(Comparator.comparing(LibraryItem::getTitle).thenComparing(LibraryItem::getId));
        return matches;
    }

    public List<LibraryItem> inventory() {
        List<LibraryItem> result = new ArrayList<>(items.values());
        result.sort(Comparator.comparing(LibraryItem::getId));
        return result;
    }

    public List<Loan> loansOf(String memberId) throws MemberNotFoundException {
        List<Loan> loans = new ArrayList<>(findMember(memberId).getLoans());
        loans.sort(BY_DUE_DAY);
        return loans;
    }

    public List<Loan> overdue() {
        List<Loan> loans = new ArrayList<>();
        for (LibraryItem item : items.values()) {
            Loan loan = item.getCurrentLoan();
            if (loan != null && loan.daysLate(today) > 0) {
                loans.add(loan);
            }
        }
        // The earliest due day has the most days late.
        loans.sort(BY_DUE_DAY);
        return loans;
    }

    public int getToday() {
        return today;
    }
}
