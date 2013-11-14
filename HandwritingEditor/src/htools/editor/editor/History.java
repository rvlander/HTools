/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.editor.editor;

import java.util.ArrayList;

/**
 *
 * @author rvlander
 */
public class History {

    private ArrayList<ArrayList<Signal>> history;
    int pointer;

    public History(ArrayList<Signal> tosave) {
        history = new ArrayList();
        history.add(copy(tosave));
    }

    public void push(ArrayList<Signal> tosave) {
        int n = history.size();
        while (pointer != n - 1) {
            history.remove(n - 1);
            n = history.size();
        }
        history.add(copy(tosave));
        pointer++;
    }

    public ArrayList<Signal> pop() {
        int n = history.size();
        ArrayList<Signal> res = history.get(n - 1);
        if (n > 1) {
            history.remove(n - 1);
            pointer = Math.min(pointer, n - 2);
        }

        return res;
    }

    public ArrayList<Signal> getLast() {
        int n = history.size();
        ArrayList<Signal> res = history.get(n - 1);
        return copy(res);
    }

    public ArrayList<Signal> getAtPointer() {
        ArrayList<Signal> res= history.get(pointer);
        return copy(res);
    }

    private static ArrayList<Signal> copy(ArrayList<Signal> tocopy) {
        int m = tocopy.size();
        ArrayList<Signal> tab = new ArrayList();
        for (int i = 0; i < m; i++) {
            tab.add(i,tocopy.get(i).copy());
        }
        return tab;
    }

    public ArrayList<Signal> undo() {
        pointer = Math.max(0,pointer-1);
        return getAtPointer();
    }
    
    public ArrayList<Signal> redo() {
        pointer = Math.min(history.size()-1,pointer+1);
        return getAtPointer();
    }
}
