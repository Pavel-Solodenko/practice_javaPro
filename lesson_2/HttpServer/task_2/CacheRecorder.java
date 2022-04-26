public class CacheRecorder {
    public byte[] data;
    public long date;

    public CacheRecorder(byte[] data) {
        this.data = data;
        this.date = System.currentTimeMillis();
    }
    @Override
    public String toString() {
        long life = (System.currentTimeMillis() - date)/1000;
        return Long.toString(life)+" seconds";
    }
}
