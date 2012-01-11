/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vivek
 */
public class A2Problem6 extends Blocks {

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
                    new Atom(new Sentence("On", A, B)),
                    new Atom(new Sentence("On", B, C)),
                    new Atom(new Sentence("On", C, D)),
                    new Atom(new Sentence("On", D, Table)),
                    new Atom(new Sentence("Clear", A)),});

        final Action Finish = new Action(
                new Sentence("Finish"),
                new Atom[]{
                    new Atom(new Sentence("On", D, C)),
                    new Atom(new Sentence("On", C, B)),
                    new Atom(new Sentence("On", B, A)),
              },
                new Inequality[]{},
                new Atom[]{});

        A2Main.solve (Start, Finish, Move, MoveToTable);
}
}