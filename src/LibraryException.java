public abstract class LibraryException extends Exception {
    protected LibraryException(String message) {
        super(message);
    }
}

class ItemNotFoundException extends LibraryException {
    ItemNotFoundException(String id) {
        super("item " + id + " not found");
    }
}

class MemberNotFoundException extends LibraryException {
    MemberNotFoundException(String id) {
        super("member " + id + " not found");
    }
}

class ItemNotAvailableException extends LibraryException {
    private ItemNotAvailableException(String message) {
        super(message);
    }

    static ItemNotAvailableException onLoan(String id) {
        return new ItemNotAvailableException(id + " is on loan");
    }

    static ItemNotAvailableException notBorrowable(String id) {
        return new ItemNotAvailableException(id + " cannot be borrowed");
    }
}

class BorrowLimitExceededException extends LibraryException {
    BorrowLimitExceededException(String memberId, int limit) {
        super(memberId + " has reached the limit of " + limit);
    }
}

class ItemNotOnLoanException extends LibraryException {
    ItemNotOnLoanException(String id) {
        super(id + " is not on loan");
    }
}
