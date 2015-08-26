package util;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public final class ListMap<K, V> extends AbstractMap<K, V> implements Cloneable, Serializable, Map<K, V>
{
	private static final long serialVersionUID = -4213104337910808865L;

	static class LinkedNode<K, V> implements Entry<K, V>
	{
		final K key;
		V value;
		LinkedNode<K, V> prev, next;
		int idx;

		LinkedNode (K key, V value, int idx)
		{
			this.key = key;
			this.value = value;
			this.idx = idx;
		}

		public final void setLink(LinkedNode<K, V> prev, LinkedNode<K, V> next)
		{
			this.prev = prev;
			this.next = next;
		}

		public final LinkedNode<K, V> first()
		{
			LinkedNode<K, V> pres = this, n = pres, f;

			do
			{
				f = n;
			} while ((n = n.prev) != null);

			return f;
		}

		public final LinkedNode<K, V> last()
		{
			LinkedNode<K, V> pres = this, n = pres, l;

			do
			{
				l = n;
			} while ((n = n.next) != null);

			return l;
		}

		public final K getKey()
		{
			return key;
		}

		public final V getValue()
		{
			return value;
		}

		public final int getIndex()
		{
			return idx;
		}

		public final String toString()
		{
			return key + "=" + value;
		}

		public final int hashCode()
		{
			return Objects.hashCode(key) ^ Objects.hashCode(value);
		}

		public final V setValue(V newValue)
		{
			V oldValue = value;
			value = newValue;
			return oldValue;
		}

		public final boolean equals(Object o)
		{
			if (o == this) return true;
			if (o instanceof Entry)
			{
				Entry<?, ?> e = (Entry<?, ?>) o;

				if (Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue())) return true;
			}

			return false;
		}
	}

	static Class<?> comparableClassFor(Object x)
	{
		if (x instanceof Comparable)
		{
			Class<?> c;
			Type[] ts, as;
			Type t;
			ParameterizedType p;

			if ((c = x.getClass()) == String.class) return c;
			if ((ts = c.getGenericInterfaces()) != null) for (int i = 0; i < ts.length; ++i) if (((t = ts[i]) instanceof ParameterizedType) && ((p = (ParameterizedType) t).getRawType() == Comparable.class) && (as = p.getActualTypeArguments()) != null && as.length == 1 && as[0] == c) return c;
		}

		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static int compareComparables(Class<?> kc, Object k, Object x)
	{
		return (x == null || x.getClass() != kc ? 0 : ((Comparable) k).compareTo(x));
	}
	transient LinkedNode<K, V> table;
	transient Set<K> keySet;
	transient Collection<V> values;
	transient Set<Entry<K, V>> entrySet;
	transient int size;

	public ListMap() { }

	public ListMap(Map<? extends K, ? extends V> m)
	{
		putMapEntries(m);
	}

	final void putMapEntries(Map<? extends K, ? extends V> m)
	{
		int s = m.size();

		if (s > 0)
		{
			for (Entry<? extends K, ? extends V> e : m.entrySet())
			{
				K key = e.getKey();
				V value = e.getValue();
				putVal(-1, key, value);
			}
		}
	}

	public int size()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		if (table != null && size > 0)
		{
			LinkedNode<K, V> l = table;

			sb = sb.append("{");

			do
			{
				sb = sb.append(l.toString() + ((l.idx == size - 1) ? "" : ", "));
			} while ((l = l.next) != null);

			sb = sb.append("}");

			return sb.toString();
		}

		return "{}";
	}

	public String linkRelation()
	{
		StringBuffer sb = new StringBuffer();
		LinkedNode<K, V> f = table.first(), n = f, l = table.last();

		do
		{
			sb.append((f.equals(n) ? "null <- " : " <-> ") + n.idx + " " + (l.equals(n) ? l + " -> null" : n));
		} while ((n = n.next) != null);

		return sb.toString();
	}

	private void putLink(LinkedNode<K, V> prev, LinkedNode<K, V> newln, LinkedNode<K, V> next)
	{
		if (next != null) next.setLink(newln, next.next);
		if (prev != null) prev.setLink(prev.prev, newln);
		newln.setLink(prev, next);
	}

	private void removeLink(LinkedNode<K, V> prev, LinkedNode<K, V> removeln, LinkedNode<K, V> next)
	{
		if (next != null) next.setLink(prev, next.next);
		if (prev != null) prev.setLink(prev.prev, next);
		removeln.setLink(null, null);
	}

	private void reDefineIndex(boolean sign, int start, int end)
	{
		LinkedNode<K, V> l = getNode(start, true);

		do
		{
			l.idx = l.idx + (sign ? 1 : -1);
			l = l.next;
			if (l == null) return;
		} while (l.idx != end);
	}

	public V get(Object key)
	{
		LinkedNode<K, V> l;
		return (l = getNode(key, false)) == null ? null : l.value;
	}

	public Object get(int idx, Character get)
	{
		char c;
		if (get == null || (c = get.charValue()) != 'k' && c != 'v') throw new UnsupportedOperationException("must set get = 'k' or 'v'");
		LinkedNode<K, V> l;
		return (l = getNode(idx, true)) == null ? null : (c == 'k' ? l.key : l.value);
	}

	public V getOrDefault(Object key, V defaultValue)
	{
		LinkedNode<K, V> l;
		return (l = getNode(key, false)) == null ? defaultValue : l.value;
	}

	public boolean containsKey(Object key)
	{
		return getNode(key, false) != null;
	}

	final LinkedNode<K, V> getNode(Object key, boolean internal)
	{
		if (table != null)
		{
			LinkedNode<K, V> f = table.first(), l = f;

			do
			{
				if ((internal) ? l.getIndex() == Integer.parseInt(key.toString()) : l.getKey().equals(key)) return l;
			} while ((l = l.next) != null);
		}

		return null;
	}

	public V put(K key, V value)
	{
		return putVal(-1, key, value);
	}

	public V put(int idx, K key, V value)
	{
		if (idx >= size || idx < 0) throw new ArrayIndexOutOfBoundsException(idx);
		return putVal(idx, key, value);
	}

	final V putVal(int idx, K key, V value)
	{
		LinkedNode<K, V> tab, newln;

		if ((tab = table) == null)
		{
			table = newLinkedNode(key, value, 0);
			putLink(null, table, null);
			++size;
		}
		else
		{
			LinkedNode<K, V> l;

			if ((l = getNode(key, false)) != null)
			{
				if (idx == -1) putVal(size - 1, key, value);
				else
				{
					newln = l;
					boolean flag = idx < newln.idx;
					int inc = flag ? 1 : 0;

					LinkedNode<K, V> bp = l.prev, bn = l.next;
					LinkedNode<K, V> ap = idx == 0 ? null : getNode(idx - inc, true), an = idx == size - 1 ? null : getNode(idx + 1 - inc, true);

					newln.value = value;
					if (newln.idx != idx)
					{
						int start = (flag ? an : bn).idx, end = (flag ? bp : ap).idx;
						removeLink(bp, newln, bn);
						if (newln.idx == 0) table = bn;
						reDefineIndex(flag, start, end + 1 + inc);
						newln.idx = idx;
						putLink(ap, newln, an);
					}
				}

				return value;
			}
			else
			{
				if (idx == -1)
				{
					newln = newLinkedNode(key, value, size);
					l = tab.last();
					putLink(l, newln, null);
				}
				else
				{
					l = getNode(idx, true);
					LinkedNode<K, V> prev = l.prev;

					newln = newLinkedNode(key, value, idx);
					reDefineIndex(true, idx, size);
					putLink(prev, newln, l);
				}

				++size;
			}
		}

		return value;
	}

	public void putAll(Map<? extends K, ? extends V> m)
	{
		putMapEntries(m);
	}

	public V remove(Object key)
	{
		LinkedNode<K, V> l;
		return (l = removeNode(key, null, false, false)) == null ? null : l.value;
	}

	public V remove(int idx)
	{
		if (idx >= size || idx < 0) throw new ArrayIndexOutOfBoundsException(idx);
		LinkedNode<K, V> l;
		return (l = removeNode(new Integer(idx), null, true, false)) == null ? null : l.value;
	}



	public boolean remove(Object key, Object value)
	{
		return removeNode(key, value, false, true) != null;
	}

	public boolean remove(int idx, Object value)
	{
		return removeNode(new Integer(idx), value, true, true) != null;
	}

	final LinkedNode<K, V> removeNode(Object key, Object value, boolean internal, boolean valueCheck)
	{
		LinkedNode<K, V> tab = table, p;

		if (tab != null)
		{
			if ((p = getNode(key, internal)) != null)
			{
				if (valueCheck && !p.value.equals(value)) return null;
				LinkedNode<K, V> ln = p.next, lp = p.prev;

				removeLink(lp, p, ln);
				if (p.idx == 0) table = ln;
				if (ln != null) reDefineIndex(false, ln.idx, size);
				--size;
				return p;
			}
		}

		return null;
	}

	public boolean replace(K key, V oldValue, V newValue)
	{
		if (oldValue == null) throw new NullPointerException();
		return replaceNode(key, oldValue, newValue, false, true) != null;
	}

	public boolean replace(int idx, V oldValue, V newValue)
	{
		if (oldValue == null) throw new NullPointerException();
		return replaceNode(new Integer(idx), oldValue, newValue, true, true) != null;
	}

	public V replace(K key, V value)
	{
		LinkedNode<K, V> l;
		return (l = replaceNode(key, null, value, false, false)) == null ? null : l.value;
	}

	public boolean replace(int idx, V value)
	{
		return replaceNode(new Integer(idx), null, value, true, false) != null;
	}

	private LinkedNode<K, V> replaceNode(Object key, V oldValue, V newValue, boolean internal, boolean valueCheck)
	{
		LinkedNode<K, V> tab = table, oln, fln;

		if (tab != null)
		{
			if ((oln = fln = getNode(key, internal)) != null)
			{
				if (valueCheck ? oldValue.equals(fln.value) : true) fln.value = newValue;
				else return null;
				return oln;
			}
		}

		return null;
	}

	public void clear()
	{
		LinkedNode<K, V> l = table == null ? null : table.last();

		if (l != null && size > 0)
		{
			LinkedNode<K, V> n = l, t;

			do
			{
				t = n.prev;
				n = null;
			} while ((n = t) != null);

			table = null;
			size = 0;
		}
	}

	public boolean containsValue(Object value)
	{
		LinkedNode<K, V> tab;

		if ((tab = table) != null && size > 0)
		{
			LinkedNode<K, V> n = tab;

			do
			{
				if (value.equals(n.value)) return true;
			} while ((n = n.next) != null);
		}

		return false;
	}

	abstract class LinkedSet<E> extends AbstractSet<E>
	{
		Set<E> k;

		LinkedSet()
		{
			k = new LinkedHashSet<E>();
		}

		public String toString()
		{
			return k.toString();
		}

		public final int size()
		{
			return size;
		}

		public boolean isEmpty()
		{
			return size != 0;
		}

		public final void clear()
		{
			k.clear();
			ListMap.this.clear();
		}

		public final Iterator<E> iterator()
		{
			return k.iterator();
		}

		public abstract Object[] toArray();
		public abstract <T> T[] toArray(T[] paramArrayOfT);
		public abstract boolean contains(Object o);
		public abstract boolean remove(Object key);
	}

	public Set<K> keySet()
	{
		keySet = null;
		Set<K> ks;
		return (ks = keySet) == null ? (keySet = new KeySet()) : ks;
	}

	final class KeySet extends LinkedSet<K>
	{
		KeySet()
		{
			LinkedNode<K, V> l = table;

			if (table != null && size > 0)
			{
				do
				{
					k.add(l.key);
				} while ((l = l.next) != null);
			}
		}

		public Object[] toArray()
		{
			Object[] array = new Object[size];

			if (table != null && size > 0)
			{
				LinkedNode<K, V> k = table;

				do
				{
					array[k.idx] = k.key;
				} while ((k = k.next) != null);
			}

			return array;
		}

		@SuppressWarnings("unchecked")
		public <T> T[] toArray(T[] paramArrayOfT)
		{
			if (paramArrayOfT != null && table != null && size > 0 && size == paramArrayOfT.length)
			{
				LinkedNode<K, V> l = table;

				do
				{
					paramArrayOfT[l.idx] = (T) l.key;
				} while ((l = l.next) != null);
			}

			return paramArrayOfT;
		}

		public final boolean contains(Object o)
		{
			return containsKey(o);
		}

		public final boolean remove(Object key)
		{
			return removeNode(key, null, false, false) != null;
		}
	}

	public Collection<V> values()
	{
		values = null;
		Collection<V> vs;
		return (vs = values) == null ? (values = new Values()) : vs;
	}

	final class Values extends LinkedSet<V>
	{
		Values()
		{
			LinkedNode<K, V> l = table;

			if (table != null && size > 0)
			{
				do
				{
					k.add(l.value);
				} while ((l = l.next) != null);
			}
		}

		public Object[] toArray()
		{
			Object[] array = new Object[size];

			if (table != null && size > 0)
			{
				LinkedNode<K, V> v = table;

				do
				{
					array[v.idx] = v.value;
				} while ((v = v.next) != null);
			}

			return array;
		}

		@SuppressWarnings("unchecked")
		public <T> T[] toArray(T[] paramArrayOfT)
		{
			if (paramArrayOfT != null && table != null && size > 0 && size == paramArrayOfT.length)
			{
				LinkedNode<K, V> l = table;

				do
				{
					paramArrayOfT[l.idx] = (T) l.value;
				} while ((l = l.next) != null);
			}

			return paramArrayOfT;
		}

		public final boolean contains(Object o)
		{
			return containsKey(o);
		}

		public final boolean remove(Object key)
		{
			return removeNode(key, null, false, false) != null;
		}
	}

	public Set<Entry<K, V>> entrySet()
	{
		entrySet = null;
		Set<Entry<K, V>> es;
		return (es = entrySet) == null ? (entrySet = new EntrySet()) : es;
	}

	final class EntrySet extends LinkedSet<Entry<K, V>>
	{
		EntrySet()
		{
			LinkedNode<K, V> l = table;

			if (table != null && size > 0)
			{
				do
				{
					k.add(l);
				} while ((l = l.next) != null);
			}
		}

		public Object[] toArray()
		{
			Object[] array = new Object[size];

			if (table != null && size > 0)
			{
				LinkedNode<K, V> l = table;

				do
				{
					array[l.idx] = l;
				} while ((l = l.next) != null);
			}

			return array;
		}

		@SuppressWarnings("unchecked")
		public <T> T[] toArray(T[] paramArrayOfT)
		{
			if (paramArrayOfT != null && table != null && size > 0 && size == paramArrayOfT.length)
			{
				LinkedNode<K, V> l = table;

				do
				{
					paramArrayOfT[l.idx] = (T) l;
				} while ((l = l.next) != null);
			}

			return paramArrayOfT;
		}

		public final boolean contains(Object o)
		{
			return containsKey(o);
		}

		public final boolean remove(Object key)
		{
			return removeNode(key, null, false, false) != null;
		}
	}

	public V putIfAbsent(K key, V value)
	{
		throw new UnsupportedOperationException();
	}

	public void forEach(BiConsumer<? super K, ? super V> action)
	{
		throw new UnsupportedOperationException();
	}

	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function)
	{
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	public Object clone()
	{
		ListMap<K, V> result;

		try
		{
			result = (ListMap<K, V>) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new InternalError(e);
		}

		result.putMapEntries(this);

		return result;
	}

	private void writeObject(ObjectOutputStream s) throws IOException
	{
		s.defaultWriteObject();
		s.writeInt(size);
		internalWriteEntries(s);
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();
		int size = s.readInt();

		if (size < 0) throw new InvalidObjectException("Illegal size count: " + size);
		else if (size > 0)
		{
			table = newLinkedNode((K) s.readObject(), (V) s.readObject(), 0);

			for (int i = 1; i < size; i++) putVal(-1, (K) s.readObject(), (V) s.readObject());
		}
	}

	abstract class ListIterator
	{
		LinkedNode<K, V> prev, current, next;
		int index;

		ListIterator()
		{
			LinkedNode<K, V> tab = table;
			prev = current = next = null;
			index = 0;
			if (tab != null && size > 0)
			{
				LinkedNode<K, V> l = tab;

				do
				{
					putLink(l.prev, l, l.next);
				} while ((l = l.next) != null);
			}
		}

		public final boolean hasNext()
		{
			return next != null;
		}

		final LinkedNode<K, V> nextNode()
		{
			LinkedNode<K, V> l = next;

			if (l == null) throw new NoSuchElementException();

			return l;
		}

		public final void remove()
		{
			LinkedNode<K, V> l = current;

			if (l == null) throw new IllegalStateException();

			current = null;
			K key = l.key;
			removeNode(key, null, false, false);
		}
	}

	final class KeyIterator extends ListIterator implements Iterator<K>
	{
		public final K next()
		{
			return nextNode().key;
		}
	}

	final class ValueIterator extends ListIterator implements Iterator<V>
	{
		public final V next()
		{
			return nextNode().value;
		}
	}

	final class EntryIterator extends ListIterator implements Iterator<Entry<K, V>>
	{
		public final Entry<K, V> next()
		{
			return nextNode();
		}
	}

	LinkedNode<K, V> newLinkedNode(K key, V value, int idx)
	{
		return new LinkedNode<K, V>(key, value, idx);
	}

	void reinitialize()
	{
		clear();
		table = null;
		entrySet = null;
		keySet = null;
		values = null;
		size = 0;
	}

	void internalWriteEntries(ObjectOutputStream s) throws IOException
	{
		LinkedNode<K, V> tab;

		if ((tab = table) != null && size > 0)
		{
			LinkedNode<K, V> l = tab;

			for (int i = 0; i < size; ++i)
			{
				do
				{
					s.writeObject(l.key);
					s.writeObject(l.value);
				} while ((l = l.next) != null);
			}
		}
	}
}
