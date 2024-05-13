public class Smoker extends Thread {
    private Zutat zutat;
    private Zutat fehlendeZutat1;
    private Zutat fehlendeZutat2;
    private final BoundedBuffer<Zutat> tisch;

    public Smoker(String name, Zutat zutat, BoundedBuffer<Zutat> tisch) {
        this.setName(name);
        this.zutat = zutat;
        this.tisch = tisch;
        getFehlendeZutaten();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (tisch) {
                    while (tisch.zutatAvailable(fehlendeZutat1) && tisch.zutatAvailable(fehlendeZutat2)) {
                        tisch.remove(fehlendeZutat1);
                        tisch.remove(fehlendeZutat2);
                        smoke();
                    }
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            synchronized (tisch) {
                tisch.notifyAll();
            }
        }
    }

    public void smoke() throws InterruptedException {
        System.err.println(getName() + " hat alle Zutaten und raucht jetzt!");
        System.err.println();
        Thread.sleep(2000);
    }

    public void getFehlendeZutaten() {
        switch (zutat) {
            case Papier:
                fehlendeZutat1 = Zutat.Tabak;
                fehlendeZutat2 = Zutat.Streichhoelzer;
                break;
            case Streichhoelzer:
                fehlendeZutat1 = Zutat.Tabak;
                fehlendeZutat2 = Zutat.Papier;
                break;
            default:
                fehlendeZutat1 = Zutat.Papier;
                fehlendeZutat2 = Zutat.Streichhoelzer;
                break;
        }
    }
}
