import java.util.BitSet;

public class Main {

	public static void main(String[] args) {
		BitSet bits = new BitSet();
		bits.set(0, true);
		bits.set(1, true);
		bits.set(2, false);
		bits.set(3, false);

		bits.set(4, false);
		bits.set(5, false);
		bits.set(6, true);
		bits.set(7, true);

		bits.set(8, true);
		bits.set(9, true);
		bits.set(10, true);
		bits.set(11, true);

		bits.set(12, false);
		bits.set(13, false);
		bits.set(14, false);
		bits.set(15, false);

		KeySchedule keySchedule = new KeySchedule(bits);
		keySchedule.print();

	}
}
