import java.util.List;
import java.util.Scanner;

public class Main {
    private final Library library = new Library();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            String line = input.nextLine();
            try {
                execute(line);
            } catch (LibraryException e) {
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }

    private void execute(String line) throws LibraryException {
        String[] fields = line.split("\\|", -1);
        switch (fields[0]) {
            case "ADD_BOOK":
                library.addBook(fields[1], fields[2], fields[3]);
                break;
            case "ADD_MAGAZINE":
                library.addMagazine(fields[1], fields[2], fields[3]);
                break;
            case "ADD_REFERENCE":
                library.addReference(fields[1], fields[2], fields[3]);
                break;
            case "ADD_MEMBER":
                library.addMember(fields[1], fields[2], fields[3]);
                break;
            case "BORROW":
                Loan loan = library.borrow(fields[1], fields[2]);
                System.out.println("OK due on day " + loan.getDueDay());
                return;
            case "RETURN":
                int fine = library.returnItem(fields[1]);
                System.out.println("OK fine " + fine);
                return;
            case "ADVANCE":
                library.advance(Integer.parseInt(fields[1]));
                System.out.println("OK today is day " + library.getToday());
                return;
            case "INVENTORY":
                printInventory();
                return;
            default:
                throw new IllegalArgumentException("Unknown command: " + fields[0]);
        }
        System.out.println("OK");
    }

    private void printInventory() {
        List<LibraryItem> items = library.inventory();
        if (items.isEmpty()) {
            System.out.println("NONE");
            return;
        }
        for (LibraryItem item : items) {
            System.out.println(item.getId() + "|" + item.getType() + "|" + describeStatus(item));
        }
    }

    private String describeStatus(LibraryItem item) {
        if (!item.isBorrowable()) {
            return "NOT_BORROWABLE";
        }
        if (item.isOnLoan()) {
            return "ON_LOAN|" + item.getCurrentLoan().getMember().getId();
        }
        return "AVAILABLE";
    }
}
