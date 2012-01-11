/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vivek
 */
//package javaapplication4;
public class A2Problem5 extends Blocks {

    public static void main(String[] args) {
        final Action Start = new Action(
                new Sentence("Start"),
                new Atom[]{},
                new Inequality[]{},
                new Atom[]{
                    new Atom(new Sentence("Block", A)),
                    new Atom(new Sentence("Block", B)),
                    new Atom(new Sentence("Block", C)),
                    new Atom(new Sentence("Block", D)),
                    new Atom(new Sentence("On", A, Table)),
                    new Atom(new Sentence("On", B, Table)),
                    new Atom(new Sentence("On", C, Table)),
                    new Atom(new Sentence("On", D, Table)),
                    new Atom(new Sentence("Clear", A)),
                    new Atom(new Sentence("Clear", B)),
                    new Atom(new Sentence("Clear", D)),
                    new Atom(new Sentence("Clear", C)),});

        final Action Finish = new Action(
                new Sentence("Finish"),
                new Atom[]{
                    new Atom(new Sentence("On", A, B)),
                    new Atom(new Sentence("On", C, D)),},
                new Inequality[]{},
                new Atom[]{});

        A2Main.solve(Start, Finish, Move, MoveToTable);
    }
}
