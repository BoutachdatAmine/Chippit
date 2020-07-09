package be.ehb.project.chippit.exception;

public class EmailAlreadyUsedException extends Exception {
    public EmailAlreadyUsedException() {
        super("Email is already used");
    }
}
