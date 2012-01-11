
public class A2Problem4 extends Blocks {
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
                new Atom(new Sentence("On", B, C)),
                new Atom(new Sentence("On", C, A)),
                new Atom(new Sentence("On", A, Table)),
                new Atom(new Sentence("Clear", B)),
            }
        );

        final Action Finish = new Action(
            new Sentence("Finish"),
            new Atom[] {
                new Atom(new Sentence("On", A, B)),
                new Atom(new Sentence("On", B, C)),
            },
            new Inequality[] {
            },
            new Atom[] {
            }
        );

        A2Main.solve(Start, Finish, Move, MoveToTable);
    }
}
