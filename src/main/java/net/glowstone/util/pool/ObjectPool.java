package net.glowstone.util.pool;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The default {@link net.glowstone.util.pool.Pool}.
 */
public abstract class ObjectPool<T extends Poolable> implements Pool<T> {
    private Queue<T> freeObjs;

    public ObjectPool() {
        this(10);
    }

    public ObjectPool(int capacity) {
        freeObjs = new LinkedList<>();
        growTo(capacity);
    }

    @Override
    public T get() {
        if (freeObjs.isEmpty())
            grow();

        return freeObjs.poll();
    }

    protected void grow() {
        growTo(freeObjs.size() * 2);
    }

    @Override
    public void free(T object) {
        object.reset();
        freeObjs.offer(object);
    }

    @Override
    public void growTo(int size) throws IllegalArgumentException {
        if (size < 0) throw new IllegalArgumentException("Size must be >= 0.");

        int toAdd = freeObjs.size() - size;
        for (int i = 0; i < toAdd; i++)
            free(createObj());
    }

    @Override
    public void clear() {
        freeObjs.clear();
    }

    protected abstract T createObj();
}
