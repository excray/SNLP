/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vivek
 */
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

// Represents expressions such as A, Table, b, x, On(A, Table), Move(A, x, y)
// Represents constant expressions such as A, B, Table
// Represents variables such as b, x, y
// Represents constant expressions such as A, B, Table
// Represents variables such as b, x, y
interface Expression {

    Expression clone();
}
// Represents constant expressions such as A, B, Table

// Represents variables such as b, x, y
class Constant implements Expression {

    private String value;

    public Constant(String value) {
        this.value = value;
    }

    private Constant(Constant aThis) {
        value = aThis.value;
    }

    public Expression clone() {
        return new Constant(this);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Constant) {
            return this.equals((Constant) that);
        } else {
            return false;
        }
    }

    public boolean equals(Constant that) {
        return this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}

// Represents variables such as b, x, y
// Represents statement expressions such as Clear(x), Move(b, C, D)
// Represents statement expressions such as Clear(x), Move(b, C, D)
class Variable implements Expression {

    private String name;
    private String InitalName = null;

    public Variable() {
    }

    public Variable(String name) {
        this.name = name;

        if (InitalName == null) {
            InitalName = "" + name;
        }
    }

    private Variable(Variable aThis) {
        name = aThis.name;
        if (InitalName == null) {
            InitalName = "" + name;
        }
    }

    public void ChangeName(int ctr) {
        name = InitalName + ctr;
    }

    public Expression clone() {
        return new Variable(this);

    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Variable) {
            return this.equals((Variable) that);
        } else {
            return false;
        }
    }

    public boolean equals(Variable that) {
        return this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    Object getName() {
        return name;
    }
}

// Represents inequalities such as b != x, x != A
class Inequality {

    public Expression x, y;

    public Inequality(Expression x, Expression y) {
        this.x = x.clone();
        this.y = y.clone();
    }

    public Inequality clone() {
        return new Inequality(x, y);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Inequality) {
            return equals((Inequality) that);
        } else {
            return false;
        }
    }

    public boolean equals(Inequality that) {
        return (this.x.equals(that.x) && this.y.equals(that.y))
                || (this.x.equals(that.y) && this.y.equals(that.x));
    }

    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode();
    }

    @Override
    public String toString() {
        return x + "!=" + y;
    }
}

class Atom {

    private Expression expression;
    private boolean positive;

    public Atom() {
    }

    @Override
    public Atom clone() {
        return new Atom(expression.clone(), positive);
    }

    public Atom(Expression expression) {
        this(expression, true);
    }

    public Atom(Expression expression, boolean positive) {
        this.expression = expression;
        this.positive = positive;
    }

    public boolean getPositive() {
        return positive;
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Atom) {
            return equals((Atom) that);
        } else {
            return false;
        }
    }

    public boolean equals(Atom that) {
        return this.positive == that.positive
                && this.expression.equals(that.expression);
    }

    @Override
    public int hashCode() {
        return expression.hashCode() + (positive ? 0 : 1);
    }

    @Override
    public String toString() {
        return (positive ? "" : "!") + expression;
    }

    public boolean compare(Atom b) {
        if (this.toString().equals(b.toString())) {
            return true;
        }
        return false;
    }

    public Expression getExpression() {
        return expression;
    }

    public List<Expression> getArgumentList() {
        return ((Sentence) this.getExpression()).getArgumentList();
    }

    public String getFunctionName() {
        return ((Sentence) (this.getExpression())).getFunctionName();
    }
}

// Represents statement expressions such as Clear(x), Move(b, C, D)
class Sentence implements Expression {

    private String function;
    private ArrayList<Expression> arguments;

    public Sentence() {
    }

    public Sentence(Sentence aThis) {
        function = aThis.function;
        arguments = new ArrayList(aThis.arguments.size());

        for (Expression a : aThis.arguments) {
            arguments.add(a.clone());
        }
    }

    @Override
    public Expression clone() {
        return new Sentence(this);
    }

    public Sentence(String function) {
        this(function, new ArrayList<Expression>(0));
    }

    public Sentence(String function, ArrayList<Expression> arguments) {
        this.function = function;
        this.arguments = arguments;
    }

    public Sentence(String function, Expression... arguments) {
        ArrayList<Expression> expClone = new ArrayList<Expression>();
        //int c = 0;
        for (Expression arg : arguments) {
            expClone.add(arg.clone());
        }
        this.function = function;
        this.arguments = expClone;
    }

    public List<Expression> getArgumentList() {
        return arguments;
    }

    public String getFunctionName() {
        return function;
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Sentence) {
            return equals((Sentence) that);
        } else {
            return false;
        }
    }

    public boolean equals(Sentence that) {
        if (!this.function.equals(that.function)) {
            return false;
        }
        ListIterator<Expression> i = this.arguments.listIterator();
        ListIterator<Expression> j = that.arguments.listIterator();
        while (i.hasNext() && j.hasNext()) {
            if (!i.next().equals(j.next())) {
                return false;
            }
        }
        return !i.hasNext() && !j.hasNext();
    }

    @Override
    public int hashCode() {
        int hash = function.hashCode();
        for (Expression a : arguments) {
            hash += a.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        String s = function + "(";
        for (int i = 0; i < arguments.size(); i++) {
            if (i != 0) {
                s += ",";
            }
            s += arguments.get(i);
        }
        return s + ")";
    }
}

class CausalLink {

    private Action op1;
    private Action op2;
    private Atom condition;

