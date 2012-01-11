//package javaapplication4;

// Description of blocks world from Russell & Norvig, 2nd edition, Section 11.2
public class Blocks {

    // constants representing blocks
    protected static final Constant A = new Constant("A");
    protected static final Constant B = new Constant("B");
    protected static final Constant C = new Constant("C");
    protected static final Constant D = new Constant("D");
    protected static final Constant E = new Constant("E");
    protected static final Constant F = new Constant("F");
    protected static final Constant G = new Constant("G");
    protected static final Constant H = new Constant("H");
    protected static final Constant I = new Constant("I");
    // constant representing table
    protected static final Constant Table = new Constant("Table");
    // variables used in actions
    private static final Variable b = new Variable("b");
    private static final Variable x = new Variable("x");
    private static final Variable y = new Variable("y");
    // action: move block b from x to y
    protected static final Action Move = new Action(
            new Sentence("Move", b, x, y),
            // preconditions
            new Atom[]{
                new Atom(new Sentence("On", b, x)),
                new Atom(new Sentence("Clear", b)),
                new Atom(new Sentence("Clear", y)),
                new Atom(new Sentence("Block", b)),
                new Atom(new Sentence("Block", y))
            },
            // inequalities
            new Inequality[]{
                new Inequality(b, x),
                new Inequality(b, y),
                new Inequality(x, y)
            },
            // effects
            new Atom[]{
                new Atom(new Sentence("On", b, y)),
                new Atom(new Sentence("Clear", x)),
                new Atom(new Sentence("On", b, x), false),
                new Atom(new Sentence("Clear", y), false)
            });
    // action: move block b from x to table
    protected static final Action MoveToTable = new Action(
            new Sentence("MoveToTable", b, x),
            // preconditions
            new Atom[]{
                new Atom(new Sentence("On", b, x)),
                new Atom(new Sentence("Clear", b)),
                new Atom(new Sentence("Block", b))
            },
            // inequalities
            new Inequality[]{
                new Inequality(b, x)
            },
            // effects
            new Atom[]{
                new Atom(new Sentence("On", b, Table)),
                new Atom(new Sentence("Clear", x)),
                new Atom(new Sentence("On", b, x), false)
            });
}
