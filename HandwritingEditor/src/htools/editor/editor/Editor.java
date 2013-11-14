/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.editor.editor;

import htools.editor.matlabfunctions.MatlabMethods;
import htools.core.traces.Trace;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import matlabcontrol.MatlabInvocationException;

/**
 *
 * @author rvlander
 */
public class Editor {

    public static final String[] EDS = {"Geometry", "X", "Y", "dXsdT", "dYsdT", "HollerX", "HollerY"};
    public static final int GEOMETRY = 0;
    public static final int TIME = 0;
    public static final int X_SIGNAL = 1;
    public static final int Y_SIGNAL = 2;
    public static final int dX_SIGNAL = 3;
    public static final int dY_SIGNAL = 4;
    public static final int X_HOLLER = 5;
    public static final int Y_HOLLER = 6;
    //   private double[] sampling;
    //  private double[] X;
    //  private double[] Y;
    private ArrayList<Signal> signals; //sampling,x,y
    private ArrayList<EditorListener> listeners;
    private boolean workingTrace;
    private History history;

    public Editor(Trace t) {
        this();
        this.setWorkingTrace(t);
    }

    public Editor() {
        this.listeners = new ArrayList<EditorListener>();
        this.workingTrace = false;
    }

    private double[] signals(int i) {
        return signals.get(i).signal;
    }

