// Copyright (c) 2006 by Leif Frenzel <himself@leiffrenzel.de>
// All rights reserved.
package net.sf.eclipsefp.haskell.core.cabalmodel;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.sf.eclipsefp.haskell.scion.types.Component;
import net.sf.eclipsefp.haskell.scion.types.Component.ComponentType;

/** <p>The root of the package description model, represents the contents of a
  * <code>.cabal</code> file.</p>
  *
  * @author Leif Frenzel
  */
public class PackageDescription {

  private final List<PackageDescriptionStanza> stanzas;

  PackageDescription() {
    stanzas = new ArrayList<PackageDescriptionStanza>();
  }

  public PackageDescription(final String name){
    this();
    PackageDescriptionStanza pds = new PackagePropertiesStanza( 0 );
    stanzas.add( pds );
    pds.update( CabalSyntax.FIELD_NAME, name );
  }

  public List<PackageDescriptionStanza> getStanzas() {
    return stanzas;
  }

  public PackageDescriptionStanza addStanza(final CabalSyntax type,final String name){
    int startLine=stanzas.get(stanzas.size()-1).getEndLine()+1;
    PackageDescriptionStanza pds=new PackageDescriptionStanza( type, name, startLine );
    pds.setEndLine( startLine+1 );
    stanzas.add( pds );
    return pds;
  }

  public Map<String, List<PackageDescriptionStanza>> getStanzasBySourceDir(){
    Map<String, List<PackageDescriptionStanza>> ret=new HashMap<String, List<PackageDescriptionStanza>>();

    for (PackageDescriptionStanza pds:stanzas){
      Collection<String> sds=pds.getSourceDirs();
      for (String t:sds){
        if (t.length()>0){
          List<PackageDescriptionStanza> pdss=ret.get( t );
          if (pdss==null){
            pdss=new LinkedList<PackageDescriptionStanza>();
            ret.put( t, pdss );
          }
          pdss.add(pds);
        }
      }

    }

    return ret;
  }

  public void dump(final Writer w) throws IOException {
    for (PackageDescriptionStanza pds:stanzas){
      pds.dump( w, 0 );
    }
  }

  public String dump(){
    StringWriter sw=new StringWriter();
    try {
      dump( sw );
      String s=sw.toString();
      return s;
    } catch (IOException ioe){
      // cannot happen
    }
    return null;
  }

  public PackageDescriptionStanza getComponentStanza(final Component c){
    for (PackageDescriptionStanza pds:stanzas){
      if (CabalSyntax.SECTION_LIBRARY.equals( pds.getType()) && c.getType().equals( ComponentType.LIBRARY )){
        return pds;
      }
      if (CabalSyntax.SECTION_EXECUTABLE.equals(pds.getType()) && c.getType().equals( ComponentType.EXECUTABLE ) && pds.getName().equals(c.getName())){
        return pds;
      }
    }
    return null;
  }

  public PackageDescriptionStanza getSameStanza(final PackageDescriptionStanza p){
    for (PackageDescriptionStanza pds:stanzas){
      if (pds.getType()==null && p.getType()==null){
        return pds;
      }
      if (CabalSyntax.SECTION_LIBRARY.equals( pds.getType()) && CabalSyntax.SECTION_LIBRARY.equals( p.getType())){
        return pds;
      }
      if (CabalSyntax.SECTION_EXECUTABLE.equals(pds.getType()) && CabalSyntax.SECTION_EXECUTABLE.equals(p.getType() ) && pds.getName().equals(p.getName())){
        return pds;
      }
    }
    return null;
  }
}
