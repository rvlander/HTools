/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.editor.UI;

import htools.editor.editor.HollerSignal;
import htools.editor.editor.SignalPair;
import htools.editor.matlabfunctions.MatlabMethods;
import htools.hollermap.mat2java.plotter.PlotType;
import htools.hollermap.mat2java.plotter.Plotter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import matlabcontrol.MatlabInvocationException;

/**
 *
 * @author rvlander
 */
public class SignalPlotter extends AnalyzerPlotter {

    SignalPair signal;
    double[] selection;
    double[] savedSelection;
    double[] hollerSelection;

    SignalPlotter(SignalPair sig) {
        signal = sig;
        selection = new double[sig.x.signal.length];
        if (signal.y instanceof HollerSignal) {
            hollerSelection = new double[((HollerSignal) signal.y).t0.length];
        }
    }

    @Override
    public void plot() {
        plot = new Plotter();
        plot.figure("Trace", 10, 10, "10px", "10px", "Bleu : trace   * enregistrée, Rouge : trace synthétisée");

        plot.plot(signal.y.getSignal(), signal.x.getSignal(), PlotType.XYLINE, selection, Color.BLUE, Color.YELLOW);

        if (signal.y instanceof HollerSignal) {
            HollerSignal sig = (HollerSignal) (signal.y);
            double[] t0 = sig.t0;
            double[] zeros = new double[t0.length];
            plot.hold_on();
            plot.plot(zeros, t0, PlotType.XYPLOTS, hollerSelection, Color.GREEN, Color.ORANGE);
        }


    }

    public void selectAll() {
        for (int i = 0; i < selection.length; i++) {
            selection[i] = 1;
        }
        this.updateHollerSelect();
    }

    public void selectNothing() {
        for (int i = 0; i < selection.length; i++) {
            selection[i] = 0;
        }
        this.updateHollerSelect();
    }

    public void select(double[] sel) {
        for (int i = 0; i < selection.length; i++) {
            selection[i] = sel[i];
        }
        this.updateHollerSelect();

    }

    public double[] getSelection() {
        return selection;
    }

    public double[] getSavedSelection() {
        if (savedSelection == null) {
            savedSelection = Arrays.copyOf(selection, selection.length);
        }
        return savedSelection;
    }

    public double[] getRealSavedSelection() {
        return savedSelection;
    }

    public void unsaveSelection(boolean keep) {
        if (!keep) {
            this.selection = savedSelection;
        }
        savedSelection = null;
    }

    public boolean isAllSelected() {
        boolean res = true;
        int i = 0;
        while (res && i < selection.length) {
            res = selection[i] == 1;
            i++;
        }
        return res;
    }

    public boolean isNothingSelected() {
        boolean res = true;
        int i = 0;
        while (res && i < selection.length) {
            res = selection[i] == 0;
            i++;
        }
        return res;
    }

    void select(double inf, double sup) {
        for (int i = 0; i < selection.length; i++) {
            selection[i] = signal.x.signal[i] > inf && signal.x.signal[i] < sup ? 1 : selection[i];
        }
        this.updateHollerSelect();
    }

    void setSavedSelection(double[] ssel) {
        this.savedSelection = ssel;
    }

    private void updateHollerSelect() {
        if (signal.y instanceof HollerSignal) {
            try {
                HollerSignal hs = ((HollerSignal) signal.y);
                double[] mask = MatlabMethods.selectedHollerPoints(signal.x.signal, selection, hs.t0);
                for(int i=0;i<mask.length;i++){
                    hollerSelection[i] = mask[i];
                }
            } catch (MatlabInvocationException ex) {
                Logger.getLogger(SignalPlotter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