    public void setWorkingTrace(Trace t) {
        try {
            //System.out.println("rhere");

            signals = new ArrayList();

            signals.add(new Signal(t.T));
            signals.add(new Signal(t.X));
            signals.add(new Signal(t.Y));
            signals.add(new Signal(MatlabMethods.derive(signals(TIME), signals(X_SIGNAL))));
            signals.add(new Signal(MatlabMethods.derive(signals(TIME), signals(Y_SIGNAL))));


            ArrayList<double[]> hollerRet = MatlabMethods.quickDM(signals(TIME), signals(X_SIGNAL), signals(Y_SIGNAL));

            signals.add(new HollerSignal(signals(0), hollerRet.get(0), hollerRet.get(2), hollerRet.get(4), hollerRet.get(6)[0], hollerRet.get(8)[0]));
            signals.add(new HollerSignal(signals(0), hollerRet.get(1), hollerRet.get(3), hollerRet.get(5), hollerRet.get(7)[0], 0));

            history = new History(signals);

            workingTrace = true;

            for (EditorListener l : this.listeners) {
                // System.out.println(l);
                l.workingTraceChanged();
            }
        } catch (MatlabInvocationException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void appendTrace(Trace t) {
        int m = signals(0).length;
        int n = m + t.T.length;
        signals.get(TIME).signal = Arrays.copyOf(signals(TIME), n);
        signals.get(X_SIGNAL).signal = Arrays.copyOf(signals(X_SIGNAL), n);
        signals.get(Y_SIGNAL).signal = Arrays.copyOf(signals(Y_SIGNAL), n);
        double off = signals(TIME)[m - 1] + signals(TIME)[m - 1] - signals(TIME)[m - 2];
        for (int i = m; i < n; i++) {
            signals(0)[i] = t.T[i - m] + off;
            signals(1)[i] = t.X[i - m];
            signals(2)[i] = t.Y[i - m];
        }
        try {
            signals.get(dX_SIGNAL).signal = MatlabMethods.derive(signals(0), signals(1));
            signals.get(dY_SIGNAL).signal = MatlabMethods.derive(signals(0), signals(2));
        } catch (MatlabInvocationException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateHollerSignals();
        history.push(signals);
        for (EditorListener l : this.listeners) {
            // System.out.println(l);
            l.workingTraceChanged();
        }
    }

    
    public void appendHollerTrace(Trace t) {
        int m = signals(0).length;
        int n = m + t.T.length;
        signals.get(TIME).signal = Arrays.copyOf(signals(TIME), n);
        double off = signals(TIME)[m - 1] + signals(TIME)[m - 1] - signals(TIME)[m - 2];
        for (int i = m; i < n; i++) {
            signals(0)[i] = t.T[i - m] + off;
        }
        try {
           ArrayList<double[]> hollerRet = MatlabMethods.quickDM(t.T, t.X, t.Y);
           
           signals.get(X_HOLLER).append(off,signals(0), hollerRet.get(0), hollerRet.get(2), hollerRet.get(4), hollerRet.get(6)[0], hollerRet.get(8)[0]);
           signals.get(Y_HOLLER).append(off,signals(0), hollerRet.get(1), hollerRet.get(3), hollerRet.get(5), hollerRet.get(7)[0], 0);
           signalAsChanged(X_HOLLER);
           signalAsChanged(Y_HOLLER);
           
        } catch (MatlabInvocationException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
       // updateHollerSignals();
        history.push(signals);
        for (EditorListener l : this.listeners) {
            // System.out.println(l);
            l.workingTraceChanged();
        }
    }
    
    public SignalPair getSignal(int type) {

        if (type == GEOMETRY) {
            return new SignalPair(signals.get(1), signals.get(2));
        } else {
            return new SignalPair(signals.get(0), signals.get(type));
        }
    }

    public void moveAndTimeShift(int signal, double[] selection, double dt, double dx) {
        if (signal == GEOMETRY) {
            System.err.println("Editing GEOMETRY is not permitted");
        } else {

            signals = history.getAtPointer();
            signals.get(signal).moveAndTimeShift(signals(0), selection, dt, dx);
            signalAsChanged(signal);
            fireListeners();

        }
    }

    public void reSize(int signal, double[] selection, double orit, double ratio_t, double orix, double ratio_x) {
        if (signal == GEOMETRY) {
            System.err.println("Editing GEOMETRY is not permitted");
        } else {

            signals = history.getAtPointer();
            signals.get(signal).reSize(signals(TIME), selection, orit, ratio_t, orix, ratio_x);
            signalAsChanged(signal);
            fireListeners();

        }
    }

    public boolean isWorkingTrace() {
        return workingTrace;
    }

    public void addListener(EditorListener e) {
        //  System.out.println("added");
        this.listeners.add(e);
    }

    private void fireListeners() {
        for (EditorListener l : this.listeners) {
            // System.out.println(l);
            l.signalsChanged();
        }
    }

    public void validateModifications() {
        history.push(signals);
    }

    public void cancelModifications() {
        signals = history.getAtPointer();
        fireListeners();
    }

    public void undo() {
        this.signals = history.undo();
        fireListeners();
    }

    public void redo() {
        this.signals = history.redo();
        fireListeners();
    }

    public Trace getTrace() {
        Trace t = new Trace();
        int n = signals(0).length;
        t.T = Arrays.copyOf(signals(0), n);
        t.X = Arrays.copyOf(signals(1), n);
        t.Y = Arrays.copyOf(signals(2), n);
        t.Z = new double[n];

        return t;
    }

    private void signalAsChanged(int type) {
        switch (type) {
            case X_SIGNAL:
                try {
                    signals.get(dX_SIGNAL).signal = MatlabMethods.derive(signals(TIME), signals(X_SIGNAL));
                    updateHollerSignals();
                } catch (MatlabInvocationException ex) {
                    Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);

                }
                break;
            case Y_SIGNAL:
                try {
                    signals.get(dY_SIGNAL).signal = MatlabMethods.derive(signals(TIME), signals(Y_SIGNAL));
                    updateHollerSignals();
                } catch (MatlabInvocationException ex) {
                    Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case dX_SIGNAL:
                try {
                    signals.get(X_SIGNAL).signal = MatlabMethods.integre(signals(0), signals(dX_SIGNAL), signals(X_SIGNAL)[0]);
                    updateHollerSignals();
                } catch (MatlabInvocationException ex) {
                    Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case dY_SIGNAL:
                try {
                    signals.get(Y_SIGNAL).signal = MatlabMethods.integre(signals(0), signals(dY_SIGNAL), signals(Y_SIGNAL)[0]);
                    updateHollerSignals();
                } catch (MatlabInvocationException ex) {
                    Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case X_HOLLER:
                signals.get(dX_SIGNAL).signal = HollerSignal.copyTab(signals(X_HOLLER));
                for (int i = 0; i < signals(X_SIGNAL).length; i++) {
                    HollerSignal hs = ((HollerSignal) signals.get(X_HOLLER));
                    signals(dX_SIGNAL)[i] += hs.c;
                }
                try {
                    signals.get(X_SIGNAL).signal = MatlabMethods.integre(signals(0), signals(dX_SIGNAL), signals(X_SIGNAL)[0]);
                } catch (MatlabInvocationException ex) {
                    Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case Y_HOLLER:
                signals.get(dY_SIGNAL).signal = HollerSignal.copyTab(signals(Y_HOLLER));
                try {
                    signals.get(Y_SIGNAL).signal = MatlabMethods.integre(signals(0), signals(dY_SIGNAL), signals(Y_SIGNAL)[0]);
                } catch (MatlabInvocationException ex) {
                    Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }

    }

    private void updateHollerSignals() {
        ArrayList<double[]> hollerRet;
        try {
            hollerRet = MatlabMethods.quickDM(signals(TIME), signals(X_SIGNAL), signals(Y_SIGNAL));


            signals.set(X_HOLLER, new HollerSignal(signals(0), hollerRet.get(0), hollerRet.get(2), hollerRet.get(4), hollerRet.get(6)[0], hollerRet.get(8)[0]));
            signals.set(Y_HOLLER, new HollerSignal(signals(0), hollerRet.get(1), hollerRet.get(3), hollerRet.get(5), hollerRet.get(7)[0], 0));
        } catch (MatlabInvocationException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void smooth() {
        signals.get(X_SIGNAL).smooth(signals(0));
        signals.get(Y_SIGNAL).smooth(signals(0));
        this.signalAsChanged(X_SIGNAL);
        this.signalAsChanged(Y_SIGNAL);
        history.push(signals);
        fireListeners();
    }

    public void smooth(int signal, double[] selection) {
        if (signal == GEOMETRY) {
            System.err.println("Editing GEOMETRY is not permitted");
        } else {
            signals.get(signal).smooth(signals(TIME), selection);
            history.push(signals);
            signalAsChanged(signal);
            fireListeners();
        }
    }

    public SignalPair getLastSignal(int type) {
        ArrayList<Signal> sigs = history.getLast();
        return new SignalPair(sigs.get(0), sigs.get(type));
    }

    public void handSignal(int signal, double[] time, double[] ampli) {
        if (signal == GEOMETRY) {
            System.err.println("Editing GEOMETRY is not permitted");
        } else {
            signals.get(signal).handSignal(signals(0), time, ampli);
            history.push(signals);
            signalAsChanged(signal);
            fireListeners();
        }
    }

    public void delete(int signal, double[] selection) {
        if (signal == GEOMETRY) {
            System.err.println("Editing GEOMETRY is not permitted");
        } else {
            signals.get(signal).delete(signals(TIME), selection);
            history.push(signals);
            signalAsChanged(signal);
            fireListeners();
        }
    }
    
    public double[] getTime(){
        return signals(0);
    }
}
