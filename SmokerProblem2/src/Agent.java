import java.util.Random;

public class Agent extends Thread {
    private Zutat zutat1;
    private Zutat zutat2;
    private Zutat zutat3;
    private final BoundedBuffer<Zutat> tisch;
    private final Random random;

    public Agent(String name, BoundedBuffer<Zutat> tisch) {
        this.setName(name);
        this.tisch = tisch;
        this.zutat1 = Zutat.Papier;
        this.zutat2 = Zutat.Tabak;
        this.zutat3 = Zutat.Streichhoelzer;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (tisch) {
                int randomZahl = random.nextInt(3);
                if (randomZahl == 0) {
                    tisch.enter(zutat1, zutat2);
                    System.err.println(getName() + " legt " + zutat1 + " und " + zutat2 + " auf den Tisch!");
                } else if (randomZahl == 1) {
                    tisch.enter(zutat2, zutat3);
                    System.err.println(getName() + " legt " + zutat2 + " und " + zutat3 + " auf den Tisch!");
                } else {
                    tisch.enter(zutat1, zutat3);
                    System.err.println(getName() + " legt " + zutat1 + " und " + zutat3 + " auf den Tisch!");
                }
            }
        }
    }


}