    public CausalLink(Action op1, Action ope2, Atom condition) {
        this.op1 = op1;
        this.op2 = ope2;
        this.condition = condition;
    }

    private CausalLink(CausalLink _cl) {
        op1 = _cl.op1.clone();
        op2 = _cl.op2.clone();
        condition = _cl.condition.clone();
    }

    @Override
    public CausalLink clone() {
        return new CausalLink(this);
    }

    public Action GetAction2() {
        return op2;
    }

    public Action GetAction1() {
        return op1;
    }

    public Atom GetCondition() {
        return condition;
    }

    @Override
    public String toString() {
        String cond;
        if (condition == null) {
            cond = "<";
        } else {
            cond = condition.toString();
        }
        String toprint = op1.toString() + "--" + cond + "--" + op2.toString();
        if( condition == null) toprint+="(Threat)";
        return toprint;
    }
}

class Threat {

    private CausalLink l;
    private Action op;
    private Atom condition;

    public Threat(CausalLink l, Action op, Atom condition) {
        this.l = l;
        this.op = op;
        this.condition = condition;
    }

    public CausalLink getCausalLink() {
        return l;
    }

    public Action getAction() {
        return op;
    }

    public Atom getCond() {
        return condition;
    }
}

class AtomPair {

    public Atom a;
    public Atom b;

    public AtomPair(Atom a1, Atom a2) {
        this.a = a1;
        this.b = a2;
    }

    public AtomPair clone() {
        return new AtomPair(a, b);
    }
}

class Action {

    private Expression expression;
    private ArrayList<Atom> preconditions;
    private ArrayList<Inequality> inequalities;
    private ArrayList<Atom> effects;
    final CopyHelper copyHelper = new CopyHelper();

    public Action(Expression expression, Atom[] preconditions, Inequality[] inequalities, Atom[] effects) {
        this.expression = expression;
        this.preconditions = new ArrayList();

        for (int i = 0; i < preconditions.length; i++) {
            this.preconditions.add(preconditions[i]);
        }

        this.inequalities = new ArrayList();

        for (int i = 0; i < inequalities.length; i++) {
            this.inequalities.add(inequalities[i]);
        }

        this.effects = new ArrayList();

        for (int i = 0; i < effects.length; i++) {
            this.effects.add(effects[i]);
        }

    }

    private Action(Action orig) {
        this.expression = orig.expression.clone();
        preconditions = copyHelper.CopyAtomList(orig.preconditions);
        inequalities = copyHelper.CopyIneqList(orig.inequalities);
        effects = copyHelper.CopyAtomList(orig.effects);
    }

    @Override
    public Action clone() {
        return new Action(this);
    }

    @Override
    public String toString() {
        return expression.toString();
    }

    public boolean equals(Action orig) {
        return this.toString().equals(orig.toString());
    }

    public List<Atom> getPreCond() {
        return preconditions;
    }

    public List<Atom> getEffects() {
        return effects;
    }

    List<Inequality> GetInqualities() {
        return inequalities;
    }

    void substitute(Hashtable substitutionMap) {
        ListIterator<Expression> expItr = ((Sentence) expression).getArgumentList().listIterator();
        while (expItr.hasNext()) {
            Expression _expression = expItr.next();
            if (_expression instanceof Variable) {
                if (substitutionMap.containsKey(_expression)) {

                    Expression t = (Expression) substitutionMap.get(_expression);
                    Expression temp = t.clone();
                    expItr.set(temp);
                }
            }
        }
        //Sustitute Precond
        ListIterator<Atom> atomItr = preconditions.listIterator();
        while (atomItr.hasNext()) {
            Atom _atom = atomItr.next();
            expItr = ((Sentence) _atom.getExpression()).getArgumentList().listIterator();
            while (expItr.hasNext()) {
                Expression e = expItr.next();
                if (e instanceof Variable) {
                    if (substitutionMap.containsKey(e)) {
                        Expression t = (Expression) substitutionMap.get(e);
                        Expression temp = t.clone();
                        expItr.set(temp);
                    }
                }
            }
        }

        //Substitute Effects
        atomItr = effects.listIterator();
        while (atomItr.hasNext()) {
            Atom _atom = atomItr.next();
            expItr = ((Sentence) _atom.getExpression()).getArgumentList().listIterator();
            while (expItr.hasNext()) {
                Expression e = expItr.next();
                if (e instanceof Variable) {
                    if (substitutionMap.containsKey(e)) {
                        //Atom term = (Atom) var.get(e);
                        Expression t = (Expression) substitutionMap.get(e);
                        Expression temp = t.clone();
                        expItr.set(temp);
                    }
                }
            }
        }

        //Substiute Inequalities
        ListIterator<Inequality> inItr = inequalities.listIterator();
        while (inItr.hasNext()) {
            Inequality elem = inItr.next();
            Expression a = elem.x;
            Expression b = elem.y;

            if (a instanceof Variable) {
                if (substitutionMap.containsKey(a)) {
                    Expression t = (Expression) substitutionMap.get(a);
                    a = t.clone();
                }
            }
            if (b instanceof Variable) {
                if (substitutionMap.containsKey(b)) {

                    Expression t = (Expression) substitutionMap.get(b);
                    b = t.clone();
                }
            }

            Inequality inTemp = new Inequality(a, b);
            inItr.set(inTemp);
        }
    }
    static int variablecounter = 0;

