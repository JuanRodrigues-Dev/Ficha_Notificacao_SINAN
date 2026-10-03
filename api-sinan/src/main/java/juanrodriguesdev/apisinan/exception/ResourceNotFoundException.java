package juanrodriguesdev.apisinan.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String mensage) {
        super(mensage);
    }
}
