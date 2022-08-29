package by.devincubator.infrastructure.threads.configurators;

import by.devincubator.infrastructure.configurators.ProxyConfigurator;
import by.devincubator.infrastructure.core.Context;
import by.devincubator.infrastructure.threads.annotations.Schedule;
import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.*;

public class ScheduleConfigurator implements ProxyConfigurator {
    @Override
    public <T> T makeProxy(T object, Class<T> implementation, Context context) {
        for (Method method : implementation.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Schedule.class)) {
                checkVoid(method);
                checkPublic(method);
                return (T) Enhancer.create(implementation, (MethodInterceptor) this::invoke);
            }
        }
        return object;
    }

    @SneakyThrows
    private Object invoke(Object object, Method method, Object[] args, MethodProxy methodProxy) {
        Schedule schedulesync = method.getAnnotation(Schedule.class);
        if (schedulesync != null) {
            System.out.println(method);
            Thread thread = new Thread(() -> this.invoker(object, methodProxy, args, schedulesync.timeout(), schedulesync.delta()));
            thread.setDaemon(true);
            thread.start();
            return null;
        }
        return methodProxy.invokeSuper(object, args);
    }

    private void invoker(Object object, MethodProxy method, Object[] args, int milliseconds, int delta) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread invokeThread = new Thread(() -> {
                        ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
                            @Override
                            public Thread newThread(Runnable r) {
                                Thread fThread = Executors.defaultThreadFactory().newThread(r);
                                fThread.setDaemon(true);
                                return fThread;
                            }
                        });
                        try {
                            executorService.submit(() -> {
                                try {
                                    return method.invokeSuper(object, args);
                                } catch (Throwable throwable) {
                                }
                                return null;
                            }).get(milliseconds, TimeUnit.MILLISECONDS);
                        } catch (TimeoutException exception) {
                            executorService.shutdownNow();
                        } catch (Exception exception) {
                            executorService.shutdownNow();
                        }
                    });
                    invokeThread.setDaemon(true);
                    invokeThread.start();
                    Thread.currentThread().sleep(delta);
                } catch (InterruptedException exception) {
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void checkVoid(Method method) {
        if (!method.getReturnType().equals(void.class)) {
            throw new IllegalStateException("Method isn't void");
        }
    }

    private void checkPublic(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new IllegalStateException("Method's visibility isn't public");
        }
    }
}