    void renameVariables() {
        ListIterator<Expression> itr = ((Sentence) expression).getArgumentList().listIterator();

        while (itr.hasNext()) {
            Expression e = itr.next();
            if (e instanceof Variable) {
                ((Variable) e).ChangeName(variablecounter);
            }
        }


//        //Sustitute Precond
        ListIterator<Atom> litr = preconditions.listIterator();
        while (litr.hasNext()) {
            Atom _atom = litr.next();
            itr = ((Sentence) _atom.getExpression()).getArgumentList().listIterator();
            while (itr.hasNext()) {
                Expression e = itr.next();
                if (e instanceof Variable) {
                    ((Variable) e).ChangeName(variablecounter);
                }
            }
        }
        //Substiute Inequalities
        ListIterator<Inequality> inItr = inequalities.listIterator();
        while (inItr.hasNext()) {
            Inequality elem = inItr.next();
            Expression a = elem.x;
            Expression b = elem.y;

            if (a instanceof Variable) {
                ((Variable) a).ChangeName(variablecounter);
            }
            if (b instanceof Variable) {
                ((Variable) b).ChangeName(variablecounter);
            }

            Inequality inTemp = new Inequality(a, b);
            inItr.set(inTemp);
        }
        //Substitute Effects
        litr = effects.listIterator();
        while (litr.hasNext()) {
            Atom _atom = litr.next();
            itr = ((Sentence) _atom.getExpression()).getArgumentList().listIterator();
            while (itr.hasNext()) {
                Expression e = itr.next();
                if (e instanceof Variable) {
                    ((Variable) e).ChangeName(variablecounter);
                }
            }
        }

        variablecounter++;
    }
}

class CopyHelper {

    public ArrayList<Inequality> CopyIneqList(ArrayList<Inequality> list) {
        ArrayList<Inequality> clonedList = new ArrayList<Inequality>(list.size());

        for (Inequality o : list) {
            clonedList.add(o.clone());
        }
        return clonedList;
    }

    public ArrayList<Atom> CopyAtomList(List<Atom> listToClone) {
        ArrayList<Atom> clonedList = new ArrayList<Atom>();

        if (listToClone == null) {
            return clonedList;
        }

        for (Atom o : listToClone) {
            clonedList.add(o.clone());
        }
        return clonedList;
    }

    Map<Action, List<Atom>> copyOpenPreCond(Map<Action, List<Atom>> openPreCond) {

        Map<Action, List<Atom>> clonedOpenPreCond = new HashMap<Action, List<Atom>>();
        Iterator it = openPreCond.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            //System.out.println(pairs.getKey() + " = " + pairs.getValue());
            // it.remove(); // avoids a ConcurrentModificationException

            Action origAction = (Action) pairs.getKey();
            List<Atom> origPreCondList = (List<Atom>) pairs.getValue();

            Action clonedAction = origAction.clone();
            List<Atom> clonedPreCondList = CopyAtomList(origPreCondList);

            clonedOpenPreCond.put(clonedAction, clonedPreCondList);
        }

        return clonedOpenPreCond;
    }

    List<Action> copyActionList(List<Action> actions) {

        List<Action> clonedActionList = new ArrayList<Action>();

        for (Action _a : actions) {
            clonedActionList.add(_a.clone());
        }

        return clonedActionList;
    }

    List<CausalLink> copyCausalLinks(List<CausalLink> causalLinks) {
        List<CausalLink> clonedCausal = new ArrayList<CausalLink>();

        for (CausalLink _cl : causalLinks) {
            clonedCausal.add(_cl.clone());
        }

        return clonedCausal;
    }

    List<AtomPair> copySepConstraint(List<AtomPair> seperation_constraints) {

        List<AtomPair> clonedSepConstrnt = new ArrayList<AtomPair>();

        for (AtomPair _ap : seperation_constraints) {
            clonedSepConstrnt.add(_ap.clone());
        }

        return clonedSepConstrnt;
    }
}

class SNLPState {

    Map<Action, List<Atom>> openPreCond;
    List<Action> actions;
    List<CausalLink> causalLinks;
    List<AtomPair> seperation_constraints;
    final CopyHelper copyHelper = new CopyHelper();
    // List<Atom> PreCondAndEffRefHolder;

    public SNLPState() {
        openPreCond = new LinkedHashMap<Action, List<Atom>>();
        actions = new ArrayList<Action>();
        causalLinks = new ArrayList<CausalLink>();
        seperation_constraints = new ArrayList<AtomPair>();
        // PreCondAndEffRefHolder = new ArrayList<Atom>();
    }

