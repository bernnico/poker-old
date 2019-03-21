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
		Dealer d = new Dealer();
		d.setPlayers(p);
//		long timeStart = System.currentTimeMillis();

		double[] eq =d.getEquity();
		double sum = 0;
		for (int i = eq.length - 1; i >= 0; i--) {
			sum += eq[i];
		}
		
		System.out.println("High\t" + 100*eq[9]);
		System.out.println("Pair\t" + 100*eq[8]);
		System.out.println("Two\t" + 100*eq[7]);
		System.out.println("Three\t" + 100*eq[6]);
		System.out.println("Str\t" + 100*eq[5]);
		System.out.println("Flush\t" + 100*eq[4]);
		System.out.println("Full\t" + 100*eq[3]);
		System.out.println("Four\t" + 100*eq[2]);
		System.out.println("StrF\t" + 100*eq[1]);
		
		System.out.println("\nwin\t" + 100*eq[0]);
		System.out.println("sum\t" + (sum - eq[0]));
		
		//System.out.println(System.currentTimeMillis() - timeStart);
		


		 
	
	}

}
