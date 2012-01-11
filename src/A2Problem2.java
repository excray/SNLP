
public class A2Problem2 extends Blocks {
    public static void main(String[] args) {
        final Action Start = new Action(
            new Sentence("Start"),
            new Atom[] {
            },
            new Inequality[] {
            },
            new Atom[] {
                new Atom(new Sentence("Block", A)),
                new Atom(new Sentence("Block", B)),
                new Atom(new Sentence("Block", C)),
                new Atom(new Sentence("Block", D)),
                new Atom(new Sentence("Block", E)),
                new Atom(new Sentence("Block", F)),
                new Atom(new Sentence("On", B, Table)),
                new Atom(new Sentence("On", D, Table)),
                new Atom(new Sentence("On", F, Table)),
                new Atom(new Sentence("On", A, B)),
                new Atom(new Sentence("On", C, D)),
                new Atom(new Sentence("On", E, F)),
                new Atom(new Sentence("Clear", A)),
                new Atom(new Sentence("Clear", C)),
                new Atom(new Sentence("Clear", E)),
            }
        );

        final Action Finish = new Action(
            new Sentence("Finish"),
            new Atom[] {
                new Atom(new Sentence("On", A, B)),
                new Atom(new Sentence("On", B, C)),
                new Atom(new Sentence("On", D, E)),
                new Atom(new Sentence("On", E, F)),
            },
            new Inequality[] {
            },
            new Atom[] {
            }
        );

        A2Main.solve(Start, Finish, Move, MoveToTable);
    }
}