    private SNLPState(SNLPState aThis) {
        //copy actions and update referencces

        //openPreCond = copyHelper.copyOpenPreCond(aThis.openPreCond);
        actions = copyHelper.copyActionList(aThis.actions);
        // causalLinks = copyHelper.copyCausalLinks(aThis.causalLinks);
        //seperation_constraints = copyHelper.copySepConstraint(aThis.seperation_constraints);

        openPreCond = new HashMap<Action, List<Atom>>();

        //update Action references
        for (int i = 0; i < aThis.actions.size(); i++) {
            if (aThis.openPreCond.containsKey(aThis.actions.get(i))) {
                //got the clonedAction
                Action clonedAction = actions.get(i);

                //Get the list of precond references
                List<Atom> openPreconds = aThis.openPreCond.get(aThis.actions.get(i));
                List<Atom> totalPre = aThis.actions.get(i).getPreCond();
                List<Atom> clonedTotalPre = actions.get(i).getPreCond();
                List<Atom> clonedOpenPre = new ArrayList<Atom>();

                //iterate through total precond of orig
                for (int t = 0; t < openPreconds.size(); t++) {
                    //find if open precond of orig contains the reference
                    int index = totalPre.indexOf(openPreconds.get(t));
                    if (index != -1) {
                        //if found, add the reference
                        clonedOpenPre.add(clonedTotalPre.get(index));
                    }
                }

                //Now add the action to map
                openPreCond.put(clonedAction, clonedOpenPre);
            }
        }

        //update causal link action references
        causalLinks = new ArrayList<CausalLink>();
        Action a1 = null, a2 = null;
        Atom cond = null;
        for (CausalLink origL : aThis.causalLinks) {
            int index = aThis.actions.indexOf(origL.GetAction1());
            if (index != -1) {
                //found action
                a1 = actions.get(index);
                int index1 = aThis.actions.indexOf(origL.GetAction2());

                if (index1 != -1) {
                    a2 = actions.get(index1);

                    int index2 = origL.GetAction2().getPreCond().indexOf(origL.GetCondition());
                    if (index2 != -1) {
                        cond = a2.getPreCond().get(index2);
                    }
                    else
                    {
                        cond = null;
                    }
                }
                else
                {
                    System.exit(1);
                }

            }
            if (a1 != null && a2 != null ) {
                causalLinks.add(new CausalLink(a1, a2, cond));
            }
        }

        //update seperation constraints
        //seperation_constraints = new ArrayList<AtomPair>();
        //for( AtomPair _ap : aThis.seperation_constraints)
        //{
        //    int index = aThis.
        //}

    }

    public SNLPState clone() {
        return new SNLPState(this);
    }

    public SNLPState clone(Action _act) {
        SNLPState snlp = new SNLPState(this);
        snlp.addAction(_act);
        snlp.addOpenPreCond(_act, _act.getPreCond());
        return snlp;
    }

