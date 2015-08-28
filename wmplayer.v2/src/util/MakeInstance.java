package util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class MakeInstance
{
	private static MakeInstance instance;

	public static MakeInstance getInstance()
	{
		return instance == null ? instance = new MakeInstance() : instance;
	}

	public Class<?> getNonPrimitiveClass(Class<?> c)
	{
		switch (c.getName())
		{
			case "byte" :
				c = Byte.class;
				break;
			case "short" :
				c = Short.class;
				break;
			case "int" :
				c = Integer.class;
				break;
			case "long" :
				c = Long.class;
				break;
			case "float" :
				c = Float.class;
				break;
			case "double" :
				c = Double.class;
				break;
			case "char" :
				c = Character.class;
				break;
			case "boolean" :
				c = Boolean.class;
				break;
			default :
		}
		return c;
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public<E> E newInstance(Class<E> c, Map<String, ? extends Object> args)
	{
		Class<?>[] classes = null;
		Object[] arguments = null;
		E instance = null;

		if (args != null && args.size() > 0)
		{
			classes = new Class[args.size()];
			arguments = new Object[args.size()];
			String[] keys = new String[args.size()];
			keys = args.keySet().toArray(keys);

			for (int i = 0; i < keys.length; i++)
			{
				arguments[i] = args.get(keys[i]);
				classes[i] = getNonPrimitiveClass(arguments[i].getClass());
			}
		}

		try
		{
			String findMethod = "getInstance";
			Method m = c.getMethod(findMethod, classes);
			instance = (E) m.invoke(instance, arguments);
		}
		catch (NoSuchMethodException e)
		{
			try
			{
			String findMethod = "newInstance";
			Method m = c.getMethod(findMethod, classes);
			instance = (E) m.invoke(instance, arguments);
			}
			catch (NoSuchMethodException e1)
			{
				try
				{
					Constructor<?> constr = c.getConstructor(classes);
					instance = (E) constr.newInstance(arguments);
				}
				catch (InstantiationException e2)
				{
					throw new ConstructorException("this class is abstract class or interface", e2);
				}
			}
		}
		catch (IllegalArgumentException e)
		{
			throw new ConstructorException("parameter type or number of the parameter error", e);
		}
		catch (SecurityException | IllegalAccessException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
		finally
		{
			return instance;
		}
	}
}
