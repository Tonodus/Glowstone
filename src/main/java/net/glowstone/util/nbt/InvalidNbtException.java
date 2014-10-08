package net.glowstone.util.nbt;

/**
 * This exception is thrown when the structure of a CompoundTag is wrong, (f.e. not including elements that are necessary)
 */
public class InvalidNbtException extends RuntimeException {
    public InvalidNbtException(String msg) {
        super(msg);
    }

    public InvalidNbtException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNbtException(Throwable cause) {
        super(cause);
    }

    public InvalidNbtException() {
    }
}