    public int GetOpenPreCondLength() {
        int i = 0;
        Map<Action, List<Atom>> openPreCond = getOpenPreCond();
        Iterator it = openPreCond.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry mapItem = (Map.Entry) it.next();
            Action act = (Action) mapItem.getKey();
            List<Atom> openPreCondList = (List<Atom>) mapItem.getValue();
            i += openPreCondList.size();
        }
        return i;
    }

    public List<Action> getLinearization() {
        System.out.println("Partial Plan Found");
        System.exit(0);
        List<Action> linearActions = new ArrayList<Action>();
        return linearActions;
    }
    // public boolean circularityError = false;

    public boolean action_before(Action a1, Action a2) {

        if (a1.equals(getActions().get(1))) {
            return false;
        }
        if (a2.equals(getActions().get(0))) {
            return false;
        }

        Action original_act2 = a2;
        ArrayList<Action> already_added = new ArrayList<Action>();
        ArrayList<Action> before_actions = new ArrayList<Action>();
        Iterator<CausalLink> clitr = causalLinks.iterator();

        CausalLink link;

        before_actions.add(a2);
        already_added.add(a2);

        while (!before_actions.isEmpty()) {
            a2 = before_actions.remove(0);
            while (clitr.hasNext()) {
                link = clitr.next();
                if (link.GetAction2().equals(a2)) {
                    if (original_act2.equals(link.GetAction1())) {
                        before_actions.clear();
                        already_added.clear();
                       // System.out.println("Operator Before Error: Circularity in dependencies");
//                        circularityError = true;
                        return true;
                    }

                    if (a1.equals(link.GetAction1())) {
                        before_actions.clear();
                        already_added.clear();
                        return true;
                    }

                    if (!already_added.contains(link.GetAction1())) {
                        before_actions.add(link.GetAction1());
                        already_added.add(link.GetAction1());
                    }
                }
            }
        }

        already_added.clear();

        return false;
    }

    public void addCausalLink(SNLPState _state, Action s, Atom p, Action w) {
        List<Action> origActions = (List<Action>) _state.getActions();
        assert (origActions.size() == actions.size()) : "Clone state doesnt have same no of actions";

        int index = origActions.indexOf(s);
        assert (actions.get(index).equals(s)) : "Clone state doesnt have the same action at index " + index;
        Action clonedS = actions.get(index);

        index = origActions.indexOf(w);
        assert (actions.get(index).equals(w)) : "Clone state doesnt have the same action at index " + index
                + "clonedAction: " + actions.get(index).toString() + " orig action: " + w.toString();
        Action clonedW = actions.get(index);

//        assert (openPreCond.containsKey(clonedW)) : "clon state doesnt contain action in precond: " + clonedW.toString();


        Atom clonedP;
        if (p == null) {
            clonedP = null;
        } else {
            List<Atom> precond = w.getPreCond();
            assert (precond.contains(p)) : "Precnd list doesnt have p: " + p.toString();

            List<Atom> clonedprecond = clonedW.getPreCond();

            index = precond.indexOf(p);

            clonedP = clonedprecond.get(index);

            assert (clonedP.compare(p)) : "Not the same cond: " + clonedP.toString() + " and " + p.toString();
        }
        CausalLink c = new CausalLink(clonedS, clonedW, clonedP);
        causalLinks.add(c);
    }

    public void removeCondition(SNLPState _state, Action act, Atom cond) {

        List<Action> origActions = (List<Action>) _state.getActions();
        //  assert (origActions.size() == actions.size()) : "Clone state doesnt have same no of actions";

        int index = origActions.indexOf(act);
        assert (actions.get(index).equals(act)) : "Clone state doesnt have the same action at index " + index;
        Action clonedS = actions.get(index);

        assert (openPreCond.containsKey(clonedS)) : "Exception: Action for which precond is to be removed not found";


        List<Atom> openPreconds = openPreCond.get(clonedS);

        int indexMatch = -1;

        for (int i = 0; i < openPreconds.size(); i++) {
            Atom cc = openPreconds.get(i);
            if (cc.compare(cond)) {
                indexMatch = i;
                break;
            }
        }

        assert (indexMatch != -1) : "Cond to be removed not found: " + cond.toString();

        Atom cloned_cond = openPreconds.get(indexMatch);

        assert (openPreconds.contains(cloned_cond)) : "Exception: Condition to be removed not found";
        openPreconds.remove(cloned_cond);
        if (openPreconds.isEmpty()) {
            openPreCond.remove(clonedS);
        } else {
            openPreCond.put(clonedS, openPreconds);
        }

    }

    public boolean canUnify(Atom a, Atom b) {
        return false;
    }

    public void addAction(Action a) {
        actions.add(a);
    }

    public void addOpenPreCond(Action a, List<Atom> c) {
        openPreCond.put(a, c);
    }

    boolean isOpenPreCondEmpty() {
        return openPreCond.isEmpty();
    }

    Map<Action, List<Atom>> getOpenPreCond() {
        return openPreCond;
    }

    List<Action> getActions() {
        return actions;

    }

    void substitute(SNLPState _state, Action a, Hashtable SubsitutionMap) {
        List<Action> origActions = (List<Action>) _state.getActions();
//        assert (origActions.size() == actions.size()) : "Clone state doesnt have same no of actions";

        int index = origActions.indexOf(a);
        assert (actions.get(index).equals(a)) : "Clone state doesnt have the same action at index";
        Action clonedAction = actions.get(index);
        clonedAction.substitute(SubsitutionMap);
    }

    List<CausalLink> getCausalLinks() {
        return causalLinks;
    }

    void addCausalLink_new(SNLPState _state, Action s, Atom p, Action w) {
        List<Action> origActions = (List<Action>) _state.getActions();
        // assert (origActions.size() == actions.size()) : "Clone state doesnt have same no of actions";

        //  int index = origActions.indexOf(s);
        //   assert (actions.get(index).equals(s)) : "Clone state doesnt have the same action at index " + index;
        //   Action clonedS = actions.get(index);

        int index = origActions.indexOf(w);
        assert (actions.get(index).equals(w)) : "Clone state doesnt have the same action at index " + index;
        Action clonedW = actions.get(index);

        assert (openPreCond.containsKey(clonedW)) : "clon state doesnt contain action in precond";
        List<Atom> precond = w.getPreCond();
        assert (precond.contains(p)) : "Precnd list doesnt have p";

        List<Atom> clonedprecond = clonedW.getPreCond();

        index = precond.indexOf(p);

        Atom clonedP = clonedprecond.get(index);

        assert (clonedP.compare(p)) : "Not the same cond";

        CausalLink c = new CausalLink(s, clonedW, clonedP);
        causalLinks.add(c);
    }

    public void removeCondition_new(SNLPState _state, Action act, Atom cond) {

        List<Action> origActions = (List<Action>) _state.getActions();
        // assert (origActions.size() == actions.size()) : "Clone state doesnt have same no of actions";

        int index = origActions.indexOf(act);
        assert (actions.get(index).equals(act)) : "Clone state doesnt have the same action at index " + index;
        Action clonedS = actions.get(index);

        assert (openPreCond.containsKey(clonedS)) : "Exception: Action for which precond is to be removed not found";


        List<Atom> openPreconds = openPreCond.get(clonedS);

        int indexMatch = -1;

        for (int i = 0; i < openPreconds.size(); i++) {
            Atom cc = openPreconds.get(i);
            if (cc.compare(cond)) {
                indexMatch = i;
                break;
            }
        }

        assert (indexMatch != -1) : "Cond to be removed not found";

        Atom cloned_cond = openPreconds.get(indexMatch);

        assert (openPreconds.contains(cloned_cond)) : "Exception: Condition to be removed not found";
        openPreconds.remove(cloned_cond);
        if (openPreconds.isEmpty()) {
            openPreCond.remove(clonedS);
        } else {
            openPreCond.put(clonedS, openPreconds);
        }

    }

    boolean OrderConsistent() {
        Map<Action, Boolean> NodesWithIncomingEdges = new LinkedHashMap<Action, Boolean>();

        List<CausalLink> graph = new ArrayList<CausalLink>(getCausalLinks());
        List<CausalLink> ntomedges = new ArrayList<CausalLink>();

        for (Action a : getActions()) {
            NodesWithIncomingEdges.put(a, false);
        }

        for (CausalLink cl : graph) {
            NodesWithIncomingEdges.put(cl.GetAction2(), true);
        }

        List<Action> nodeswithoutincoming = new ArrayList<Action>();
        for (Action a : NodesWithIncomingEdges.keySet()) {
            if (!NodesWithIncomingEdges.get(a)) {
                nodeswithoutincoming.add(a);
            }
        }

        List<Action> L = new ArrayList<Action>();

        while (!nodeswithoutincoming.isEmpty()) {
            Action n = nodeswithoutincoming.remove(0);
            L.add(n);
            for (CausalLink cl : graph) {
                if (cl.GetAction1().equals(n)) {
                    ntomedges.add(cl);
                }
            }

            for (CausalLink edge : ntomedges) {
                Action m = edge.GetAction2();
                graph.remove(edge);

                for (CausalLink l : graph) {
                    boolean mhasincoming = false;

                    if (l.GetAction2().equals(m)) {
                        mhasincoming = true;
                    }
                    if (!mhasincoming) {
                        if (!nodeswithoutincoming.contains(m)) {
                            nodeswithoutincoming.add(m);
                        }
                    }
                }

            }


        }

        if (!graph.isEmpty()) {
            return false;
        }

        return true;
    }
}

