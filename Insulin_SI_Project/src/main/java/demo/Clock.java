package demo;

public class Clock {

    private final long createdMillis = System.currentTimeMillis();

    //ritorna l'ora esatta in secondi
    public int getAgeInSeconds() {
        long nowMillis = System.currentTimeMillis();
        return (int)((nowMillis - this.createdMillis) / 1000);
    }

}
