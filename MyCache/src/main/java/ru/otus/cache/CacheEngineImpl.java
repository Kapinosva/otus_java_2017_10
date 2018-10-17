package ru.otus.cache;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;


public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<MyElement<K, V>>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();
    private final ReferenceQueue<MyElement<K, V>> queue = new ReferenceQueue<>();

    private int hit = 0;
    private int miss = 0;
    private boolean keepLookingQueue = true;

    private final Thread queueMonitor = new Thread(() -> {
		try {
			while (!(Thread.currentThread().isInterrupted()) || (keepLookingQueue)   ){
				Reference<?> ref = null;
	    		
	    		while ((ref = queue.poll()) == null) {
						Thread.sleep(100);
	    		}         		
	    		if (ref instanceof SoftReference<?>){
	    			K key = ((SoftReference<MyElement<K,V>>) ref).get().getKey();
	    			synchronized (elements) {
	    		    	System.out.println(elements.size());
	        			elements.remove(key);
					}
	    		}
			}
		
		} catch (InterruptedException e) {
			System.out.println("Thread Interrupted");
		}
    });
    
    CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
       
        queueMonitor.start();
    }

    public void printSize() {
    	System.out.println("Size " + elements.size());
    }
    
    
    
    public void put(MyElement<K, V> element) {
        
    	if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        K key = element.getKey();
        
        SoftReference<MyElement<K, V>> srElement = new SoftReference<>(element, queue);
        
        elements.put(key, srElement);

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
               
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    public MyElement<K, V> get(K key) {
    	SoftReference<MyElement<K, V>> srElement = elements.get(key);
        MyElement<K, V> element = null;
        if (srElement != null) {
            element = srElement.get();                
            if (element != null) {
                hit++;
                element.setAccessed();
            } else {
                miss++;
            }
        } else {
            miss++;
        }

        return element;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
        queueMonitor.interrupt();
        elements.clear();
    }

    private TimerTask getTimerTask(final K key, Function<MyElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                MyElement<K, V> element = elements.get(key).get();
                if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}
