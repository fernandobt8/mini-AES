import java.util.BitSet;

public class MiniAes {
	private SBox sBox = new SBox();
	private BitSet[][] value;
	private boolean inverse;

	public MiniAes(BitSet[][] text, boolean inverse) {
		this(text[0][0], text[1][0], text[0][1], text[1][1], inverse);
	}

	// plaintext = 16 bits dividido em 4 blocos de 4 bits disposto em uma matriz
	// P = (p0, p2)
	//	   (p1, p3) 
	public MiniAes(BitSet p0, BitSet p1, BitSet p2, BitSet p3, boolean inverse) {
		this.value = new BitSet[2][2];
		this.value[0][0] = p0; //p0
		this.value[1][0] = p1; //p1
		this.value[0][1] = p2; //p2
		this.value[1][1] = p3; //p3
		this.inverse = inverse;
	}

	//subBytes troca cada bloco da matriz por um correspondente a caixa s-box
	public BitSet[][] subBytes() {
		this.value = this.sBox.subBytes(this.value, this.inverse);
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

	// d0 = (3, 2) * (p0) e d2 = (3, 2) * (p2)  resultando em  (d0, d2)
	// d1   (2, 3)   (p1)   d3   (2, 3) * (p3)				   (d1, d3)
	public BitSet[][] mixColumn() {
		BitSet[][] result = new BitSet[2][2];
		result[0][0] = Utils.multiplyByThree(this.value[0][0]);
		result[0][0].xor(Utils.multiplyByTwo(this.value[1][0]));

		result[1][0] = Utils.multiplyByTwo(this.value[0][0]);
		result[1][0].xor(Utils.multiplyByThree(this.value[1][0]));

		result[0][1] = Utils.multiplyByThree(this.value[0][1]);
		result[0][1].xor(Utils.multiplyByTwo(this.value[1][1]));

		result[1][1] = Utils.multiplyByTwo(this.value[0][1]);
		result[1][1].xor(Utils.multiplyByThree(this.value[1][1]));
		this.value = result;
		return this.value;
	}

	public void print() {
		String result = "";
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				result += "(";
				for (int count = 0; count < 4; count++) {
					result = result + (this.value[i][j].get(count) ? 1 : 0);
				}
				result += ")";
			}
			result += "\n";
		}
		System.out.println(result);
	}
}
