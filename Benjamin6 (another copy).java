
import java.io.*;
import java.util.*;
import java.util.Scanner;

//sortThread extend thread
//threadpeker

class Benjamin6 {
	public static void main (String[] args) {

		String en = "";
		String to = "";
		String tre = "";

		int tradTeller = 0;
		int antallTrader = 10;

	/*
		String test = "polse";
		String test1 = "polse";
		String test2 = "pylse";
		String test3 = "qylse";

		System.out.println(test.compareTo(test1) < 0);
		System.out.println(test.compareTo(test3) < 0);
	*/

		int lengde = args.length;

		if (lengde!=3) {
			System.out.println("For mye. For lite.");
		} else {
			en =  args[0];
			to = args[1];
			tre = args[2];
		}

		Innleser lesning = new Innleser(antallTrader);

	}

}

class Traad extends Thread {

	int antallFerdige = 0;

	int minsteLengde;

	String[] liste;
	String[] delt;

	Traad[] total;

    //    Traad mor;

    int sta = 0;//Start
    int slu = 0;//Slutt

    int modul = 0;
    int antallTrader = 0;

    Traad(String[] ord, int y, int antallTrader, int modul) {//Rotkonstruktor

	//	sta = 0;
	//	slu = ord.length;

    	liste = ord;
    	minsteLengde= y;
    	total = new Traad[antallTrader];
    	this.antallTrader = antallTrader;
    	this.modul = 0;

    	sta = 0;
    	slu = minsteLengde;

    	opprett();

    }

    Traad(String[] delt) {
    	this.delt = delt;
    }

    public void opprett() {

    	for (int i = 0; i<antallTrader; i++) {

    		if (modul!=0) {
    			slu++;
    			modul--;
    		}
    		delt = Arrays.copyOfRange(liste, sta, slu);

    		sta = slu+1;
    		//slu = sta+minsteLengde;
    		slu = Math.min(sta + minsteLengde, liste.length);

    		total[i] = new Traad(delt);
    		System.out.println(total[i].delt.length);
    		total[i].start();
    		//System.out.println("Waiter?");
    	}

    }

    public void sorter() {

    //	String lager;
    //	String tmp;

    	if (delt!=null) {

    		//Quicksort!

    	}

}

public void run() {

	if(delt!=null) {
		//System.out.println(delt.length);

		sorter();
	}

}

}

class Innleser {

	int antallTrader = 0;

	Innleser(int antallTrader) {

		File fil1 = new File("names.txt");
		String[] liste;

		this.antallTrader = antallTrader;

		try {
			Scanner f = new Scanner(fil1);

			int antall = f.nextInt();

			int y = antall / antallTrader;
			int modul = antall % antallTrader;
			System.out.println(modul);

			liste = new String[antall];
			f.nextLine();

			for (int i = 0; i<antall; i++) {

				String linje = f.nextLine();
				liste[i] = linje;

			}

			Traad traad = new Traad(liste, y, antallTrader, modul);
			traad.start();

		} catch (FileNotFoundException e) {
			System.out.println("Fil 1 ikke funnet.");
			e.printStackTrace();
		}

	//Lesinn

	}

}
