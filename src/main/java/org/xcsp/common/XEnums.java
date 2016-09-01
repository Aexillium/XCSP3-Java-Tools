/*
 * Copyright (c) 2016 XCSP3 Team (contact@xcsp.org)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.xcsp.common;

import java.util.stream.Stream;

/**
 * @author Christophe Lecoutre
 */
public class XEnums {

	public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
		try {
			return Enum.valueOf(enumType, name.toUpperCase()); // just for upper-case
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/** The enum type describing the different types of frameworks. */
	public static enum TypeFramework {
		CSP,
		MAXCSP,
		COP,
		WCSP,
		FCSP,
		QCSP,
		QCSP_PLUS,
		QCOP,
		QCOP_PLUS,
		SCSP,
		SCOP,
		QSTR,
		TCSP,
		NCSP,
		NCOP,
		DisCSP,
		DisWCSP;
	}

	/**
	 * The enum type describing the different types of constraints and meta-constraints. We use lower-case letters, so as to directly get the names of the
	 * elements (no need to define constants or make any transformations).
	 */
	public static enum TypeCtr {
		extension,
		intension,
		regular,
		grammar,
		mdd,
		allDifferent,
		allEqual,
		allDistant,
		ordered,
		lex,
		allIncomparable,
		sum,
		count,
		nValues,
		cardinality,
		balance,
		spread,
		deviation,
		sumCosts,
		stretch,
		noOverlap,
		cumulative,
		binPacking,
		knapsack,
		networkFlow,
		circuit,
		nCircuits,
		path,
		nPaths,
		tree,
		nTrees,
		arbo,
		nArbos,
		nCliques,
		clause,
		instantiation,
		allIntersecting,
		range,
		roots,
		partition,
		minimum,
		maximum,
		element,
		channel,
		permutation,
		precedence,
		and,
		or,
		not,
		iff,
		ifThen,
		ifThenElse,
		slide,
		seqbin,
		smart; // future constraint to be taken into account

		/** Returns true if the element has a sliding nature. */
		public boolean isSliding() {
			return this == slide || this == seqbin;
		}

		/** Returns true if the element has a if-based control structure. */
		public boolean isControl() {
			return this == ifThen || this == ifThenElse;
		}

		/** Returns true if the element has a logical nature. */
		public boolean isLogical() {
			return this == and || this == or || this == not || this == iff;
		}

		/** Returns true if the element corresponds to a meta-constraint. */
		public boolean isMeta() {
			return isSliding() || isLogical() || isControl();
		}
	}

	/**
	 * The enum type describing the different types of child elements of constraints. We use lower-case letters, so as to directly get the names of the elements
	 * (except for FINAL that needs to be managed apart, because this is a keyword).
	 */
	public static enum TypeChild {
		list,
		set,
		mset,
		matrix,
		function,
		supports,
		conflicts,
		except,
		value,
		values,
		total,
		coeffs,
		condition,
		cost,
		operator,
		number,
		transitions,
		start,
		FINAL, // upper-cased because a keyword
		terminal,
		rules,
		index,
		mapping,
		occurs,
		rowOccurs,
		colOccurs,
		widths,
		patterns,
		origins,
		lengths,
		ends,
		heights,
		machines,
		conditions,
		sizes,
		weights,
		profits,
		limit,
		size,
		root,
		image,
		graph,
		row;
	}

	/**
	 * The enum type describing the different types of attributes that may be encountered. We use lower-case letters, so as to directly get the names of the
	 * elements (except for CLASS, FOR and CASE that need to be managed apart, because they correspond to keywords).
	 */
	public static enum TypeAtt {
		format,
		type,
		id,
		CLASS, // upper-cased because a keyword
		note,
		as,
		size,
		violationMeasure,
		violationParameters,
		defaultCost,
		violationCost,
		cost,
		reifiedBy,
		hreifiedFrom,
		hreifiedTo,
		closed,
		FOR, // upper-cased because a keyword
		restriction,
		rank,
		startIndex,
		zeroIgnored,
		CASE, // upper-cased because a keyword
		order,
		circular,
		offset,
		collect,
		violable,
		lb,
		ub,
		combination;
		// unclean, // used for tuples of table constraints
		// starred; // used for tuples of table constraints

		/** Returns true iff the element has a (full or half) reification nature. */
		public boolean isReifying() {
			return this == reifiedBy || this == hreifiedFrom || this == hreifiedTo;
		}

		/** Returns the constant that corresponds to the specified string (we need this method to manage the special constants FOR and CASE). */
		public static TypeAtt valOf(String s) {
			return s.equals("class") ? CLASS : s.equals("for") ? FOR : s.equals("case") ? TypeAtt.CASE : valueOf(s);
		}
	}

	/** The enum type describing the different flags that may be associated with some elements (e.g., constraints). */
	public static enum TypeFlag {
		STARRED_TUPLES,
		UNCLEAN_TUPLES;
	}

	/** The enum type describing the different types of reification. */
	public static enum TypeReification {
		FULL,
		HALF_FROM,
		HALF_TO;
	}

	/** The enum type describing the different types of operators that can be used in conditions. */
	public static enum TypeConditionOperator {
		LT,
		LE,
		GE,
		GT,
		NE,
		EQ,
		IN,
		NOTIN;

		/** Returns true iff the constant corresponds to a set operator. */
		public boolean isSet() {
			return this == IN || this == NOTIN;
		}

		public boolean isValidFor(int v1, int v2) {
			assert !isSet();
			return this == LT ? v1 < v2 : this == LE ? v1 <= v2 : this == GE ? v1 >= v2 : this == GT ? v1 > v2 : this == NE ? v1 != v2 : v1 == v2;
		}

		public boolean isValidFor(int v, int min, int max) {
			assert isSet();
			return this == IN ? min <= v && v <= max : v < min || v > max;
		}
	}

	/** The enum type describing the different types of classical relational operators that can be used in conditions. */
	public static enum TypeConditionOperatorRel {
		LT,
		LE,
		GE,
		GT,
		NE,
		EQ;

		public TypeConditionOperatorRel reverseForSwap() {
			return this == LT ? GT : this == LE ? GE : this == GE ? LE : this == GT ? LT : this; // no change for NE and EQ
		}
	}

	/** The enum type describing the different types of operators that can be used in conditions. */
	public static enum TypeConditionOperatorSet {
		IN,
		NOTIN;
	}

	/** The enum type describing the different types of operators that can be used in elements <operator>. */
	public static enum TypeOperator {
		LT,
		LE,
		GE,
		GT,
		SUBSET,
		SUBSEQ,
		SUPSEQ,
		SUPSET;

		public static TypeOperator valOf(String s) {
			return TypeOperator.valueOf(s.trim().toUpperCase());
		}

		/** Returns true iff the constant corresponds to a set operator. */
		public boolean isSet() {
			return this == SUBSET || this == SUBSEQ || this == SUPSEQ || this == SUPSET;
		}

		public boolean isValidFor(int v1, int v2) {
			assert !isSet();
			return this == LT ? v1 < v2 : this == LE ? v1 <= v2 : this == GE ? v1 >= v2 : v1 > v2;
		}
	}

	/** The enum type describing the different types of operators that can be used in elements <operator>. */
	public static enum TypeArithmeticOperator {
		ADD,
		SUB,
		MUL,
		DIV,
		MOD,
		DIST;
	}

	/** The enum type describing the different types of nodes that can be found in syntactic trees (built for intensional expressions). */
	public static enum TypeExpr {
		NEG(1),
		ABS(1),
		ADD(2, Integer.MAX_VALUE),
		SUB(2),
		MUL(2, Integer.MAX_VALUE),
		DIV(2),
		MOD(2),
		SQR(1),
		POW(2),
		MIN(2, Integer.MAX_VALUE),
		MAX(2, Integer.MAX_VALUE),
		DIST(2),
		LT(2),
		LE(2),
		GE(2),
		GT(2),
		NE(2),
		EQ(2, Integer.MAX_VALUE),
		SET(0, Integer.MAX_VALUE),
		IN(2),
		NOT(1),
		AND(2, Integer.MAX_VALUE),
		OR(2, Integer.MAX_VALUE),
		XOR(2, Integer.MAX_VALUE),
		IFF(2, Integer.MAX_VALUE),
		IMP(2),
		IF(3),
		CARD(1),
		UNION(2, Integer.MAX_VALUE),
		INTER(2, Integer.MAX_VALUE),
		DIFF(2),
		SDIFF(2, Integer.MAX_VALUE),
		HULL(1),
		DJOINT(2),
		SUBSET(2),
		SUBSEQ(2),
		SUPSET(2),
		SUPSEQ(2),
		CONVEX(1),
		FDIV(2),
		FMOD(2),
		SQRT(1),
		NROOT(2),
		EXP(1),
		LN(1),
		LOG(2),
		SIN(1),
		COS(1),
		TAN(1),
		ASIN(1),
		ACOS(1),
		ATAN(1),
		SINH(1),
		COSH(1),
		TANH(1),
		LONG(0),
		RATIONAL(0),
		DECIMAL(0),
		VAR(0),
		PAR(0),
		SYMBOL(0);

		/** The name of the constant in lower-case. */
		public final String lcname;

		/** The minimal and maximal arity (number of sons) of the node. */
		public final int arityMin, arityMax;

		/** Builds a constant, while specifying its minimal and maximal arity (number of sons). */
		TypeExpr(int arityMin, int arityMax) {
			this.arityMin = arityMin;
			this.arityMax = arityMax;
			this.lcname = name().toLowerCase();
		}

		/** Builds a constant, while specifying its arity (number of sons). */
		TypeExpr(int arity) {
			this(arity, arity);
		}

		/**
		 * Returns the postfix expression of the operator when considering the specified operands. When the number of operands does not correspond to the usual
		 * arity, the name of the operator is concatenated to this number, so as to be able to make evaluations later using a stack. A whitespace is
		 * systematically put as last character: this is necessary for combining expressions
		 */
		public String postfixExpressionFor(Object... operands) {
			XUtility.control(arityMin <= operands.length && operands.length <= arityMax, "Bad number of operands");
			if (this == SET)
				return (operands.length > 0 ? XUtility.join(operands) + " " : "") + operands.length + lcname + " ";
			XUtility.control(arityMin != 0, "Forbidden to build a postfix expression with 0-ary operators (except for the empty set");
			return XUtility.join(operands) + " " + (operands.length == arityMin ? "" : operands.length) + lcname + " ";
		}
	}

	/** The enum type describing the different types of measures used by elements <cost>. */
	public static enum TypeMeasure {
		VAR,
		DEC,
		VAL,
		EDIT;
	}

	/** The enum type describing the different types of objectives. */
	public static enum TypeObjective {
		EXPRESSION,
		SUM,
		PRODUCT,
		MINIMUM,
		MAXIMUM,
		NVALUES,
		LEX;
	}

	/** The enum type describing the different types of combination of objectives. */
	public static enum TypeCombination {
		LEXICO,
		PARETO;
	}

	/** The enum type describing the different types of ranking used by constraints <maximum>, <minimum>, <element>. */
	public static enum TypeRank {
		FIRST,
		LAST,
		ANY;
	}

	/** The interface that denotes a class that can be associated with an XCSP3 element */
	public interface TypeClass {
		public String name();

		/** Transforms String objects into TypeClass objects. */
		public static TypeClass[] classesFor(String... classes) {
			return Stream
					.of(classes)
					.map(s -> Stream.of(StandardClass.values()).map(c -> (TypeClass) c).filter(c -> c.name().equals(s)).findFirst().orElse(new SpecialClass(s)))
					.toArray(TypeClass[]::new);
		}

		/** Determines if the two specified arrays of TypeClass objects are disjoint or not. */
		public static boolean disjoint(TypeClass[] t1, TypeClass[] t2) {
			if (t1 == null || t2 == null)
				return true;
			for (TypeClass c1 : t1)
				for (TypeClass c2 : t2)
					if (c1.name().equals(c2.name()))
						return false;
			return true;
		}
	}

	/** The enum type describing the different standard classes that can be associated with XCSP3 elements. */
	public static enum StandardClass implements TypeClass {
		channeling,
		clues,
		rows,
		columns,
		blocks,
		diagonals,
		symmetryBreaking,
		redundantConstraints,
		nogoods;
	}

	/** The class that allows the user to define his own classes */
	public static class SpecialClass implements TypeClass {
		private String name;

		public SpecialClass(String name) {
			this.name = name;
		}

		public String name() {
			return name;
		}
	}
}
