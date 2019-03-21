package eu.nbweb.main;

public class Interpreter {
	private long[] imageAsBits = {
			0x01000000000000l, // A
			0x00100000000000l, // K
			0x00010000000000l, // ...
			0x00001000000000l,
			0x00000100000000l,
			0x00000010000000l,
			0x00000001000000l,
			0x00000000100000l,
			0x00000000010000l,
			0x00000000001000l,
			0x00000000000100l, // ...
			0x00000000000010l, // 3
			0x00000000000001l  // 2
	};
	
	private char[] image = { 'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2' };
	private char[] suit = { 'h', 'k', 's', 'c' };

	
	public Interpreter() { }
	
	public long[] getCardAsField(char[]... cards) {
		long[] bourd = new long[4];
		
		for (int i = 0; i < cards.length; i++) {
			if (cards[i][1] == 'h') {
				bourd[0] = getImageAsChar(cards[i][0]);
			} else if (cards[i][1] == 'k') {
				bourd[1] = getImageAsChar(cards[i][0]);
			} else if (cards[i][1] == 's') {
				bourd[2] = getImageAsChar(cards[i][0]);
			} else if (cards[i][1] == 'c') {
				bourd[3] = getImageAsChar(cards[i][0]);
			}
		}
		return bourd;
	}
	
	public long[] getCardAsField(String cards) {
		char[][] card = new char[cards.length() >> 1][2];

		char[] c = cards.toCharArray();
		
		for (int i = 0; i < c.length / 2; i++) {
			card[i][0] = c[2 * i];
			card[i][1] = c[2 * i + 1];
		}

		return getCardAsField(card);
	}
	
	private long getImageAsChar(char image) {
		if (image > 0x31 && image < 0x3a ) {
			return 0x00_0000_0000_0001l << ((Character.getNumericValue(image) - 2) << 2);
		} else if(image == 'T' || image == 't') {
			return 0x00_0001_0000_0000l;
		} else if(image == 'J' || image == 'j') {
			return 0x00_0010_0000_0000l;
		} else if(image == 'Q' || image == 'q') {
			return 0x00_0100_0000_0000l;
		} else if(image == 'K' || image == 'k') {
			return 0x00_1000_0000_0000l;
		} else if(image == 'A' || image == 'a') {
			return 0x01_0000_0000_0000l;
		}
		return 0;
	}

	public char[][] getCardAsChars(long[] stack) {
		char[][] flop = new char[5][2];
		
		int k = 0;		
		for (int i = 0; i < 4; i++)
			if (stack[i] != 0) 
				for (int j = 0; j < 13; j++)
					if (stack[i] == (stack[i] | imageAsBits[j])) {
						flop[k][0] = image[j];
						flop[k++][1] = suit[i];
						
						if ((stack[i] ^ imageAsBits[j]) == 0) break;
					}
		
		return flop;
	}
	
	public String getCardAsString(long[] stack) {
//		char[][] flop = getCardAsChars(stack);
//		String st = "";
//		for (int i = 0; i < 5; i++)
//			st += "" + flop[i][0] + flop[i][1];
		return "" + getCardAsChars(stack)[0][0] + getCardAsChars(stack)[0][1];
	}

}
