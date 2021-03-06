// Copyright (c) 2007-2008 by Leif Frenzel - see http://leiffrenzel.de
// This code is made available under the terms of the Eclipse Public License,
// version 1.0 (EPL). See http://www.eclipse.org/legal/epl-v10.html
package net.sf.eclipsefp.haskell.ui.internal.refactoring;

import net.sf.eclipsefp.haskell.ui.internal.util.UITexts;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.IConditionChecker;
import org.eclipse.ltk.core.refactoring.participants.ValidateEditChecker;

/** <p>delegate object that contains the logic used by the processor.</p>
  *
  * @author Leif Frenzel
  */
public class RenameDelegate extends RefDelegate {

  private Change change;

  public RenameDelegate( final RefInfo info ) {
    super( info );
    info.setAllowEmptySelection( true );
  }

  @Override
  RefactoringStatus checkFinalConditions( final IProgressMonitor pm,
                                          final CheckConditionsContext ctxt ) {
    RefactoringStatus result = new RefactoringStatus();
    try {
      pm.beginTask( UITexts.refDelegate_checking, 100 );
      if( ctxt != null ) {
        IConditionChecker checker = ctxt.getChecker( ValidateEditChecker.class );
        ValidateEditChecker editChecker = ( ValidateEditChecker )checker;
        editChecker.addFile( info.getSourceFile() );
      }
      change = createRenameChange();
      if( change == null ) {
        result.addFatalError( UITexts.mkPointFreeDelegate_notApplicable );
      }
    } finally {
      pm.done();
    }
    return result;
  }

  @Override
  void createChange( final IProgressMonitor pm,
                     final CompositeChange rootChange ) {
    try {
      pm.beginTask( UITexts.mkPointFreeDelegate_collectingChanges, 100 );
      if( change == null ) {
        throw new IllegalStateException();
      }
      rootChange.add( change );
    } finally {
      pm.done();
    }
  }


  // helping methods
  //////////////////

  private Change createRenameChange() {
    TextFileChange result = null;
    // TODO TtC replace by something not Cohatoe-based
    /*
    CohatoeServer server = CohatoeServer.getInstance();
    IRename fun = server.createFunction( IRename.class );
    if( fun != null ) {
      String newName = "I-AM-THE-NEW-NAME";
      int line = info.getLine();
      int column = info.getColumn();
      List<IReplaceEditDesc> descs
        = fun.performRename( info.getSourceFile(), line, column, newName );
      Map<IFile, List<IReplaceEditDesc>> map = mapByFile( descs );
      for( IFile file: map.keySet() ) {
        result = new TextFileChange( file.getName(), file );
        // a file change contains a tree of edits, first add the root of them
        MultiTextEdit fileChangeRootEdit = new MultiTextEdit();
        result.setEdit( fileChangeRootEdit );
        // edit object for the text replacement in the file,
        // this is the only child
        List<IReplaceEditDesc> editDescs = map.get( file );
        for( IReplaceEditDesc editDesc: editDescs ) {
          ReplaceEdit edit = new ReplaceEdit( editDesc.getOffset(),
                                              editDesc.getLength(),
                                              editDesc.getReplacement() );
          fileChangeRootEdit.addChild( edit );
        }
      }
    }
    */
    return result;
  }

  /*
  private Map<IFile, List<IReplaceEditDesc>> mapByFile(
      final List<IReplaceEditDesc> editDescs ) {
    Map<IFile, List<IReplaceEditDesc>> result
      = new HashMap<IFile, List<IReplaceEditDesc>>();
    for( IReplaceEditDesc editDesc: editDescs ) {
      List<IReplaceEditDesc> list = result.get( editDesc.getFile() );
      if( list == null ) {
        list = new ArrayList<IReplaceEditDesc>();
        result.put( editDesc.getFile(), list );
      }
      list.add( editDesc );
    }
    return result;
  }
  */
}
