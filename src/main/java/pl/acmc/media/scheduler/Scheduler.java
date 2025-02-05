package pl.acmc.media.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Scheduler {
  private final ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(6);
  
  public ScheduledExecutorService getThreadPool() {
    return this.threadPool;
  }
  
  private final HashMap<String, List<ScheduledFuture<?>>> schedulers = new HashMap<>();
  
  public void cancel(String name) {
    List<ScheduledFuture<?>> scheduledFutures = this.schedulers.get(name);
    if (scheduledFutures == null)
      return; 
    for (ScheduledFuture<?> scheduledFuture : scheduledFutures)
      scheduledFuture.cancel(true); 
    this.schedulers.remove(name);
  }
  
  public ScheduledFuture<?> registerScheduleAtFixedRate(String name, Runnable runnable, long initialTime, long period, TimeUnit timeUnit) {
    List<ScheduledFuture<?>> list = this.schedulers.computeIfAbsent(name, k -> new ArrayList<>());
    ScheduledFuture<?> schedule = this.threadPool.scheduleAtFixedRate(new SafeRunnable(runnable), initialTime, period, timeUnit);
    list.add(schedule);
    return schedule;
  }
}
