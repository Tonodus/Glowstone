package net.glowstone.util.pool;

/**
 * A pool for objects of the type T.
 *
 * @param <T> The type of the objects this pool contains
 */
public interface Pool<T extends Poolable> {
    /**
     * Gets a new object from this pool. This object should be freed by calling {@link #free(Object)}.
     *
     * @return the new object
     */
    public T get();

    /**
     * Frees this objects, marking as as ready to use.
     *
     * @param object The object to free
     */
    public void free(T object);

    /**
     * Grows/Shrinks the amount of objects to the given size.
     *
     * @param size The new amount of free objects this pool contains
     * @throws java.lang.IllegalArgumentException if size < 0
     */
    public void growTo(int size) throws IllegalArgumentException;

    /**
     * Clears all references this pool contains.
     */
    public void clear();
}
