package edu.umich.verdict.relation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.umich.verdict.VerdictContext;
import edu.umich.verdict.datatypes.TableUniqueName;
import edu.umich.verdict.relation.condition.CompCond;
import edu.umich.verdict.relation.condition.Cond;
import edu.umich.verdict.relation.condition.CondModifier;
import edu.umich.verdict.relation.expr.ColNameExpr;
import edu.umich.verdict.relation.expr.Expr;
import edu.umich.verdict.relation.expr.ExprModifier;
import edu.umich.verdict.relation.expr.FuncExpr;

public class ApproxFilteredRelation extends ApproxRelation {
	
	private ApproxRelation source;
	
	private Cond cond;

	public ApproxFilteredRelation(VerdictContext vc, ApproxRelation source, Cond cond) {
		super(vc);
		this.source = source;
		this.cond = cond;
	}

	public ApproxRelation getSource() {
		return source;
	}
	
	public Cond getFilter() {
		return cond;
	}

	@Override
	public ExactRelation rewrite() {
		ExactRelation r = new FilteredRelation(vc, source.rewrite(), condWithApprox(cond, tableSubstitution()));
		r.setAliasName(getAliasName());
		return r;
	}
	
	@Override
	protected double samplingProbabilityFor(FuncExpr f) {
		return source.samplingProbabilityFor(f);
	}

	@Override
	protected Map<String, String> tableSubstitution() {
		return source.tableSubstitution();
	}

	@Override
	protected String sampleType() {
		return source.sampleType();
	}
	
	@Override
	protected List<TableUniqueName> accumulateStratifiedSamples() {
		return source.accumulateStratifiedSamples();
	}

	@Override
	protected List<String> sampleColumns() {
		return source.sampleColumns();
	}

}