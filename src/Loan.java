public class Loan {
    private final LibraryItem item;
    private final Member member;
    private final int dueDay;
    private final int fineRate;

    public Loan(LibraryItem item, Member member, int borrowDay, int fineRate) {
        this.item = item;
        this.member = member;
        this.dueDay = borrowDay + member.getLoanPeriod();
        this.fineRate = fineRate;
    }

    public LibraryItem getItem() {
        return item;
    }

    public Member getMember() {
        return member;
    }

    public int getDueDay() {
        return dueDay;
    }

    public int daysLate(int today) {
        return Math.max(0, today - dueDay);
    }

    public int fineAsOf(int today) {
        return daysLate(today) * fineRate;
    }
}
