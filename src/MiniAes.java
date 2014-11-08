import java.util.BitSet;

public class MiniAes {

	private final BitSet[][] mixColumn = new BitSet[2][2];
	private SBox sBox = new SBox();
	private BitSet[][] value;

	// plaintext = 16 bits dividido em 4 blocos de 4 bits disposto em uma matriz
	// P = (p0, p2)
	//	   (p1, p3) 
	public MiniAes(BitSet plaintext) {
		this.initMixColumn();
		this.value = new BitSet[2][2];
		this.value[0][0] = plaintext.get(0, 4); //p0
		this.value[1][0] = plaintext.get(4, 8); //p1
		this.value[0][1] = plaintext.get(8, 12); //p2
		this.value[1][1] = plaintext.get(12, 16); //p3
	}

	//subBytes troca cada bloco da matriz por um correspondente a caixa s-box
	public BitSet[][] subBytes() {
		this.value = this.sBox.subBytes(this.value);
		return this.value;
	}

	//shiftRow
	// B = ( b0, b2) ( b0, b2)
	//     ( b1, b3) ( b3, b1)
	public BitSet[][] shiftRow() {
		BitSet aux = this.value[1][0];
		this.value[1][0] = this.value[1][1];
		this.value[1][1] = aux;
		return this.value;
	}

	//addRoundKey xor de cada bit de value por cada bit da roundKey
	public BitSet[][] addRoundKey(BitSet[][] roundKey) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				this.value[i][j].xor(roundKey[i][j]);
			}
		}
		return this.value;
	}

	public BitSet[][] mixColumn() {

		return this.value;
	}

	public String print() {
		String result = "";
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				result += "(";
				for (int count = 0; count < 4; count++) {
					result = result + (this.value[i][j].get(count) ? 1 : 0) + ",";
				}
				result += ")";
			}
			result += "\n";
		}
		return result;
	}

	//inicialização da matriz usada para a funação mixColumn
	private void initMixColumn() {
		this.mixColumn[0][0] = SBox.createBitSet(0, 0, 1, 1);
		this.mixColumn[0][1] = SBox.createBitSet(0, 0, 1, 0);
		this.mixColumn[1][0] = SBox.createBitSet(0, 0, 1, 0);
		this.mixColumn[1][1] = SBox.createBitSet(0, 0, 1, 1);
	}
}
