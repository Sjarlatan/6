import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.concurrent.*;
import java.io.FileWriter;

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

class Sjef extends Thread {

    int minsteLengde;

    String[] liste;//Liste med alle ord

    SorterTraad[] total;//Traadarray med oversikt over alle traader

  //  Fletter fletter;
    ArrayBlockingQueue <String[]> ferdig;

    int sta = 0;//Start
    int slu = 0;//Slutt

    int modul = 0;
    int antallTrader = 0;
    int antallFlettet = 0;

    int traadTeller;

    Sjef(String[] ord, int y, int antallTrader, int modul) {//Rotkonstruktor

        liste = ord;
        minsteLengde= y;
        total = new SorterTraad[antallTrader];
        this.antallTrader = antallTrader;
        traadTeller = antallTrader;
        this.modul = modul;

        ferdig = new ArrayBlockingQueue<String[]>(antallTrader);

        sta = 0;
        slu = minsteLengde-1;//Fra null, ikke 1.

    }

    synchronized void flettOmMulig() throws InterruptedException {
        if (ferdig.size()>1) {
            String[] tmp1 = ferdig.take();
            String[] tmp2 = ferdig.take();

            antallFlettet++;
            new Fletter(this, tmp1, tmp2).start();
        }
    }

    void pushSorter(String[] s) throws InterruptedException {
        addFerdig(s);
        traadTeller--;
    }

    void addFerdig(String[] s) throws InterruptedException {
        ferdig.add(s);
        flettOmMulig();
    }

    void pushFlett(String[] s) throws InterruptedException {
        addFerdig(s);
        antallFlettet--;
    }

    public void opprett() {
       // System.out.format("Start system %d\n", liste.length);
        int i = 0;

        if (modul > 0) {
            minsteLengde++;
            slu++;

            for (; i<modul; i++) {
               // System.out.format("%d X Start %d slutt %d: ML: %d\n", i, sta, slu, minsteLengde);
                total[i] = new SorterTraad(Arrays.copyOfRange(liste, sta, slu), this);
                sta = slu+1;
                slu = slu + minsteLengde;
                total[i].start();
            }
            minsteLengde--;
        }

        for (; i<antallTrader; i++) {
           // System.out.format("%d Start %d slutt %d: ML: %d\n", i, sta, slu, minsteLengde);

            total[i] = new SorterTraad(Arrays.copyOfRange(liste, sta, slu), this);
            sta = slu+1;
            slu = slu + minsteLengde;

            total[i].start();
        }

    }

    public void run() {

        opprett();

        while(traadTeller > 0 || antallFlettet > 0) {
            Thread.yield();
        }

  // File fil1 = new File("fil1.txt");

    //FileWriter f = new FileWriter(fil1);

        PrintWriter writer;

        try {
        writer = new PrintWriter("out.txt", "UTF-8");

                //writer.println("The second line");

        String[] lost = ferdig.poll();
        for (int i = 0; i<lost.length; i++) {

            writer.println(lost[i]);

         //   System.out.println(lost[i]);
      // f.write(lost[i]);

        }

        writer.close();
        
    } catch (IOException e) {
                System.out.println("Fil feil.");     
            }
        

    }

}

class SorterTraad extends Thread {

        String[] delt;//Oppdelt liste for hver traad
        Sjef sjef;

        SorterTraad(String[] delt, Sjef sjef) {
         this.delt = delt;
         this.sjef = sjef;
     }

     public void run() {

         if(delt!=null) {

            try { 
                sort(delt, 0, delt.length-1);
                sjef.pushSorter(delt);
            }
            catch (InterruptedException e) {
                System.out.println("Interrupted");     
            }

        }

    }

    void sort(String[] s, int start, int slutt) {
        if (start < slutt) {
            int pivot = partisjon(s, start, slutt, ((start + slutt)/2));
            sort(s, start, pivot-1);
            sort(s, pivot + 1, slutt);
        }
    }

    int partisjon(String[] s, int venstre, int hoyre, int pivot) {
        String pivotVerdi = s[pivot];

        s[pivot] = s[hoyre];
        s[hoyre] = pivotVerdi;
        String tmp = "";

        
        for(int i = venstre; i < hoyre; i++) {
            if (s[i].compareTo(pivotVerdi) <= 0) {
                tmp = s[i];
                s[i] = s[venstre];
                s[venstre] = tmp;
                venstre++;
            }
        }

        tmp = s[venstre];
        s[venstre] = s[hoyre];
        s[hoyre] = tmp;

        return venstre;
    }

}

class Fletter extends Thread {

    Sjef sjef;
    String[] array1;
    String[] array2;

    Fletter(Sjef sjef, String[] tmp1, String[] tmp2) {
        this.sjef = sjef;
        array1 = tmp1;
        array2 = tmp2;
    }

    public void run() {


        try { 
            String[] resultat = flett(array1, array2);
            sjef.pushFlett(resultat);
        }
        catch (InterruptedException e) {
            System.out.println("Interrupted");     
        }


    }

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
			//System.out.println(modul);

			liste = new String[antall];
			f.nextLine();

			for (int i = 0; i<antall; i++) {

				String linje = f.nextLine();
				liste[i] = linje;

			}

			Sjef sjef = new Sjef(liste, y, antallTrader, modul);
			sjef.start();

		} catch (FileNotFoundException e) {
			System.out.println("Fil 1 ikke funnet.");
			e.printStackTrace();
		}

	//Lesinn

	}

}
