package pl.acmc.media.scheduler;

public class SafeRunnable implements Runnable {
  private final transient Runnable runnable;
  
  public SafeRunnable(Runnable runnable) {
    this.runnable = runnable;
  }
  
  public void run() {
    try {
      this.runnable.run();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