public class A2Main {

    static ArrayList<Action> newActions = new ArrayList<Action>();
    static Hashtable SubsitutionMap = new Hashtable();

    /**
     * @param args the command line arguments
     */
    public static void solve(Action... actions) {

        Action start = actions[0];
        Action finish = actions[1];

        for (int i = 2; i < actions.length; i++) {
            newActions.add(actions[i]);
        }

        SNLPState inital_problem = new SNLPState();
        inital_problem.addAction(start.clone());
        Action finishC = finish.clone();
        inital_problem.addAction(finishC);

        //Action a = finish.clone();
        List<Atom> openPreConList = new ArrayList<Atom>();
        for (Atom i : finishC.getPreCond()) {

            openPreConList.add(i);
        }

        inital_problem.addOpenPreCond(finishC, openPreConList);

        Comparator<SNLPState> comparator = new OpenPreCondLengthComp();
        PriorityQueue<SNLPState> open = new PriorityQueue<SNLPState>(10, comparator);
        // List<SNLPState> open = new ArrayList<SNLPState>();

        List<SNLPState> next;
        SNLPState state;

        open.add(inital_problem);

        while (!open.isEmpty()) {
            state = open.remove();
            PrintState(state);
            if (state.getActions().size() < 15 && state.OrderConsistent()) {
                if (state.isOpenPreCondEmpty()) {
                    List<Action> plan = state.getLinearization();
                    //Print plan
                } else {
                    next = SNLP_get_succ_step1(state);

                    if (next != null) {
                        open.addAll(next);
                    }
                }
            }
        }
    }

    private static List<SNLPState> SNLP_get_succ_step1(SNLPState state) {
        List<SNLPState> successors = new ArrayList<SNLPState>();
        List<SNLPState> successors_tmp;

        Map<Action, List<Atom>> openPreCond = state.getOpenPreCond();
        Iterator it = openPreCond.entrySet().iterator();
        List<SNLPState> l3 = new ArrayList<SNLPState>();
        while (it.hasNext()) {
            Map.Entry mapItem = (Map.Entry) it.next();
            Action act = (Action) mapItem.getKey();
            List<Atom> openPreCondList = (List<Atom>) mapItem.getValue();
            for (Atom p : openPreCondList) {

                // System.out.println("Get existing actions for state: ");
                // PrintState(state);
                List<SNLPState> l2 = SNLP_get_exising_satisfying_actions(state, p, act);

                for (SNLPState s2 : l2) {
                    // System.out.println("Get threats for state: ");
                    //PrintState(s2);

                    if (s2.OrderConsistent()) {
                        successors_tmp = SNLP_getsucc_step2(s2);
                       // System.out.println("After Resolving threats and adding exisitng action");
                      //  PrintState(s2);

                        for (SNLPState succ : successors_tmp) {
                            if (succ.OrderConsistent()) {
                                successors.add(succ);
                            }
                        }
                    }
                    // return successors;
                }

                //   System.out.println("Get new actions for state: ");
                //  PrintState(state);
                if (l2.isEmpty()) {
                    l3 = SNL_get_new_satisfying_actions(state, p, act);
                }

                for (SNLPState s2 : l3) {
                    //    System.out.println("Get threats for state: ");
                    //    PrintState(s2);
                    if (s2.OrderConsistent()) {
                        successors_tmp = SNLP_getsucc_step2(s2);
                        for (SNLPState succ : successors_tmp) {
                            if (succ.OrderConsistent()) {
                                successors.add(succ);
                            }
                        }
                        // return successors;
                      //  System.out.println("After Resolving threats and adding new action");
                      //  PrintState(s2);
                    }

                }
                l3.clear();

            }
        }

        return successors;
    }

    private static List<SNLPState> SNLP_get_exising_satisfying_actions(SNLPState _state, Atom _cond, Action _act) {
        List<SNLPState> l = new ArrayList<SNLPState>();
        boolean stepAdded = false;

        for (Action a : _state.getActions()) {
            if (stepAdded) {
                break;
            }
            if (!a.equals(_act) && !_state.action_before(_act, a)) {
//                if (_state.circularityError) {
                //                  _state.circularityError = false;
                //             break;
                //         }
                for (Atom g : a.getEffects()) {

                    if (g.getPositive() != _cond.getPositive()) {
                        continue;
                    }

                    //unify g and _p
                    Hashtable uniMap = Unify(g, _cond, SubsitutionMap);

                    if (uniMap != null) {
                        if (checkInequality(a) || checkInequality(_act)) {
                            break;
                        }

                        //     System.out.println("At existing action, Action " + a.toString() + "\'s effect " + g
                        //             + "can unify cond " + _cond.toString() + " for action " + _act.toString());

                        SNLPState state2 = _state.clone();

                        state2.addCausalLink(_state, a, _cond, _act);
                        state2.removeCondition(_state, _act, _cond);

                        state2.substitute(_state, a, SubsitutionMap);
                        state2.substitute(_state, _act, SubsitutionMap);

                        //    System.out.println("At existing action, after sub, action1 -> " + a.toString() + " "
                        //            + "action2 -> " + _act.toString());

                        //  System.out.println("At existing action: Causal link added: " + state2.getCausalLinks().get(state2.getCausalLinks().size() - 1).toString());

                        l.add(state2);
                        //stepAdded = true;
                        //break;
                    }
                }
            }
        }
        return l;
    }

