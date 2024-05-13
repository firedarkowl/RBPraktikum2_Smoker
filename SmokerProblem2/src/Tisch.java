import java.util.LinkedList;

public class Tisch<E> implements BoundedBuffer<E> {
    private int maxAnzahlZutaten;
    private LinkedList<E> zutatenListe;

    public Tisch(int maxAnzahlZutaten) {
        this.maxAnzahlZutaten = maxAnzahlZutaten;
        this.zutatenListe = new LinkedList<>();
    }

    @Override
    public synchronized void enter(E zutat1, E zutat2) {
        while (zutatenListe.size() == maxAnzahlZutaten) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Interrupt-Status wiederherstellen
                throw new RuntimeException(e); // Unterbrechung behandeln
            }
        }
        zutatenListe.add(zutat1);
        zutatenListe.add(zutat2);
        System.err.println("Es wurden zwei Zutaten von " + Thread.currentThread().getName() + " auf den Tisch gelegt! Es liegen jetzt " + zutatenListe.size() + " Zutaten auf dem Tisch.");
        this.notifyAll();
    }


    @Override
    public synchronized void remove(E vorhandeneZutat) throws InterruptedException {
        while (!zutatAvailable(vorhandeneZutat)) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Interrupt-Status wiederherstellen
                throw e; // Exception erneut werfen, damit der Aufrufer darauf reagieren kann
            }
        }
        zutatenListe.remove(vorhandeneZutat);
        System.err.println("Eine Zutat von " + Thread.currentThread().getName() + " wurde vom Tisch genommen. Es liegen jetzt " + zutatenListe.size() + " Zutaten auf dem Tisch.");
        this.notifyAll();
    }


    @Override
    public boolean zutatAvailable(E item) {
        return zutatenListe.contains(item);
    }
}
