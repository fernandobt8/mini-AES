import java.util.BitSet;

public class MiniAes {
	private SBox sBox = new SBox();
	private KeySchedule key;
	// texto cifrado ou plano = 16 bits dividido em 4 blocos de 4 bits disposto em uma matriz
	// value = (p0, p2)
	//	   	   (p1, p3) 
	private BitSet[][] value;

	public MiniAes() {
	}

	public MiniAes(BitSet p0, BitSet p1, BitSet p2, BitSet p3) {
		this.value = new BitSet[2][2];
		this.value[0][0] = p0; //p0
		this.value[1][0] = p1; //p1
		this.value[0][1] = p2; //p2
		this.value[1][1] = p3; //p3
	}

	public void encrypt() {
		//round 0
		this.addRoundKey(this.key.getRound0());

		//round 1
		this.subBytes(false);
		this.shiftRow();
		this.mixColumn();
		this.addRoundKey(this.key.getRound1());

		//round 2
		this.subBytes(false);
		this.shiftRow();
		this.addRoundKey(this.key.getRound2());
	}

	public void decrypt() {
		//round 2
		this.addRoundKey(this.key.getRound2());
		this.subBytes(true);
		this.shiftRow();

		//round 1
		this.addRoundKey(this.key.getRound1());
		this.mixColumn();
		this.subBytes(true);
		this.shiftRow();

		//round 0
		this.addRoundKey(this.key.getRound0());
	}

	public BitSet[][] getValue() {
		return this.value;
	}

	public byte[] getBytes() {
		String[] string = this.toString().split("-");
		byte parseByte = (byte) Integer.parseInt(string[0], 2);
		byte parseByte2 = (byte) Integer.parseInt(string[1], 2);
		byte[] aux = { parseByte, parseByte2 };
		return aux;
	}

	public void setValue(BitSet plaintext) {
		this.value = new BitSet[2][2];
		this.value[0][0] = plaintext.get(0, 4); //p0
		this.value[1][0] = plaintext.get(4, 8); //p1
		this.value[0][1] = plaintext.get(8, 12); //p2
		this.value[1][1] = plaintext.get(12, 16); //p3
	}

	public void setValue(BitSet[][] value) {
		this.value = value;
	}

	public void setKey(KeySchedule key) {
		this.key = key;
	}

	//subBytes, troca cada bloco da matriz por um correspondente a caixa s-box
	private void subBytes(boolean inverse) {
		this.value = this.sBox.subBytes(this.value, inverse);
	}

	//shiftRow
	// B = ( b0, b2) ( b0, b2)
	//     ( b1, b3) ( b3, b1)
	private void shiftRow() {
		BitSet aux = this.value[1][0];
		this.value[1][0] = this.value[1][1];
		this.value[1][1] = aux;
	}

	//addRoundKey, xor de cada bit do estado atual do texto por cada bit da roundKey
	private void addRoundKey(BitSet[][] roundKey) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				this.value[i][j].xor(roundKey[i][j]);
			}
		}
	}

	//mixColumn multiplicação por uma matriz:
	// d0 = (3, 2) * (p0) e d2 = (3, 2) * (p2)  resultando em  (d0, d2)
	// d1   (2, 3)   (p1)   d3   (2, 3) * (p3)				   (d1, d3)
	private void mixColumn() {
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
	}

	@Override
	public String toString() {
		String result = "";
		for (int j = 0; j < 2; j++) {
			for (int i = 1; i > -1; i--) {
				for (int count = 3; count > -1; count--) {
					result = result + (this.value[i][j].get(count) ? 1 : 0);
				}
			}
			result += "-";
		}
		return result.substring(0, result.length() - 1);
	}
}
