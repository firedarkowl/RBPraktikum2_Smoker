public interface BoundedBuffer<Zutat> {
    void enter(Zutat zutat1, Zutat zutat2);
    void remove(Zutat zutat) throws InterruptedException;
    boolean zutatAvailable(Zutat zutat);
}
