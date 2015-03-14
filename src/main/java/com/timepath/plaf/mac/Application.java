package com.timepath.plaf.mac;

import com.apple.OSXAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reflected com.apple.eawt.Application
 *
 * @author TimePath
 */
@SuppressWarnings("rawtypes")
public class Application {

    private static final Logger LOG = Logger.getLogger(Application.class.getName());
    //
    private Object macOSXApplication;

    private Application() {
    }

    @NotNull
    public static Application getApplication() {
        return new Application();
    }

    public void setAboutHandler(AboutHandler aboutHandler) {
    }

    public void setDefaultMenuBar(JMenuBar menuBar) {
    }

    public void setDockIconBadge(String badge) {
    }

    public void setDockIconImage(Image image) {
    }

    public void setDockMenu(PopupMenu popup) {
    }

    public void setPreferencesHandler(PreferencesHandler preferencesHandler) {
    }

    public void setQuitHandler(QuitHandler quitHandler) {
        setHandler(new OSXHandler(/*
                 * quitHandler
                 */));
    }

    private void setHandler(@NotNull InvocationHandler adapter) {
        try {
            @NotNull Class<?> applicationClass = Class.forName("com.apple.eawt.Application");
            if (macOSXApplication == null) {
                // com.apple.eawt.Application()
                macOSXApplication = applicationClass.getConstructor((Class[]) null).newInstance((Object[]) null);
            }
            @NotNull Class applicationListenerClass = Class.forName("com.apple.eawt.ApplicationListener");
            // com.apple.eawt.Application.addApplicationListener(com.apple.eawt.ApplicationListener)
            @NotNull Method addListenerMethod = applicationClass.getDeclaredMethod("addApplicationListener", new Class[]{
                            applicationListenerClass
                    }
            );
            @NotNull Object osxAdapterProxy = Proxy.newProxyInstance(OSXAdapter.class.getClassLoader(),
                    new Class[]{applicationListenerClass},
                    adapter);
            addListenerMethod.invoke(macOSXApplication, osxAdapterProxy);
        } catch (ClassNotFoundException cnfe) {
            LOG.log(Level.WARNING,
                    "This version of Mac OS X does not support the Apple EAWT. ApplicationEvent handling has been disabled ({0})",
                    cnfe);
        } catch (Exception ex) {
            LOG.warning("Mac OS X Adapter could not talk to EAWT.");
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private Object getMacOSXApplication() {
        return macOSXApplication;
    }

    private void setMacOSXApplication(Object amacOSXApplication) {
        macOSXApplication = amacOSXApplication;
    }

    public interface AboutHandler {

        void handleAbout(AboutEvent e);
    }

    public interface PreferencesHandler {

        void handlePreferences(PreferencesEvent e);
    }

    public interface QuitHandler {

        void handleQuitRequestWith(QuitEvent qe, QuitResponse qr);
    }

    public static class AboutEvent {

        public AboutEvent() {
        }
    }

    public static class PreferencesEvent {

        public PreferencesEvent() {
        }
    }

    public static class QuitEvent {

        public QuitEvent() {
        }
    }

    public static class QuitResponse {

        public QuitResponse() {
        }
    }

    private static class OSXHandler implements InvocationHandler {

        private OSXHandler() {
        }

        @Nullable
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
