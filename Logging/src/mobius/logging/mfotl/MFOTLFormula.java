package mobius.logging.mfotl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

//TODO add specs and docs

/**
 * 
 */
public class MFOTLFormula {
    private String[] my_formula_parts;
    private Set<String> my_variable;
    private Set<String> my_bound_variable;
    
    public Formula my_formula;
    public Set<Formula> my_temporal_subformula;
    
    public Signature my_signature;
    public Structure my_structure;
    
    
    private final Logger my_logger = new Logger();
    
    public MFOTLFormula(final String a_formula) {
        // initialize Operator
        // Operator.init();
        my_logger.debug("InMethod: Formula()");

        if (ReservedSymbol.isOperator("U")) {
            my_logger.debug("ReservedSymbol Test OK");
        }
        
        /*
         * check lexer
         */
        lexer(a_formula);
        my_logger.info("Read Formula: " + a_formula + ". With Length " + my_formula_parts.length);
        /*
         * main formula
         */
        my_formula = new TemporalFormula(my_formula_parts);
        getBoundVariable();
        
        /*
         * get temporal subformula
         */
        my_temporal_subformula = new HashSet<Formula>();
        getTemporalSubformula(my_formula);
        
        /*
         * lassy way to get a structure
         */
        my_signature = new Signature();
        getSignature(my_formula);
        my_logger.info("");
        for (Predicator i: my_signature.my_predicate) {
            my_logger.info(i.toString());
        }
        
        /*
         * print info
         */
        my_logger.info("\nThe MFOTL formula: " + my_formula.toString());
        my_logger.info("\nThe MFOTL temporal sub formula: ");
        for (Formula i: my_temporal_subformula) {
            my_logger.info(i.toString());
        }
    }
        
    /**
     * 
     */
    public boolean evaluation(final Structure a_structure) {
        //@ assert false;
        assert false;
        // TODO bottom implementation
        return true;
    }
    
    private void getBoundVariable() {
        my_logger.debug("InMethod: get Bound Variable");
        my_bound_variable = new HashSet<String>();
        my_bound_variable = Collections.unmodifiableSet(((TemporalFormula)my_formula).my_bound_variable);        
    }
    
    /**
     * <p>
     * Get the temporal subformula
     * </p>
     */
    private void getTemporalSubformula(final Formula a_formula) {
        final Formula temp_formula = a_formula;
        
        if (temp_formula == null) {
            return;
        }
        
        if (temp_formula.my_is_temporal) {
            my_temporal_subformula.add(temp_formula);
        } else if (temp_formula instanceof TemporalFormula) {
                getTemporalSubformula(((TemporalFormula) temp_formula).my_left_subformula);
                getTemporalSubformula(((TemporalFormula) temp_formula).my_right_subformula);
        }
    }
    
    /**
     * <p>
     * Get the Signature
     * </p>
     */
    private void getSignature(final Formula a_formula) {
        final Formula temp_formula = a_formula;
        
        if (temp_formula == null) {
            return;
        }
        
        if (temp_formula instanceof AtomicFormula) {
            //my_variable.add(arg0);
            //my_signature.addConstant();
            my_signature.addPredicate(((AtomicFormula) temp_formula).my_predicator);
        } else {
            getSignature(((TemporalFormula) temp_formula).my_left_subformula);
            getSignature(((TemporalFormula) temp_formula).my_right_subformula);
        }
    }

	private void lexer(final String a_formula_str) {
	    String formula_str = a_formula_str;
        String formula_with_space = "";
        String temp_word = "";
        
        for (int i = 0; i < formula_str.length(); i++) {
            final String temp_str = Character.toString(formula_str.charAt(i));
            
            if (ReservedSymbol.isReserved(temp_word) || ReservedSymbol.isReserved(temp_str)) {
                /*
                final Pattern pattern = Pattern.compile("[^a-zA-Z]+[a-zA-Z0-9]* | [^0-9]+");
                if (!pattern.matcher(temp_word).find() && !ReservedSymbol.isReserved(temp_word)) {
                    my_logger.error("lexer error: " + temp_word);
                }
                */
                formula_with_space = formula_with_space.concat(temp_word).concat(" ");
                if (" ".equals(temp_str)) {
                    temp_word = "";
                } else {
                    temp_word = temp_str;
                }
            } else {
                temp_word = temp_word.concat(temp_str);
            }
        }
        formula_with_space = formula_with_space.concat(temp_word);
        
        
        while (formula_with_space.charAt(0) == ' ') {
            formula_with_space = formula_with_space.substring(1);
            
            if (formula_with_space.length() == 0) {
                my_logger.error("EMPTY FORMULA!!!");
            }
        }
        
        formula_str = formula_with_space.replaceAll("[ ]+", " ");
        my_logger.info("Formula with Space: " + formula_str);
        my_formula_parts = formula_str.split(" ");
	}
	
	/*
	void removeSyntacticsugar(String[] _part1, String[] _part2, String _op) {
	    if (_op == Operator.op.get(_op))
	        ;
	    else if (_op == Operator.op.get(_op))
	        ;
	    else
	        ;
	}
	*/
}