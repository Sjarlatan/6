
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

	String[] liste;//Liste med alle ord
	String[] delt;//Oppdelt liste for hver traad

	Traad[] total;//Traadarray med oversikt over alle traader

	boolean mor = false;//Kun starttraaden er sjef.

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
    	this.modul = modul;

    	sta = 0;
    	slu = minsteLengde;

    	boolean mor = true;

    	opprett();

    }

    Traad(String[] delt) {
    	this.delt = delt;
    }

    public String[] flett(String[] tmp1, String [] tmp2) {
    	int lengde = tmp1.length + tmp2.length;

    	String[] flettet = new String[lengde];

    	int a = 0;
    	int b = 0;
    	int i = 0;

    	while(tmp1[a]!=null || tmp2[b]!=null) {

    		if (tmp1[a].compareTo(tmp2[b]) < 0) {
    			flettet[i] = tmp1[a];
    			i++;
    			a++;
    		} else {
    			flettet[i] = tmp2[b];
    			i++;
    			b++;
    		}


    	}

    	return flettet;

    }

    public void opprett() {

   // 	System.out.format("Start system %d\n", liste.length);

    	for (int i = 0; i<antallTrader; i++) {

    		if (i == antallTrader - 1) {
    			slu = slu + modul;
    		}

    		//System.out.format("%d Start %d slutt %d\n", i, sta, slu);

    		total[i] = new Traad(Arrays.copyOfRange(liste, sta, slu));
    		//Bruker copyOfRange da selvprog av den ville blitt unodig tidkrevende og langt.

    		//System.out.println(total[i].delt.length);

    		sta = slu+1;
    		slu = slu + minsteLengde;



    		total[i].start();
    	}

    }

    public void sorter() {

    //	String lager;
    //	String tmp;

    	if (delt!=null) {

    	String tmp[] = new String[delt.length]; 

//tmp1[a].compareTo(tmp2[b]) < 0
    		//Quicksort!

    	}

    }

    public void run() {

    	if(delt!=null) {
		//System.out.println(delt.length);

    		sorter();
    	}

    	if(mor) {

    		String[] forsok = flett(total[0].delt, total[1].delt);
    		//System.out.println("Kommer jeg hit?");
    		for (int i = 0; i < forsok.length; i++) {
    			System.out.println(forsok[i]);
    		}

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
