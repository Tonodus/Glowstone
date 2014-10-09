package net.glowstone.util.pool;

import java.lang.reflect.Constructor;

/**
 * A default pool using magic reflection to create it's elements.
 */
public class ReflectionPool<T extends Poolable> extends ObjectPool<T> {
    private Constructor<T> constructor;
    private Object[] args;

    /**
     * @param clazz the class of objects this pool contains
     * @param args the arguments for the constructor of the given class
     */
    public ReflectionPool(Class<T> clazz, Object... args) {
        Class<?>[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++)
            argTypes[i] = args.getClass();

        try {
            this.constructor = clazz.getConstructor(argTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("ReflectionPool cannot create objects for class " + clazz.getName() + ". No empty constructor!");
        }

        this.args = args;
    }

    /**
     * @param constructor the constructor to use to generate new objects
     * @param args the arguments the arguments for the constructor
     */
    public ReflectionPool(Constructor<T> constructor, Object... args) {
        this.constructor = constructor;
        this.args = args;
    }

    @Override
    protected T createObj() {
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException("ReflectionPool couldn't create object with " + constructor);
        }
    }
}
