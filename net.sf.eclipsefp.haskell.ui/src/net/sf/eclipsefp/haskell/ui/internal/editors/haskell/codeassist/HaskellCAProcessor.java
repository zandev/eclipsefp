// Copyright (c) 2003-2008 by Leif Frenzel - see http://leiffrenzel.de
// This code is made available under the terms of the Eclipse Public License,
// version 1.0 (EPL). See http://www.eclipse.org/legal/epl-v10.html
package net.sf.eclipsefp.haskell.ui.internal.editors.haskell.codeassist;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

/**
 * <p>
 * computes the code assist completion proposals and context information.
 * </p>
 *
 * @author Leif Frenzel
 */
public class HaskellCAProcessor implements IContentAssistProcessor {

	public static class NullCompletionContext extends HaskellCompletionContext {

	  static private class NullCompletionContextHolder {
	    private static final NullCompletionContext theInstance = new NullCompletionContext();
	  }

		public static final NullCompletionContext getInstance() {
			return NullCompletionContextHolder.theInstance;
		}

		@Override
		public ICompletionProposal[] computeProposals() {
			return new ICompletionProposal[0];
		}

	}

	private static class WorkbenchContextFactory implements
			ICompletionContextFactory {

		public IHaskellCompletionContext createContext(final ITextViewer viewer,
													  final int offset)
		{
		//	try {
				return new WorkbenchHaskellCompletionContext(viewer, offset);
//			} catch (CoreException ex) {
//				HaskellCorePlugin.log("Error when parsing the viewer "+
//						"contents. Aborting code assistance.", ex);
//				return NullCompletionContext.getInstance();
//			}
		}

	}

	private final ICompletionContextFactory fContextFactory;

	public HaskellCAProcessor() {
		this(new WorkbenchContextFactory());
	}

	public HaskellCAProcessor(final ICompletionContextFactory factory) {
		fContextFactory = factory;
	}

	// interface methods of IContentAssistProcessor
	// /////////////////////////////////////////////

	public ICompletionProposal[] computeCompletionProposals(
			final ITextViewer viewer, final int offset)
	{
		IHaskellCompletionContext context = fContextFactory.createContext(viewer, offset);
		return context.computeProposals();
	}

	public IContextInformation[] computeContextInformation(
			final ITextViewer viewer, final int documentOffset) {
		// unused
		return null;
	}

	public char[] getCompletionProposalAutoActivationCharacters() {
	  // unused
		return null;
	}

	public char[] getContextInformationAutoActivationCharacters() {
	  // unused
		return null;
	}

	public String getErrorMessage() {
		// return null to indicate we had no problems
		return null;
	}

	public IContextInformationValidator getContextInformationValidator() {
	  // unused
		return null;
	}

}