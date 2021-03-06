/**
 * AbsCon - Copyright (c) 2017, CRIL-CNRS - lecoutre@cril.fr
 * 
 * All rights reserved.
 * 
 * This program and the accompanying materials are made available under the terms of the CONTRAT DE LICENCE DE LOGICIEL LIBRE CeCILL which accompanies this
 * distribution, and is available at http://www.cecill.info
 */
package org.xcsp.modeler.problems;

import org.xcsp.common.IVar.VarSymbolic;
import org.xcsp.modeler.ProblemAPI;

public class Pic implements ProblemAPI {

	@Override
	public void model() {
		VarSymbolic x = var("x", dom("a", "b"));
		VarSymbolic y = var("y", dom("a", "b"));
		VarSymbolic z = var("z", dom("a", "b"));

		extension(vars(x, y), table("(a,a)(b,b)"));
		extension(vars(x, z), table("(a,a)(b,b)"));
		extension(vars(y, z), table("(a,b)(b,a)"));
	}
}