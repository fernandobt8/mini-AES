import java.util.BitSet;

public class Utils {
	//polinomio irredutivel para o mini-AES que trabalha em gf(2⁴), polinomio = x⁴+x+1 = 10011
	//apenas armazenado 0011 pois o bit mais a esquerda já é descartado na multiplicação por 2.
	public static final BitSet irreduciblePolynomial = Utils.createBitSet(0, 0, 1, 1);

	public static BitSet createBitSet(int a, int b, int c, int d) {
		BitSet bitSet = new BitSet(4);
		bitSet.set(0, a == 1);
		bitSet.set(1, b == 1);
		bitSet.set(2, c == 1);
		bitSet.set(3, d == 1);
		return bitSet;
	}

	public static BitSet shiftLeft(BitSet value) {
		return value.get(1, 4);
	}

	//implementação da multiplicação por 2 em gf(2⁴).
	//primeiro faz-se um shift left por 1(multiplicação por 2), casa o resultado
	//de um polinomio de grau maior que 3 faz-se um xor por um polinomio irredutivel
	//do mesmo grau, como multiplicação por dois no maximo resulta em um polinomio 
	//de grau 4, apenas fazemos xor com 0011 em vez de 10011 e descartamos o bit mais a 
	//esquerda do valor no shift left.
	public static BitSet multiplyByTwo(BitSet value) {
		BitSet shiftLeft = shiftLeft(value);
		if (value.get(0)) {
			shiftLeft.xor(irreduciblePolynomial);
		}
		return shiftLeft;
	}

	//implementação da multiplicação por 3 em gf(2⁴).
	//apenas multiplica-se o valor por 2 e soma-se mais um valor ao resultado.
	public static BitSet multiplyByThree(BitSet value) {
		BitSet multiplyByTwo = multiplyByTwo(value);
		multiplyByTwo.xor(value);
		return multiplyByTwo;
	}
}
