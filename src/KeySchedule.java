import java.io.UnsupportedEncodingException;
import java.util.BitSet;

public class KeySchedule {

	private SBox sBox = new SBox();
	//constante usada para criar a chave do round 1
	private BitSet roundConst1 = Utils.createBitSet(0, 0, 0, 1);
	//constante usada para criar a chave do round 2
	private BitSet roundConst2 = Utils.createBitSet(0, 0, 1, 0);
	//chaves do round 0, 1 e 2
	private BitSet[][] round0;
	private BitSet[][] round1;
	private BitSet[][] round2;

	public KeySchedule(String value) {
		try {
			byte[] bytes = value.getBytes("UTF-8");
			BitSet bits = BitSet.valueOf(bytes);
			this.init(bits.get(0, 4), bits.get(4, 8), bits.get(8, 12), bits.get(12, 16));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	// criação da chave que será usada nos 3 rounds do mini-AES
	private void init(BitSet k0, BitSet k1, BitSet k2, BitSet k3) {
		//round 0
		this.round0 = new BitSet[2][2];
		this.round0[0][0] = k0; //w0 = k0
		this.round0[1][0] = k1; //w1 = k1
		this.round0[0][1] = k2; //w2 = k2
		this.round0[1][1] = k3; //w3 = k3

		//round 1
		this.round1 = new BitSet[2][2];
		this.round1[0][0] = this.sBox.subBytes(this.round0[1][1], false); //w4 = w0 xor subBytes(w3) xor 0001 - round constant 1
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
		this.round2[0][0] = this.sBox.subBytes(this.round1[1][1], false); //w8 = w4 xor subBytes(w7) xor 0010 - round constant 2
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
		this.printLine("Chave do Round 0: ", this.round0);
		this.printLine("Chave do Round 1: ", this.round1);
		this.printLine("Chave do Round 2: ", this.round2);
	}

	private void printLine(String round, BitSet[][] value) {
		for (int j = 0; j < 2; j++) {
			for (int i = 1; i > -1; i--) {
				for (int count = 3; count > -1; count--) {
					round = round + (value[i][j].get(count) ? 1 : 0);
				}
				round += " ";
			}
		}
		System.out.println(round);
	}
}
