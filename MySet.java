import java.util.Set;
import java.util.Collection;
import java.lang.Iterable;
import java.util.Iterator;
import java.util.Arrays;
import java.lang.reflect.Array;

public class MySet<T> implements Set<T>, Iterable<T>
{
    private T[] backingArray;
    private int numElements;

    public MySet()
    {
        backingArray = (T[]) new Object[5];
        numElements = 0;
    }

    public boolean add(T e)
	{
        for (Object elem : backingArray)
            if (elem == null ? e == null : elem.equals(e))
                return false;

        if (numElements == backingArray.length)
		{
            T[] newArray = Arrays.copyOf(backingArray, backingArray.length * 2);
            newArray[numElements] = e;
            numElements = numElements + 1;
            backingArray = newArray;
            return true;
        }
        else
		{
          backingArray[numElements] = e;
          numElements=numElements + 1;
          return true;
        }
    }

    public boolean addAll(Collection<? extends T> c)
	{
        for(T elem : c)
            this.add(elem);

        return true;
    }

    public void clear()
	{
        T[] newArray = (T[])new Object[backingArray.length];
        numElements = 0;
        backingArray = newArray;
    }

    public boolean equals(Object o)
	{
        if (o instanceof Set && (((Set)o).size() == numElements))
		{
            for (T elem : (Set<T>)o)
               if (!this.contains(o))
                 return false;  

            return true;
        }

        return false;
    }

    public boolean contains(Object o)
	{
        for (T backingElem : backingArray)
			if (o != null && o.equals(backingElem))
				return true;

        return false;
    }

    public boolean containsAll(Collection<?> c)
	{
        for (T elem : (Set<T>)c)
            if (!(this.contains(elem)))
                return false;

        return true;
    }

    public int hashCode()
	{
		int sum = 0;

        for (T elem : backingArray)
			if(elem != null)
				sum = sum + elem.hashCode();

        return sum;
    }

    public boolean isEmpty()
	{
        if (numElements == 0)
            return true;
        else
           return false;
    }

    public boolean remove(Object o)
	{
		int i = 0;
		for (Object elem : backingArray)
		{
			if (o != null && o.equals(elem))
			{
				System.arraycopy(backingArray, i + 1, backingArray, i, numElements - i - 1);
				backingArray[numElements - 1] = null;
				numElements = numElements - 1;
				return true;
			}

			i = i + 1;
		}

		return false;
	}

    public boolean removeAll(Collection<?> c)
	{
        for (Object elem : c)
            this.remove(elem);

        return true;
    }

    public boolean retainAll(Collection<?> c)
	{
		int index = 0;
		boolean result = false;
		if (this.containsAll(c))
			result = true;

		while(index < numElements)
		{
			T e = backingArray[index];
			if (e != null && !(c.contains(e)))
				this.remove(e);
			else
				index++;
		}

		return result;
	}

    public int size()
	{
        return numElements;
    }

    public <T> T[] toArray(T[] a) throws ArrayStoreException, NullPointerException
	{
        for (int i = 0; i < numElements; i++)
           a[i] = (T)backingArray[i];

        for (int j = numElements; j < a.length; j++)
           a[j] = null;

        return a;
    }

    public Object[] toArray()
	{
       Object[] newArray = new Object[numElements];
       for (int i = 0; i < numElements; i++)
           newArray[i] = backingArray[i];

       return newArray;
    }

    public Iterator<T> iterator()
	{
        SetIterator iterator = new SetIterator();
        return iterator;
    }

    private class SetIterator implements Iterator<T>
	{
        private int currIndex;
        private T lastElement;
        public SetIterator()
		{
            currIndex = 0;
            lastElement = null;
        }

        public boolean hasNext()
		{
            while (currIndex <= numElements && backingArray[currIndex] == null)
                currIndex = currIndex + 1;

            if (currIndex <= numElements)
                return true;

            return false;
        }

        public T next()
		{
           T element = backingArray[currIndex];
           currIndex = currIndex + 1;
           lastElement = element;
           return element;
        }

        public void remove() throws UnsupportedOperationException, IllegalStateException
		{
			if (lastElement != null)
			{
               MySet.this.remove((Object)lastElement);
               numElements = numElements - 1;
            }
            else
				throw new IllegalStateException();
		}
	}
}