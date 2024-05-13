public class Main {
    public static void main(String[] args) {
        Tisch<Zutat> tisch = new Tisch<>(2);

        Agent agent1 = new Agent("Agent1", tisch);
        Agent agent2 = new Agent("Agent2", tisch);

        Smoker smoker1 = new Smoker("Tick", Zutat.Tabak, tisch);
        Smoker smoker2 = new Smoker("Trick", Zutat.Papier, tisch);
        Smoker smoker3 = new Smoker("Track", Zutat.Streichhoelzer, tisch);

        agent1.start();
        agent2.start();
        smoker1.start();
        smoker2.start();
        smoker3.start();

        try {
            Thread.sleep(1000); // Wartezeit nach dem Start der Threads
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
