
import java.io.*;
import java.util.*;
import java.util.Scanner;
//import java.lang.Object.*;
import java.util.concurrent.*;

//sortThread extend thread
//threadpeker

class Benjamin6 {
	public static void main (String[] args) {

		String en = "";
		String to = "";
		String tre = "";

		//ArrayBlockingQueue<String[]> ferdiger = new ArrayBlockingQueue<String[]>(10);

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
	int antallFlettet = 0;

	int minsteLengde;

	String[] liste;//Liste med alle ord
	String[] delt;//Oppdelt liste for hver traad

	Traad[] total;//Traadarray med oversikt over alle traader

	Fletter fletter;
	ArrayBlockingQueue <String[]> ferdig;

	boolean mor = false;//Kun starttraaden er sjef.

    Traad sjef;

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

    	fletter = new Fletter();

    	ferdig = new ArrayBlockingQueue<String[]>(antallTrader);

    	sta = 0;
    	slu = minsteLengde-1;//Fra null, ikke 1.

    	boolean mor = true;

    	opprett();

    }

    void pushSorter(String[] s) {

        ferdig.add(s);
    	antallFerdige++;
    	antallTrader--;

    }

    void pushFlett() {

    	antallFlettet++;

    }

    Traad(String[] delt, Traad sjef) {
    	this.delt = delt;
    	this.sjef = sjef;
    }

    public String[] flett(String[] tmp1, String [] tmp2) {
        //Skal flyttes
    	int lengde = tmp1.length + tmp2.length;

    	String[] flettet = new String[lengde];

    	int a = 0;
    	int b = 0;

    	for (int i = 0; i < flettet.length; i++) {
    		if (a < tmp1.length) {
    			if (b < tmp2.length) {
    				if (tmp1[a].compareTo(tmp2[b]) < 0) {
    					flettet[i] = tmp1[a];
    					a++;
    				} else {
    					flettet[i] = tmp2[b];
    					b++;
    				}
    			} else {
    				while(a < tmp1.length) {
    					flettet[i]  = tmp1[a];
    					a++;
    					i++;
    				} 
    			}
    		} else {
    			while(b < tmp2.length) {
    				flettet[i]  = tmp2[b];
    				b++;
    				i++;
    			} 
    		}
    	}

    	return flettet;

    }

    public void opprett() {
       // System.out.format("Start system %d\n", liste.length);
    	int i = 0;

    	if (modul > 0) {
    		minsteLengde++;
    		slu++;

    		for (; i<modul; i++) {
               // System.out.format("%d X Start %d slutt %d: ML: %d\n", i, sta, slu, minsteLengde);
    			total[i] = new Traad(Arrays.copyOfRange(liste, sta, slu), this);
    			sta = slu+1;
    			slu = slu + minsteLengde;
    			total[i].start();
    		}
    		minsteLengde--;
    	}

    	for (; i<antallTrader; i++) {
           // System.out.format("%d Start %d slutt %d: ML: %d\n", i, sta, slu, minsteLengde);

    		total[i] = new Traad(Arrays.copyOfRange(liste, sta, slu), this);
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

    void sort(String[] s, int start, int slutt) {
        if (slutt > start) {
            int pivot = partisjon(s, start, slutt);
            sort(s, start, pivot-1);
            sort(s, pivot, slutt);
        }

        pushSorter(s);

    }

    int partisjon(String[] s, int start, int slutt) {
        String pivot = s[slutt];
        int venstre = start;
        int hoyre = slutt;
        String tmp = "";
        do {
            while ((s[venstre].compareTo(pivot) <= 0) && (venstre < slutt))
                venstre++;
            while ((s[hoyre].compareTo(pivot) > 0) && (hoyre > start))
                hoyre--;
            if (venstre < hoyre) {
                tmp = s[venstre];
                s[venstre] = s[slutt];
                s[hoyre] = tmp;

            }
        } while (venstre < hoyre);
        tmp = s[venstre];
        s[venstre] = s[slutt];
        s[slutt] = tmp;
        return venstre;
    }


    public void run() {

    	if(delt!=null) {
		//System.out.println(delt.length);

            sort(delt, 0, delt.length-1);
    	//	sorter();
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

class Fletter extends Thread {

	   public String[] flett(String[] tmp1, String [] tmp2) {
    	int lengde = tmp1.length + tmp2.length;

    	String[] flettet = new String[lengde];

    	int a = 0;
    	int b = 0;

    	for (int i = 0; i < flettet.length; i++) {
    		if (a < tmp1.length) {
    			if (b < tmp2.length) {
    				if (tmp1[a].compareTo(tmp2[b]) < 0) {
    					flettet[i] = tmp1[a];
    					a++;
    				} else {
    					flettet[i] = tmp2[b];
    					b++;
    				}
    			} else {
    				while(a < tmp1.length) {
    					flettet[i]  = tmp1[a];
    					a++;
    					i++;
    				} 
    			}
    		} else {
    			while(b < tmp2.length) {
    				flettet[i]  = tmp2[b];
    				b++;
    				i++;
    			} 
    		}
    	}

    	return flettet;

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
