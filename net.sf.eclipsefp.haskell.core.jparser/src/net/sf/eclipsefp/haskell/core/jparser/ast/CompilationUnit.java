package net.sf.eclipsefp.haskell.core.jparser.ast;

import org.eclipse.core.resources.IFile;

import de.leiffrenzel.fp.haskell.core.halamo.ICompilationUnit;
import de.leiffrenzel.fp.haskell.core.halamo.IHaskellLanguageElement;
import de.leiffrenzel.fp.haskell.core.halamo.IModule;
import de.leiffrenzel.fp.haskell.core.halamo.ISourceLocation;

public class CompilationUnit implements ICompilationUnit {

	private IModule fModule;
	private IFile fFile;

	public CompilationUnit(IModule module) {
		fModule = module;
	}

	public IModule[] getModules() {
		return new IModule[] { fModule };
	}

	public ISourceLocation getNextLocation(final ISourceLocation srcLoc) {
		//special case for empty files
		if (isEmptyUntitledModule(fModule)
		 && 0 >= srcLoc.getLine()
		 && 0 >= srcLoc.getColumn())
		{
			return new SourceLocation(1, 0);
		}
		
		ISourceLocation result = searchNextLocation(new IModule[] {fModule},
				srcLoc);
		if (result == null) {
			searchNextLocation(fModule.getExportSpecifications(), srcLoc);
		}
		if (result == null) {
			result = searchNextLocation(fModule.getImports(), srcLoc);
		}
		if (result == null) {
			result = searchNextLocation(fModule.getDeclarations(), srcLoc);
		}
		return result;
	}
	
	private <T extends IHaskellLanguageElement> ISourceLocation
		searchNextLocation(T[] elems, final ISourceLocation srcLoc)
	{
		for (T elem : elems) {
			final ISourceLocation expLoc = elem.getSourceLocation();
			if (expLoc.isAfter(srcLoc)) {
				return expLoc;
			}
		}
		return null;
	}

	private boolean isEmptyUntitledModule(IModule module) {
		return "".equals(module.getName())
		    && 0 == module.getDeclarations().length
		    && 0 == module.getExportSpecifications().length
		    && 0 == module.getImports().length;
	}

	public Object getAdapter(Class adapter) {
		// TODO relax the need for implementing the IAdaptable interface
		// or provide a real implementation for this method
		return null;
	}
	
	public IFile getUnderlyingResource() {
		return fFile;
	}

	public void setUnderlyingResource(IFile file) {
		fFile = file;
	}
	  
}