    private static List<SNLPState> SNLP_getsucc_step2(SNLPState _state) {

        List<SNLPState> succ = new ArrayList<SNLPState>();
        List<SNLPState> succ_temp = new ArrayList<SNLPState>();

        List<CausalLink> clist = _state.getCausalLinks();
        Threat threat = null;

        List<Action> actions = _state.getActions();

        for (CausalLink cl : clist) {
            if (threat != null) {
                break;
            }
            for (Action op : actions) {
                if (threat != null) {
                    break;
                }
                if (!op.equals(cl.GetAction1()) && !op.equals(cl.GetAction2())) {
                    if (!_state.action_before(op, cl.GetAction1())
                            && !_state.action_before(cl.GetAction2(), op)) {
//                        if (_state.circularityError) {
//                            _state.circularityError = false;
//                            break;
//                        }
                        Atom threatenedCond = cl.GetCondition();

                        for (Atom effect : op.getEffects()) {
                            if (threat != null) {
                                break;
                            }

                            //    if (effect.getPositive() != threatenedCond.getPositive()) {
                            //        continue;
                            //    }

                            if (threatenedCond == null) {
                                break;
                            }
                            Hashtable unimap = Unify(effect, threatenedCond, SubsitutionMap);
                            if (unimap != null) {
                                if (checkInequality(op) || checkInequality(cl.GetAction1()) || checkInequality(cl.GetAction2())) {
                                    // System.out.println("Threat: Inequality");
                                    break;
                                }
                                //  System.out.println("Threat found: Action " + op.toString() + " is threat to causal link "
                                //          + cl.GetAction1().toString() + "-" + cl.GetCondition().toString() + "-" + cl.GetAction2().toString());
                                threat = new Threat(cl, op, effect);
                            }
                        }
                    } else {
                        //System.out.println("Circular dependency");
                    }
                } else {
                    // System.out.println("Action same as cl action");
                }
            }
        }

        if (threat == null) {
            boolean add = succ.add(_state.clone());
        } else {
            //op before causal libk
            List<SNLPState> next_states = new ArrayList();
            if (!_state.action_before(threat.getCausalLink().GetAction1(), threat.getAction())
                    && !_state.getActions().get(0).equals(threat.getCausalLink().GetAction1())
                    && !_state.getActions().get(1).equals(threat.getAction())) { //not start
                SNLPState stateNew = _state.clone();
                //CausalLink cl = new CausalLink((), null, null)
                stateNew.addCausalLink(_state, threat.getAction(), null, threat.getCausalLink().GetAction1());
                next_states.add(stateNew);
            }
            if (!_state.action_before(threat.getAction(), threat.getCausalLink().GetAction2())
                    && !_state.getActions().get(1).equals(threat.getCausalLink().GetAction2())
                    && !_state.getActions().get(0).equals(threat.getAction())) {
                SNLPState stateNew = _state.clone();
                stateNew.addCausalLink(_state, threat.getCausalLink().GetAction2(), null, /*threat.getCond()*/ threat.getAction());
                next_states.add(stateNew);
            }

            //seperation constrnt
            //  AtomPair sep_con = new AtomPair(threat.getCausalLink().GetCondition(), threat.getCond());

            while (!next_states.isEmpty()) {
                SNLPState state = next_states.remove(0);
                succ_temp = SNLP_getsucc_step2(state);
                succ.addAll(succ_temp);
            }

        }

        return succ;

    }

    private static List<SNLPState> SNL_get_new_satisfying_actions(SNLPState _state, Atom _cond, Action _act) {

        List<SNLPState> stateList = new ArrayList<SNLPState>();
        boolean stepAdded = false;
        List<Action> actionsNew = new ArrayList<Action>(newActions);

        while (!actionsNew.isEmpty()) {
            if (stepAdded) {
                break;
            }
//            if (_state.circularityError) {
//                _state.circularityError = false;
//                break;
//            }
            Action _op = actionsNew.remove(0);
            //hack
            Action op = _op.clone();
            op.renameVariables();

            List<Atom> effects = op.getEffects();

            for (Atom effect : effects) {
                //unify g and _p
                if (effect.getPositive() != _cond.getPositive()) {
                    continue;
                }
                Hashtable uniMap = Unify(effect, _cond, SubsitutionMap);

                if (uniMap != null) {
                    if (checkInequality(op) || checkInequality(_act)) {
                        break;
                    }
                    // System.out.println("At new action, Action " + op.toString() + "\'s effect " + effect
                    //         + "can unify cond " + _cond.toString() + " for action " + _act.toString());

                    SNLPState state2 = _state.clone(op);



                    state2.addCausalLink_new(_state, op, _cond, _act);
                    state2.removeCondition_new(_state, _act, _cond);

                    op.substitute(SubsitutionMap);
                    //state2.substitute(_state, op, SubsitutionMap);
                    state2.substitute(_state, _act, SubsitutionMap);

                    //  System.out.println("At new action, after sub, action1 -> " + op.toString() + " "
                    //          + "action2 -> " + _act.toString());

                    //  System.out.println("At new action: Causal link added: " + state2.getCausalLinks().get(state2.getCausalLinks().size() - 1).toString());

                    stateList.add(state2);
                    // stepAdded = true;
                    //  break;
                }
            }

        }

        return stateList;
    }

