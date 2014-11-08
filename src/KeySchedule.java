import java.util.BitSet;

public class KeySchedule {

	private SBox sBox = new SBox();
	private BitSet roundConst1 = SBox.createBitSet(0, 0, 0, 1);
	private BitSet roundConst2 = SBox.createBitSet(0, 0, 1, 0);
	private BitSet[][] round0;
	private BitSet[][] round1;
	private BitSet[][] round2;

	// key = 16 bits dividido em 4 blocos de 4 bits disposto em uma matriz
	// K = (k0, k2)
	//	   (k1, k3) 
	public KeySchedule(BitSet plaintext) {
		//round 0
		this.round0 = new BitSet[2][2];
		this.round0[0][0] = plaintext.get(0, 4); //w0 = k0
		this.round0[1][0] = plaintext.get(4, 8); //w1 = k1
		this.round0[0][1] = plaintext.get(8, 12); //w2 = k2
		this.round0[1][1] = plaintext.get(12, 16); //w3 = k3

		//round 1
		this.round1 = new BitSet[2][2];
		this.round1[0][0] = this.sBox.subBytes(this.round0[1][1]); //w4 = w0 xor subBytes(w3) xor 0001 - round constant 1
		this.round1[0][0].xor(this.round0[0][0]);
		this.round1[0][0].xor(this.roundConst1);

		this.round1[1][0] = (BitSet) this.round0[1][0].clone(); //w5 = w1 xor w4
		this.round1[1][0].xor(this.round1[0][0]);

		this.round1[0][1] = (BitSet) this.round0[0][1].clone(); //w6 = w2 xor w5
		this.round1[0][1].xor(this.round1[1][0]);

		this.round1[1][1] = (BitSet) this.round0[1][1].clone(); //w7 = w3 xor w6
		this.round1[1][1].xor(this.round1[0][1]);

		//round 2
		this.round2 = new BitSet[2][2];
		this.round2[0][0] = this.sBox.subBytes(this.round1[1][1]); //w8 = w4 xor subBytes(w7) xor 0010 - round constant 2
		this.round2[0][0].xor(this.round1[0][0]);
		this.round2[0][0].xor(this.roundConst2);

		this.round2[1][0] = (BitSet) this.round1[1][0].clone(); //w9 = w5 xor w8
		this.round2[1][0].xor(this.round2[0][0]);

		this.round2[0][1] = (BitSet) this.round1[0][1].clone(); //w10= w6 xor w9
		this.round2[0][1].xor(this.round2[1][0]);

		this.round2[1][1] = (BitSet) this.round1[1][1].clone(); //w11 = w7 xor w10
		this.round2[1][1].xor(this.round2[0][1]);
	}

	public BitSet[][] getRound0() {
		return this.round0;
	}

	public BitSet[][] getRound1() {
		return this.round1;
	}

	public BitSet[][] getRound2() {
		return this.round2;
	}

	public void print() {
		this.printLine(this.round0);
		this.printLine(this.round1);
		this.printLine(this.round2);
	}

	private void printLine(BitSet[][] value) {
		String result = "";
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 2; i++) {
				for (int count = 0; count < 4; count++) {
					result = result + (value[i][j].get(count) ? 1 : 0);
				}
				result += "-";
			}
		}
		System.out.println(result);
	}
}
