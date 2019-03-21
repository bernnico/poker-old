package eu.nbweb.main;

import java.util.Arrays;

public class Card implements Comparable<Card>{
	private char[] card;

	
	public Card() {
		this.card = new char[2];
	}
	
	public Card(char[] card) {
		if (card.length != 2 || !isValidCard(card))
			throw new IllegalArgumentException("!!!   ERROR: invalid hand   !!!");
		
		this.card = card;
	}
	
	public Card(String hand) {
		if (hand.length() != 2 || !isValidCard(hand.toCharArray()))
			throw new IllegalArgumentException("!!!   ERROR: invalid hand   !!!");
		
		this.card = hand.toCharArray();
	}
	
	private boolean isValidCard(char[] card) {
		if ((card[0] > 0x31 && card[0] < 0x3a 
				|| card[0] == 'T' || card[0] == 't'
				|| card[0] == 'J' || card[0] == 'j'
				|| card[0] == 'Q' || card[0] == 'q'
				|| card[0] == 'K' || card[0] == 'k'
				|| card[0] == 'A' || card[0] == 'a')
				&& (card[1] == 'H' || card[1] == 'h'
				|| card[1] == 'K' || card[1] == 'k'
				|| card[1] == 'S' || card[1] == 's'
				|| card[1] == 'C' || card[1] == 'c'))
			return true;
		return false;		
	}

	public char getImage() {
		return card[0];
	}
	
	public char getSuit() {
		return card[1];
	}
	
	@Override public String toString() {
		return "" + Character.toUpperCase(card[0]) + Character.toLowerCase(card[1]);
		
	}

	@Override public int hashCode() {
		final int prime = 79543;
		int result = 39351281;
		result = prime * result + (getSuit() * getImage() << 24) + getSuit();
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (!Arrays.equals(card, other.card))
			return false;
		return true;
	}

	@Override public int compareTo(Card otherCard) {
		if (getImage() == otherCard.getImage())
			return 0;
		else if (getImage() < 0x3a && otherCard.getImage() < 0x3a )
			return getImage() > otherCard.getImage() ? 1 : -1;
		else if (getImage() < 0x3a)
			return -1;
		else if (otherCard.getImage() < 0x3a )
			return 1;
		else if(getImage() == 'A')
			return 1;
		else if(otherCard.getImage() == 'A')
			return -1;
		else if(getImage() == 'K')
			return 1;
		else if(otherCard.getImage() == 'K')
			return -1;
		else if(getImage() == 'Q')
			return 1;
		else if(otherCard.getImage() == 'Q')
			return -1;
		return 0;
	}
}






