    public static Hashtable Unify(Object leftPred, Object rightPred, Hashtable subMap) {
        if (subMap == null) {
            return null;
        } else if ((leftPred).equals(rightPred)) {
            return subMap;
        } else if (isVariable(leftPred)) {
            return UnifyVar((Variable) leftPred, rightPred, subMap);
        } else if (isVariable(rightPred)) {
            return UnifyVar((Variable) rightPred, leftPred, subMap);
        } else if (isSentence(leftPred) && isSentence(rightPred)) {
            return Unify(((Atom) leftPred).getArgumentList(), ((Atom) rightPred).getArgumentList(), Unify(((Atom) leftPred).getFunctionName(), ((Atom) rightPred).getFunctionName(), subMap));
        } else if (isList(leftPred) && isList(rightPred)) {

            Expression leftPred1 = (Expression) ((ArrayList) leftPred).get(0);
            Expression rightPred1 = (Expression) ((ArrayList) rightPred).get(0);

            ArrayList leftPred2 = new ArrayList(((ArrayList) leftPred));
            ArrayList rightPred2 = new ArrayList(((ArrayList) rightPred));


            ((ArrayList) leftPred2).remove(0);
            ((ArrayList) rightPred2).remove(0);

            return Unify(leftPred2, rightPred2, Unify(leftPred1, rightPred1, subMap));
        } else {
            return null;
        }
    }

    private static boolean isVariable(Object a) {
        try {
            if (a.getClass().isInstance(new Variable())) {
                return true;
            }

            if (a.getClass().isInstance(new Atom())) {
                return ((Atom) a).getExpression().getClass().isInstance(new Variable()) ? true : false;
            }
        } catch (Exception e) {
            System.out.print("Exception");
        }
        return false;


    }

    private static boolean isSentence(Object a) {
        if (a.getClass().isInstance(new Sentence())) {
            return true;
        }
        if (a.getClass().isInstance(new Atom())) {
            return ((Atom) a).getExpression().getClass().isInstance(new Sentence()) ? true : false;
        }
        return false;
    }

    private static boolean isList(Object list) {

        return list.getClass().isInstance(new ArrayList()) ? true : false;

    }

    private static Hashtable UnifyVar(Variable _variable, Object _obj, Hashtable sub) {

        if (CheckIfCanBeSub(_variable)) {
            return Unify(getSubstitution(_variable), _obj, sub);
        } else if (isVariable(_obj) && CheckIfCanBeSub((Variable) _obj)) {
            return Unify(_variable, getSubstitution((Variable) _obj), sub);
        } else {
            sub.put(_variable, _obj);
            return (Hashtable) sub;
        }
    }

    private static Expression getSubstitution(Variable v) {
        return (Expression) SubsitutionMap.get(v);

    }

    private static boolean CheckIfCanBeSub(Variable v) {
        return SubsitutionMap.containsKey(v) ? true : false;
    }

    public static boolean checkInequality(Action newaction) {
        boolean invalid_sub = false;
        for (Inequality i : newaction.GetInqualities()) {
            if ((SubsitutionMap.containsKey(i.x) && SubsitutionMap.containsKey(i.y))) {
                if (((SubsitutionMap.get(i.x)).equals(SubsitutionMap.get(i.y)))) {
                    SubsitutionMap.remove(i.x);
                    SubsitutionMap.remove(i.y);
                    invalid_sub = true;
                    break;
                }
            } else if (SubsitutionMap.containsKey(i.x)) {
                if (i.y instanceof Constant) {
                    if ((SubsitutionMap.get(i.x)).equals(((i.y)))) {
                        SubsitutionMap.remove(i.x);
                        invalid_sub = true;
                        break;
                    }
                }
            } else if (SubsitutionMap.containsKey(i.y)) {
                if (i.x instanceof Constant) {
                    if ((SubsitutionMap.get(i.y)).equals(((i.x)))) {
                        SubsitutionMap.remove(i.y);

                        invalid_sub = true;
                        break;
                    }
                }
            }
        }
        return invalid_sub;
    }

    private static void PrintState(SNLPState state) {
//        handleplan(state);
        System.out.println("Current state that is explored:");

        Map<Action, List<Atom>> openPre = state.getOpenPreCond();
        for (Action a : state.getActions()) {
            System.out.print(a.toString());
            System.out.print(" --> ");
            if (openPre.containsKey(a)) {
                List<Atom> openPreconds = openPre.get(a);
                for (Atom o : openPreconds) {
                    System.out.print(o.toString() + ",");
                }
            }
            System.out.println();
        }

        System.out.println("Causal Links");

        for (CausalLink c : state.getCausalLinks()) {
            System.out.println(c.toString());
        }
        System.out.println("**********************");
    }

   

    private static class OpenPreCondLengthComp implements Comparator<SNLPState> {

        public OpenPreCondLengthComp() {
        }

        public int compare(SNLPState l, SNLPState r) {
            if (l.GetOpenPreCondLength() < r.GetOpenPreCondLength()) {
                return -1;
            } else if (l.GetOpenPreCondLength() > r.GetOpenPreCondLength()) {
                return 1;
            }

//            if (l.getActions().size() < r.getActions().size()) {
//                return -1;
//            } else if (l.getActions().size() < r.getActions().size()) {
//                return 1;
//            }

//            if (l.getCausalLinks().size() > r.getCausalLinks().size()) {
//                return -1;
//            } else if (l.getCausalLinks().size() < r.getCausalLinks().size()) {
//                return 1;
//            }

            return 0;
        }
    }
}
