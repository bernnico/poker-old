package eu.nbweb.main;

import java.util.Arrays;

public class Player {
	private static int num = 0;
	
	private long[] handAsMatrix;
	private  int[] cardsInGameIndex;
	private Card[] hand;
	public int player;
	
	
	public Player() {
		init();	
		player = ++num;
	}
	
	private void init() {
		this.handAsMatrix = new long[4];
		this.cardsInGameIndex = new int[2];
		this.hand = new Card[2];
	}
	
	public void setHand(Card... cards) {
		init();
		this.hand = cards;
		this.cardsInGameIndex[0] = addCardToGame(hand[0]);
		this.cardsInGameIndex[1] = addCardToGame(hand[1]);
	}
	
	public Card[] getHand() {
		return hand;
	}

	public long[] getHandAsMatrix() {
		return handAsMatrix;
	}

	public int[] getCardsInGameIndex() {
		return cardsInGameIndex;
	}

	public int getPlayer() {
		return player;
	}

	public static void setNewPlayer() {
		Player.num = 0;
	}
	
	private int addCardToGame(Card card) {
		int im = 0;
		int suit = 0;
		
		if(card.getSuit() == 'k') {
			im = 13;
			suit = 1;
		} else if(card.getSuit() == 's') {
			im = 26;
			suit = 2;
		} else if(card.getSuit() == 'c') {
			im = 39;
			suit = 3;
		}
		
		if (card.getImage() > 0x31 && card.getImage() < 0x3a ) {
			im += Character.getNumericValue(card.getImage()) - 2;
			handAsMatrix[suit] += 0x00_0000_0000_0001l << ((Character.getNumericValue(card.getImage()) - 2) << 2);
		} else if(card.getImage() == 'T' || card.getImage() == 't') {
			im += 8;
			handAsMatrix[suit] += 0x00_0001_0000_0000l;
		} else if(card.getImage() == 'J' || card.getImage() == 'j') {
			im += 9;
			handAsMatrix[suit] += 0x00_0010_0000_0000l;
		} else if(card.getImage() == 'Q' || card.getImage() == 'q') {
			im += 10;
			handAsMatrix[suit] += 0x00_0100_0000_0000l;
		} else if(card.getImage() == 'K' || card.getImage() == 'k') {
			im += 11;
			handAsMatrix[suit] += 0x00_1000_0000_0000l;
		} else if(card.getImage() == 'A' || card.getImage() == 'a') {
			im += 12;
			handAsMatrix[suit] += 0x01_0000_0000_0000l;
		}
		
		return im;		
	}

	@Override
	public int hashCode() {
		return hand[0].hashCode() ^ hand[1].hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (!Arrays.equals(hand, other.hand))
			return false;
		return true;
	}
	 
	 
	 
}
