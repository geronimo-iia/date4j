package hirondelle.date4j;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.Test;

public class ToStringUtilTest {

	/*
	 * Two informal classes with cyclic references, used for testing.
	 */
	private static final class Ping {
		public void setPong(Pong aPong) {
			fPong = aPong;
		}

		@SuppressWarnings("unused")
		public Pong getPong() {
			return fPong;
		}

		@SuppressWarnings("unused")
		public Integer getId() {
			return new Integer(123);
		}

		@SuppressWarnings("unused")
		public String getUserPassword() {
			return "blah";
		}

		public String toString() {
			return ToStringUtil.getText(this);
		}

		private Pong fPong;
	}

	private static final class Pong {
		public void setPing(Ping aPing) {
			fPing = aPing;
		}

		@SuppressWarnings("unused")
		public Ping getPing() {
			return fPing;
		}

		public String toString() {
			return ToStringUtil.getTextAvoidCyclicRefs(this, Ping.class,
					"getId");
			// to see the infinite looping, use this instead :
			// return getText(this);
		}

		private Ping fPing;
	}

	/**
	 * Informal test harness.
	 */
	@Test
	public void informalTestHarness() {
		List<String> list = new ArrayList<String>();
		list.add("blah");
		list.add("blah");
		list.add("blah");
		System.out.println(ToStringUtil.getText(list));

		StringTokenizer parser = new StringTokenizer("This is the end.");
		System.out.println(ToStringUtil.getText(parser));

		Ping ping = new Ping();
		Pong pong = new Pong();
		ping.setPong(pong);
		pong.setPing(ping);
		System.out.println(ping);
		System.out.println(pong);
	}
}
