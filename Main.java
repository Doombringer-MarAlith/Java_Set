public class Main
{
	public static void main(String[] args)
	{
        MySet<Integer> ints = new MySet<Integer>();
        for (int i = 0; i < 100; i++) 
            ints.add(i);

        System.out.println("Size: " + ints.size());

        for (int i = 0; i < 100; i += 2) 
            ints.remove(i);

        System.out.println("Size: " + ints.size());
	}
}