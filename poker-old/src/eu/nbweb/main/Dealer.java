package eu.nbweb.main;

import java.util.Random;

public class Dealer {
	private static final long pair = 			0x0000FFFFFFFFF000l;
	private static final long twoPair = 		0x0000FFFFFFFFFFF0l;
	private static final long three = 			0x30FFFFFFFFFFFF00l;
	private static final long straight = 		0x3FFFFFFFFF000000l;
	private static final long flush = 			0x3FFFFFFFFFF00000l;
	private static final long fullHouse = 		0x700FFFFFFFFFFFFFl;
	private static final long four = 			0x7FFFFFFFFF000000l;
	private static final long straightFlush = 	0x7FFFFFFFFFF00000l;
	
	private static final long checkAssAnd = 	0x7_0000_0000_0000l;
	private static final long checkAssOr = 		0x1_0000_0000_0000l;

	// index: 0 -> h, 1 -> k, 2 -> s, 3 -> c
	// 0b00000000_00000001_00010001_00010001_00010001_00010001_00010001_00010001
	// = 0x01_1111_1111_1111 => msb == (0)(A) ... (3)(2) == lsb
	// => 0A_KDJT_9876_5432 => 1 == true, 0 == false

	private boolean[] checkCardsInGame;
	private long[][] cardsInGame;
	private long[][] playerBoard;
	private long[] playersCombos;
	private long[] flushHand;
	private long[] straightHand;

	private int players;
	private int playerCardIndex[][];

	private Random random;

	public Dealer() {
		this.random = new Random();
		players = 0;
	}

	public void setPlayers(Player... players) {
		this.players = players.length;
		this.cardsInGame = new long[this.players + 1][4];
		this.playerCardIndex = new int[this.players][2];

		for (int i = 0; i < players.length; i++) {
			for (int j = 0; j < 4; j++)
				cardsInGame[i + 1] = players[i].getHandAsMatrix();

			this.playerCardIndex[i][0] = players[i].getCardsInGameIndex()[0];
			this.playerCardIndex[i][1] = players[i].getCardsInGameIndex()[1];
		}
	}

	private void init() {
		this.checkCardsInGame = new boolean[52];
		this.cardsInGame[0] = new long[4];
		this.playerBoard = new long[players][4];
		this.playersCombos = new long[players];

		this.flushHand = new long[players];
		this.straightHand = new long[players];

		for (int i = 0; i < players; i++) {
			this.checkCardsInGame[this.playerCardIndex[i][0]] = true;
			this.checkCardsInGame[this.playerCardIndex[i][1]] = true;
		}

	}

	double stflush = 0, four1 = 0, fullhouse1 = 0, flush1 = 0, straight1 = 0, three1 = 0, twoPair1 = 0, pair1 = 0, hihgCard1 = 0;
	public double[] getEquity() {
		if (players == 0) 
			return null;
		int i = 0;
		int tiel = 0;
		int wins = 0;

		while (i < 15000000) {
			init();
			getBoard();
			checkFlush();
			checkStaight();
			checkCardsCombo();

			wins += playersCombos[0] < playersCombos[1] ? 1 : 0;
			tiel += playersCombos[0] == playersCombos[1] ? 1 : 0;

			i++;
		}
		return new double[] {1.0 * wins / (i),
				stflush/ (i), four1/ (i), fullhouse1 / (i), flush1/ (i),
				straight1 / (i), three1/ (i), twoPair1 / (i), pair1 / (i), hihgCard1 / (i)};
	}

	private void getBoard() {
		getRandomCard();
		getRandomCard();
		getRandomCard();
		getRandomCard();
		getRandomCard();

		for (int i = 0; i < players; i++) {
			for (int j = 0; j < 4; j++) {
				playerBoard[i][j] |= cardsInGame[0][j] | cardsInGame[i + 1][j];
				playersCombos[i] += cardsInGame[0][j] | cardsInGame[i + 1][j];
			}
		}
	}
	
	private void getAllBoards(int[] index4, int[] index13) {
		// long[4] as 4x13 Matrix
		long h13, k13, s13, c13;
		
		h13 = 0b011111;
		
		int a = 0, b = 1, c = 2, d = 3, e = 4; 
		
	}
	

	private void getRandomCard() {
		int image = (random.nextInt(71391) ^ random.nextInt(73197)) % 13;
		int suit = (random.nextInt(4) ^ random.nextInt(5137) ^ random.nextInt(13017)) % 4;


		
		while (checkCardsInGame[image + suit * 13]) {
			image = (random.nextInt(1911153) ^ random.nextInt(917013)) % 13;
			suit = (random.nextInt(76518) ^ random.nextInt(130757) ^ random.nextInt(13057)) % 4;
		}
		cardsInGame[0][suit] |= 0x01l << (image << 2);
		checkCardsInGame[image + suit * 13] = true;	
	}

	private void checkFlush() {
		int flag;
		long high;

		for (int i = 0; i < players; i++) {
			for (int j = 0; j < 4; j++) {
				flag = 0;
				high = 0;
				for (int k = 0; k < 13; k++) {				
					if ((playerBoard[i][j] & (checkAssAnd >> (k << 2))) != 0) {
						flag++;
						high <<= 4;
						high |= (13 - k);
					}

					if (flag == 5) {
						flushHand[i] = flush;
						flushHand[i] |= high;
if(i==1) flush1++;
						break;
					}
				}
			}
		}
	}

