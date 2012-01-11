
public class A2Problem3 extends Blocks {
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
                new Atom(new Sentence("Block", G)),
                new Atom(new Sentence("Block", H)),
                new Atom(new Sentence("Block", I)),
                new Atom(new Sentence("On", C, Table)),
                new Atom(new Sentence("On", F, Table)),
                new Atom(new Sentence("On", I, Table)),
                new Atom(new Sentence("On", A, B)),
                new Atom(new Sentence("On", B, C)),
                new Atom(new Sentence("On", D, E)),
                new Atom(new Sentence("On", E, F)),
                new Atom(new Sentence("On", G, H)),
                new Atom(new Sentence("On", H, I)),
                new Atom(new Sentence("Clear", A)),
                new Atom(new Sentence("Clear", D)),
                new Atom(new Sentence("Clear", G)),
            }
        );

        final Action Finish = new Action(
            new Sentence("Finish"),
            new Atom[] {
                new Atom(new Sentence("On", A, D)),
                new Atom(new Sentence("On", D, G)),
                new Atom(new Sentence("On", B, E)),
                new Atom(new Sentence("On", E, H)),
                new Atom(new Sentence("On", C, F)),
                new Atom(new Sentence("On", F, I)),
            },
            new Inequality[] {
            },
            new Atom[] {
            }
        );

        A2Main.solve(Start, Finish, Move, MoveToTable);
    }
}
