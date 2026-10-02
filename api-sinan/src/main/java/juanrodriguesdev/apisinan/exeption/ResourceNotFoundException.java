package juanrodriguesdev.apisinan.exeption;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String mensage) {
        super(mensage);
    }
}
