package student.com.passin.domain.attendee.exceptions;

public class AttendeeAlreadyRegisteredException extends RuntimeException{
    public AttendeeAlreadyRegisteredException(String e){
        super(e);
    }

}
