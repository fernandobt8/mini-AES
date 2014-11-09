import java.util.BitSet;

public class Main {

	public static void main(String[] args) {
		MiniAes miniAes = new MiniAes(Utils.createBitSet(1, 0, 1, 1), Utils.createBitSet(1, 1, 0, 0), Utils.createBitSet(0, 1, 1, 0), Utils.createBitSet(0, 0, 1, 1), false);
		KeySchedule key = new KeySchedule(Utils.createBitSet(1, 1, 0, 0), Utils.createBitSet(0, 0, 1, 1), Utils.createBitSet(1, 1, 1, 1), Utils.createBitSet(0, 0, 0, 0));

		miniAes.print();
		miniAes.addRoundKey(key.getRound0());

		miniAes.subBytes();
		miniAes.shiftRow();
		miniAes.mixColumn();
		miniAes.addRoundKey(key.getRound1());

		miniAes.subBytes();
		miniAes.shiftRow();
		BitSet[][] cipher = miniAes.addRoundKey(key.getRound2());

		miniAes.print();

		MiniAes miniAes2 = new MiniAes(cipher, true);
		KeySchedule keyD = new KeySchedule(Utils.createBitSet(1, 1, 0, 0), Utils.createBitSet(0, 0, 1, 1), Utils.createBitSet(1, 1, 1, 1), Utils.createBitSet(0, 0, 0, 0));

		miniAes2.addRoundKey(keyD.getRound2());
		miniAes2.subBytes();
		miniAes2.shiftRow();

		miniAes2.addRoundKey(keyD.getRound1());
		miniAes2.mixColumn();
		miniAes2.subBytes();
		miniAes2.shiftRow();

		miniAes2.addRoundKey(keyD.getRound0());

		miniAes2.print();
	}
}
