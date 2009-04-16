package kg.totality.core.commands;

public interface Counted<T> {
	public static int NO_COUNT_GIVEN = 0;
	T withCount(int count);
	int getCount();
}
