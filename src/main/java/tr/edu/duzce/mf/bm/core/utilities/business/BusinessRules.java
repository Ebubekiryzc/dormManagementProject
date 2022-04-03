package tr.edu.duzce.mf.bm.core.utilities.business;

import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.core.utilities.results.SuccessResult;

public class BusinessRules {
    public static Result check(Result... rules) {
        for (Result rule : rules) {
            if (!rule.isSuccess()) {
                return rule;
            }
        }
        return new SuccessResult();
    }
}