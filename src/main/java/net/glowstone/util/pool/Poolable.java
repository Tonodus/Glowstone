package net.glowstone.util.pool;

/**
 * Represents a class that can be stored in a {@link net.glowstone.util.pool.Pool}.
 */
public interface Poolable {
    /**
     * Resets the object to it's initial state.
     * This is called when it's created by the {@link net.glowstone.util.pool.Pool} or {@link net.glowstone.util.pool.Pool#free(Poolable) freed} by the pool.
     */
    public void reset();
}
