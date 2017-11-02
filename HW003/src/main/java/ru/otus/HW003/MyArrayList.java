package ru.otus.HW003;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class MyArrayList<T> implements List<T>,RandomAccess  {

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private static final int DEFAULT_CAPACITY = 10;

    private class MyArrayListIterator implements Iterator<T> {

        protected int position = 0;

        private MyArrayListIterator() {
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next() {
            if (position >= size) {
                throw new NoSuchElementException();
            }
            return (T) items[position++];
        }
    }

    private Object[] items = new Object[DEFAULT_CAPACITY];

    private int size;

    @Override
    public String toString() {
        String result = "Kapinos V. A. MyArrayList =>>\n[";
        MyArrayListIterator iter = new MyArrayListIterator();
        while (iter.hasNext()){
            T obj = iter.next();
            result += obj.toString();
            if (iter.position < size) {
                result += ", ";
            }
        }
        return result + "]";
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
    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (size > a.length) {
            return (T1[]) Arrays.copyOf(items, size, a.getClass());
        }

        System.arraycopy(items, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(T t) {
        add(size, t);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean isChanged = false;
        for (T t : c) {
            isChanged |= add(t);
        }
        return isChanged;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        rangeCheck(index);
        if (c.isEmpty()) {
            return false;
        }
        ensureCapacity(size + c.size());
        rightShift(index, c.size());
        Iterator iter = c.iterator();
        while (iter.hasNext()) {
            items[index++] = iter.next();
        }
        return true;
    }

    @Override
    public void clear() {
        for(int i = 0; i < size; i++){
            items[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(int index) {
        rangeCheck(index);
        return (T) items[index];
    }

    @Override
    public T set(int index, T element) {
        rangeCheck(index);
        T oldElement = (T) items[index];
        items[index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, T element) {
        rangeCheck(index, size + 1);
        incCapacity();
        items[index] = element;
        size++;
    }

    @Override
    public void sort(Comparator<? super T> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        items = a;
    }

    private void ensureCapacity(int expectedCapacity) {
        if (expectedCapacity > MAX_ARRAY_SIZE || expectedCapacity < 0) {
            throw new IllegalArgumentException("The size of the array must be positive and no more than " + MAX_ARRAY_SIZE);
        }

        if (items.length < expectedCapacity) {
            items = Arrays.copyOf(items, 3 * items.length / 2 + 1);
        }
    }

    private void incCapacity() {
        ensureCapacity(size + 1);
    }

    private void rangeCheck(int index, int max) {
        if (index < 0 || index >= max) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d is out of range. Last permission index - %d", index, max)
            );
        }
    }

    private void rangeCheck(int index) {
        rangeCheck(index, size);
    }

    private void rightShift(int index, int count) {
        int moveItemsCount = size - index;
        if (moveItemsCount > 0) {
            System.arraycopy(items, index, items, index + count, moveItemsCount);
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new NotImplementedException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new NotImplementedException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
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
    public boolean contains(Object o) {
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
    public boolean remove(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
    }
}
