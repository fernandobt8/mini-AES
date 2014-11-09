import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.BitSet;

public class Main {

	public static void main(String[] args) {
		System.out.println("Digite 2 caracteres para usar de chave.");
		String readConsole = Main.readConsole();
		KeySchedule key = new KeySchedule(readConsole);
		MiniAes miniAes = new MiniAes();
		miniAes.setKey(key);
		key.print();
		while (true) {
			try {
				System.out.println("\nDigite 1 e enter para cifrar um texto.\nDigite 2 e enter para decifrar um texto.\n"
						+ "Digite 3 e enter para trocar de chave.\nDigite qualquer coisa e enter para sair.");
				readConsole = Main.readConsole();
				if (readConsole.equals("1")) {
					// cifra a mensagem digitada no console
					System.out.println("\nDigite a mensagem a cifrar.");
					byte[] bytes = Main.readConsole().getBytes("UTF-8");
					int blocos = bytes.length / 2;
					if (bytes.length % 2 != 0) {
						blocos++;
					}
					BitSet bits = BitSet.valueOf(bytes);
					byte[] result = new byte[blocos * 2];
					int count = 0;
					int countR = 0;
					for (int i = 0; i < blocos; i++) {
						miniAes.setValue(bits.get(count, count + 16));
						count += 16;
						miniAes.encrypt();
						byte[] bytes2 = miniAes.getBytes();
						result[countR++] = bytes2[0];
						result[countR++] = bytes2[1];
					}
					System.out.println("Mensagem cifrada.");
					System.out.println(new BigInteger(result).toString(36));
				} else if (readConsole.equals("2")) {
					// decifra a mensagem digitada no console
					System.out.println("\nDigite a mensagem a decifrar.");
					byte[] bytes = new BigInteger(Main.readConsole(), 36).toByteArray();
					int blocos = bytes.length / 2;
					if (bytes.length % 2 != 0) {
						blocos++;
					}
					BitSet bits = BitSet.valueOf(bytes);
					byte[] result = new byte[blocos * 2];
					int count = 0;
					int countR = 0;
					for (int i = 0; i < blocos; i++) {
						miniAes.setValue(bits.get(count, count + 16));
						count += 16;
						miniAes.decrypt();
						byte[] bytes2 = miniAes.getBytes();
						result[countR++] = bytes2[0];
						result[countR++] = bytes2[1];
					}
					System.out.println("Mensagem decifrada.");
					System.out.println(new String(result, "UTF-8"));
				} else if (readConsole.equals("3")) {
					System.out.println("\nDigite a nova chave.");
					readConsole = Main.readConsole();
					key = new KeySchedule(readConsole);
					miniAes.setKey(key);
					key.print();
				} else {
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String readConsole() {
		try {
			return new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
