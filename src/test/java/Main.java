import com.element.event.CacheMap;

public class Main {
	public static void main(String[] args) {
		CacheMap<String, Integer> map = new CacheMap<>(1);

		map.register(Integer.class, "张潮", 1);

		map.addRegistrationListener(event -> System.out.println("event1 = " + event));
		map.addRegistrationListener(event -> System.out.println("event2 = " + event));
		map.addRegistrationListener(event -> System.out.println("event3 = " + event));

		map.register(Integer.class, null, 1);
		System.out.println(map.getRegisteredObject(Integer.class,1));
	}
}