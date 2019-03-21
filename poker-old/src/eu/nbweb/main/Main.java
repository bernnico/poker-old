package eu.nbweb.main;

public class Main {
	private static final char[] images = {'2','3','4','5','6','7','8','9','t','j','d','k','a'};


	public static void main(String[] args) {
		Card card1 = new Card("As");
		Card card2 = new Card("Ac");
		Card card3 = new Card("7h");
		Card card4 = new Card("2c");
		Card card5 = new Card("2h");
		Card card6 = new Card("Ak");
		
		Player[] p = new Player[2];
		p[0] = new Player();
		p[0].setHand(card1, card2);
		//p[1] = new Player();
		//p[1].setHand(card3, card4);
		p[1] = new Player();
		p[1].setHand(card5, card6);
//
//		Interpreter inter = new Interpreter();
//		System.out.println(inter.getCardAsString(inter.getCardAsField("TcKk")));
		
		
		
		Dealer d = new Dealer();
		d.setPlayers(p);
		long timeStart = System.currentTimeMillis();
		//System.out.println(p[0].getCardsInGameIndex()[0] + " " + p[0].getCardsInGameIndex()[1]);
		//System.out.println(d.getEquity().length);
		double[] eq =d.getEquity();
		double sum = 0;
		for (int i = eq.length - 1; i >= 0; i--) {
			System.out.println(i + " " + 100*eq[i]);
			sum += eq[i];
		}
		System.out.println(sum - eq[0]);
		
		//System.out.println(System.currentTimeMillis() - timeStart);
		


		 
	
	}

}
