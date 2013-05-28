package demtech.mfotl;

/**
 * Parent class for MFOTLFormula, AtomicFormula and TemporalFormula
 * @author jianw
 *
 */
public class Formula {
    // Attributes
    protected boolean my_is_firstorder = true;
    protected boolean my_is_temporal = false;
    final private Logger my_logger = new Logger();
    
    // Public Methods
    /**
     * 
     * @param the_structure
     * @return
     */
    protected Evaluation evaluate(final Structure the_structure) {
        my_logger.debug("InMethod: Formula.evaluate");
        return null;
    }
    
    public boolean isFirstorder() {
        return this.my_is_firstorder;
    }
    
    public boolean isTemporal() {
        return this.my_is_temporal;
    }
}