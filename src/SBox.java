import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class SBox {

	//s-box
	private final Map<BitSet, BitSet> sBox = new HashMap<>();

	public SBox() {
		this.sBox.put(createBitSet(0, 0, 0, 0), createBitSet(1, 1, 1, 0));
		this.sBox.put(createBitSet(0, 0, 0, 1), createBitSet(0, 1, 0, 0));
		this.sBox.put(createBitSet(0, 0, 1, 0), createBitSet(1, 1, 0, 1));
		this.sBox.put(createBitSet(0, 0, 1, 1), createBitSet(0, 0, 0, 1));

		this.sBox.put(createBitSet(0, 1, 0, 0), createBitSet(0, 0, 1, 0));
		this.sBox.put(createBitSet(0, 1, 0, 1), createBitSet(1, 1, 1, 1));
		this.sBox.put(createBitSet(0, 1, 1, 0), createBitSet(1, 0, 1, 1));
		this.sBox.put(createBitSet(0, 1, 1, 1), createBitSet(1, 0, 0, 0));

		this.sBox.put(createBitSet(1, 0, 0, 0), createBitSet(0, 0, 1, 1));
		this.sBox.put(createBitSet(1, 0, 0, 1), createBitSet(1, 0, 1, 0));
		this.sBox.put(createBitSet(1, 0, 1, 0), createBitSet(0, 1, 1, 0));
		this.sBox.put(createBitSet(1, 0, 1, 1), createBitSet(1, 1, 0, 0));

		this.sBox.put(createBitSet(1, 1, 0, 0), createBitSet(0, 1, 0, 1));
		this.sBox.put(createBitSet(1, 1, 0, 1), createBitSet(1, 0, 0, 1));
		this.sBox.put(createBitSet(1, 1, 1, 0), createBitSet(0, 0, 0, 0));
		this.sBox.put(createBitSet(1, 1, 1, 1), createBitSet(0, 1, 1, 1));
	}

	//subBytes troca cada bloco da matriz por um correspondente a caixa s-box
	public BitSet[][] subBytes(BitSet[][] value) {
		BitSet[][] result = new BitSet[2][2];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				result[i][j] = (BitSet) this.sBox.get(value[i][j]).clone();
			}
		}
		return result;
	}

	public BitSet subBytes(BitSet value) {
		return (BitSet) this.sBox.get(value).clone();
	}

	public static BitSet createBitSet(int a, int b, int c, int d) {
		BitSet bitSet = new BitSet();
		bitSet.set(0, a == 1);
		bitSet.set(1, b == 1);
		bitSet.set(2, c == 1);
		bitSet.set(3, d == 1);
		return bitSet;
	}
}
