import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class SBox {

	//s-box mini-AES
	private final Map<BitSet, BitSet> sBox = new HashMap<>();
	//inverse s-box mini-AES
	private final Map<BitSet, BitSet> iSBox = new HashMap<>();

	public SBox() {
		this.sBox.put(Utils.createBitSet(0, 0, 0, 0), Utils.createBitSet(1, 1, 1, 0));
		this.sBox.put(Utils.createBitSet(0, 0, 0, 1), Utils.createBitSet(0, 1, 0, 0));
		this.sBox.put(Utils.createBitSet(0, 0, 1, 0), Utils.createBitSet(1, 1, 0, 1));
		this.sBox.put(Utils.createBitSet(0, 0, 1, 1), Utils.createBitSet(0, 0, 0, 1));

		this.sBox.put(Utils.createBitSet(0, 1, 0, 0), Utils.createBitSet(0, 0, 1, 0));
		this.sBox.put(Utils.createBitSet(0, 1, 0, 1), Utils.createBitSet(1, 1, 1, 1));
		this.sBox.put(Utils.createBitSet(0, 1, 1, 0), Utils.createBitSet(1, 0, 1, 1));
		this.sBox.put(Utils.createBitSet(0, 1, 1, 1), Utils.createBitSet(1, 0, 0, 0));

		this.sBox.put(Utils.createBitSet(1, 0, 0, 0), Utils.createBitSet(0, 0, 1, 1));
		this.sBox.put(Utils.createBitSet(1, 0, 0, 1), Utils.createBitSet(1, 0, 1, 0));
		this.sBox.put(Utils.createBitSet(1, 0, 1, 0), Utils.createBitSet(0, 1, 1, 0));
		this.sBox.put(Utils.createBitSet(1, 0, 1, 1), Utils.createBitSet(1, 1, 0, 0));

		this.sBox.put(Utils.createBitSet(1, 1, 0, 0), Utils.createBitSet(0, 1, 0, 1));
		this.sBox.put(Utils.createBitSet(1, 1, 0, 1), Utils.createBitSet(1, 0, 0, 1));
		this.sBox.put(Utils.createBitSet(1, 1, 1, 0), Utils.createBitSet(0, 0, 0, 0));
		this.sBox.put(Utils.createBitSet(1, 1, 1, 1), Utils.createBitSet(0, 1, 1, 1));

		this.iSBox.put(Utils.createBitSet(0, 0, 0, 0), Utils.createBitSet(1, 1, 1, 0));
		this.iSBox.put(Utils.createBitSet(0, 0, 0, 1), Utils.createBitSet(0, 0, 1, 1));
		this.iSBox.put(Utils.createBitSet(0, 0, 1, 0), Utils.createBitSet(0, 1, 0, 0));
		this.iSBox.put(Utils.createBitSet(0, 0, 1, 1), Utils.createBitSet(1, 0, 0, 0));

		this.iSBox.put(Utils.createBitSet(0, 1, 0, 0), Utils.createBitSet(0, 0, 0, 1));
		this.iSBox.put(Utils.createBitSet(0, 1, 0, 1), Utils.createBitSet(1, 1, 0, 0));
		this.iSBox.put(Utils.createBitSet(0, 1, 1, 0), Utils.createBitSet(1, 0, 1, 0));
		this.iSBox.put(Utils.createBitSet(0, 1, 1, 1), Utils.createBitSet(1, 1, 1, 1));

		this.iSBox.put(Utils.createBitSet(1, 0, 0, 0), Utils.createBitSet(0, 1, 1, 1));
		this.iSBox.put(Utils.createBitSet(1, 0, 0, 1), Utils.createBitSet(1, 1, 0, 1));
		this.iSBox.put(Utils.createBitSet(1, 0, 1, 0), Utils.createBitSet(1, 0, 0, 1));
		this.iSBox.put(Utils.createBitSet(1, 0, 1, 1), Utils.createBitSet(0, 1, 1, 0));

		this.iSBox.put(Utils.createBitSet(1, 1, 0, 0), Utils.createBitSet(1, 0, 1, 1));
		this.iSBox.put(Utils.createBitSet(1, 1, 0, 1), Utils.createBitSet(0, 0, 1, 0));
		this.iSBox.put(Utils.createBitSet(1, 1, 1, 0), Utils.createBitSet(0, 0, 0, 0));
		this.iSBox.put(Utils.createBitSet(1, 1, 1, 1), Utils.createBitSet(0, 1, 0, 1));
	}

	//subBytes retorna uma matriz com cada bloco trocado por um correspondente a caixa s-box
	public BitSet[][] subBytes(BitSet[][] value, boolean inverse) {
		BitSet[][] result = new BitSet[2][2];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (inverse) {
					result[i][j] = (BitSet) this.iSBox.get(value[i][j]).clone();
				} else {
					result[i][j] = (BitSet) this.sBox.get(value[i][j]).clone();
				}
			}
		}
		return result;
	}

	//subBytes retorna um bloco trocado por um correspondente a um da caixa s-box
	public BitSet subBytes(BitSet value, boolean inverse) {
		if (inverse) {
			return (BitSet) this.iSBox.get(value).clone();
		} else {
			return (BitSet) this.sBox.get(value).clone();
		}
	}
}
