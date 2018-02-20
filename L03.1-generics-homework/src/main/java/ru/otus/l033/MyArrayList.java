package ru.otus.l033;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyArrayList<T> implements List<T> {
    private Object[] items;
    private final int INITIAL_SIZE = 10;
    private final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private int size = 0;

    MyArrayList() {
        items = new Object[INITIAL_SIZE];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>();
    }

    @Override
    public Object[] toArray() {
        Object[] data = new Object[size];
        System.arraycopy(items, 0, data, 0, size);
        return data;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new NotImplementedException();
    }

    @Override
    public boolean add(T t) {
        checkContainerSize();
        items[size] = t;
        size++;
        return true;
    }

    private void checkContainerSize() {
        if (items.length == size) {
            int newLength = size + size / 2;
            if (newLength < 0 || newLength > MAX_ARRAY_SIZE) {
                throw new OutOfMemoryError();
            }
            Object[] array = new Object[newLength];
            System.arraycopy(items, 0, array, 0, size);
            items = array;
        }
    }

    @Override
    public boolean remove(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        throw new NotImplementedException();
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) items[index];
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);
        Object old = items[index];
        items[index] = element;
        return (T) old;
    }

    @Override
    public void add(int index, T element) {
        throw new NotImplementedException();
    }

    @Override
    public T remove(int index) {
        throw new NotImplementedException();
    }

    @Override
    public int indexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator<>();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new NotImplementedException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException();
    }

    private class MyIterator<T> implements Iterator<T> {
        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public T next() {
            int index = cursor;
            cursor++;
            return (T) items[index];
        }
    }

    private class MyListIterator<T> extends MyIterator<T>
            implements ListIterator<T> {
        private int lastIndex = -1;

        @Override
        public T next() {
            Object item = super.next();
            lastIndex = cursor - 1;
            return (T) item;
        }

        @Override
        public boolean hasPrevious() {
            throw new NotImplementedException();
        }

        @Override
        public T previous() {
            throw new NotImplementedException();
        }

        @Override
        public int nextIndex() {
            throw new NotImplementedException();
        }

        @Override
        public int previousIndex() {
            throw new NotImplementedException();
        }

        @Override
        public void remove() {
            throw new NotImplementedException();
        }

        @Override
        public void set(T t) {
            if (lastIndex < 0) {
                throw new IllegalStateException();
            }
            checkIndex(lastIndex);
            items[lastIndex] = t;
        }

        @Override
        public void add(T t) {
            throw new NotImplementedException();
        }
    }
}
