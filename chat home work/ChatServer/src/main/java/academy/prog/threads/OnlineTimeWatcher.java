package academy.prog.threads;

import academy.prog.service.ConcreteStatusManager;
import academy.prog.service.StatusManager;

public class OnlineTimeWatcher implements Runnable{
    private static final OnlineTimeWatcher watcher = new OnlineTimeWatcher();

    private StatusManager statusManager = new ConcreteStatusManager();

    private OnlineTimeWatcher() {}

    public static OnlineTimeWatcher getInstance() {
        return watcher;
    }

    @Override
    public void run() {

        while ( !Thread.interrupted() ) {
            statusManager.execute();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean isInterrupted() {
        return Thread.interrupted();
    }

}