	private void checkStaight() {
		int flag, high;

		for (int i = 0; i < players; i++) {
			flag = 0;
			high = 0;
			for (int j = 0; j < 13; j++) {
				if ((playersCombos[i] & (checkAssAnd >> (j << 2))) != 0) {
					flag++;
					high <<= 4;
					high |= (13 - j);
				} else {
					flag = 0;
					high = 0;
				}

				if (flag == 5) {
					straightHand[i] = straight;
					straightHand[i] |= high;
if(i==1) straight1++;
					break;
				}
			}
			if (flag == 4 && (playersCombos[i] & checkAssAnd) != 0) {
				straightHand[i] = straight;
				straightHand[i] |= (high << 4) | 0xd;
if(i==1) straight1++;
			}
		}
	}

	private void checkCardsCombo() {
		long cards2, cards3, cards4, cards7;

		cards2 = checkAssOr << 1;
		cards3 = cards2 | checkAssOr;
		cards4 = cards2 << 1;
		cards7 = cards4 + cards3;

		long pairs;
		int threes, highCard;
		
		for (int i = 0; i < players; i++) {
			if (flushHand[i] != 0 && straightHand[i] != 0
					&& (flushHand[i] & 0xFFFFFl) == (straightHand[i] & 0xFFFFFl)) {
				playersCombos[i] = straightFlush | flushHand[i];
if(i==1) {		
flush1--;
straight1--;
stflush++;}
				continue;
			}
			
			pairs = 0;
			threes = 0;
			highCard = 0;
			
			for (int j = 0; j < 13; j++) {
				if ((playersCombos[i] & (cards7 >> (j << 2))) == 0) {
					continue;
					
				} else if ((playersCombos[i] & (checkAssAnd >> (j << 2))) == (checkAssOr >> (j << 2))) {
					highCard <<= 4;
					highCard |= (13 - j);
					
				} else if ((playersCombos[i] & (checkAssAnd >> (j << 2)))  == (cards2 >> (j << 2))) {
					if (pairs == 0) {
						playersCombos[i] |= (13l - j) << 52;
						pairs = 1;
						if (threes == 1) {
							playersCombos[i] |= fullHouse;
							pairs = -1;
							threes = -1;
if(i==1) fullhouse1++;
							break;
						}
					} else if (pairs == 1) {
						playersCombos[i] |= (13l - j) << 48;
						pairs = 2;
					} else {
						highCard <<= 4;
						highCard |= (13 - j);
						pairs = 3;
					}
					
				} else if ((playersCombos[i] & (checkAssAnd >> (j << 2)))  == (cards3 >> (j << 2))) {
					if (threes == 0 && pairs != 0) {
						playersCombos[i] |= fullHouse | ((13l - j) << 56);
						threes = -1;
						pairs = -1;
if(i==1) fullhouse1++;
						break;
					} else if (threes == 0) {
						playersCombos[i] |= (13l - j) << 56;
						threes = 1;
					} else if (threes == 1 && pairs == 0) {
						playersCombos[i] |= fullHouse | ((13l - j) << 52);
						threes = -1;
						pairs = -1;
if(i==1) fullhouse1++;
						break;
					}
					
				} else if ((playersCombos[i] & (cards4 >> (j << 2)))  == (cards4 >> (j << 2))) {
					playersCombos[i] = four | (13 - j);
					threes = -1;
					pairs = -1;
if(i==1) four1++;	
					break;
				}
			}
			
			if (pairs != -1 && (flushHand[i] != 0 || straightHand[i] != 0)) {
				playersCombos[i] = flushHand[i] > straightHand[i] ? flushHand[i] : straightHand[i];
			}
			
			else if (pairs == -1);
			
			else if ((pairs == 0 && threes == 0)) {
					playersCombos[i] = 0;
					playersCombos[i] |= highCard >> 8;
if(i==1) hihgCard1++;
			} else if (pairs == 1) {
					playersCombos[i] |= pair;
					playersCombos[i] &= 0x0FF0FFFFFFFFF000l;
					playersCombos[i] |= highCard >> 8;
if(i==1) pair1++;
				
				
			} else if (pairs == 2) {
					playersCombos[i] |= twoPair;
					playersCombos[i] &= 0x0FFFFFFFFFFFFFF0l;
					playersCombos[i] |= highCard >> 8;
if(i==1)twoPair1++;
				
				
			}else if (pairs == 3) {
					playersCombos[i] |= twoPair;
					playersCombos[i] &= 0x0FFFFFFFFFFFFFF0l;
					playersCombos[i] |= highCard >> 4;
if(i==1)twoPair1++;
				
				
			}  else if (threes == 1) {
					playersCombos[i] |= three;
					playersCombos[i] &= 0x3FFFFFFFFFFFFF00l;
					playersCombos[i] |= highCard >> 8;
if(i==1) three1++;
//	System.out.printf("" + 0 + "    %16x\n", playersCombos[i]);
				
			}
		}

	}
}
