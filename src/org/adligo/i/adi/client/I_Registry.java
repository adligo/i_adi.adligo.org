package org.adligo.i.adi.client;

import java.util.Vector;;

/**
 * this is the begining of the org.adligo 2.0
 * it is intended as
 * a end all be all wrapper for 
 * stateless threadsafe code
 * 
 * mostly this is storage code (could be Hibernate, Jdbc DAOs,
 * SearchEngine stuff (Lucene), FileSystem stuff , 
 * stuff cached in RAM from some of the previous sources exc..)
 * Of course one of the goals is to be able to swap things so
 * if you wrote Jdbc and then decide you like Hibernate for 
 * something you can swap it.  Or if you wrote something in Hibernate
 * and then decide it needs to move to a Ldap server, and then 
 * change your mind again and decide it needs to stay in RAM.
 *
 * This idea sort of evolved from the 
 * i_persistence interfaces which were a inital attempt at doing 
 * this sort of thing.  However they got somewhat verbose and were to
 * specific to databases.
 * 
 * This set of interfaces are much more adaptable,
 * for instance if you have a I_Invoker
 * for "getPersons"  you may start with 
 * accepting I_TemplateParams to provide the user
 * with a large taximony landsacpe for all sorts of 
 * extendable advanced search screens.
 * 
 * Later on you may think, hey what about 
 * per compiling my queries and doing a lookup of single
 * object on the primary key.  So with this api
 * you can use Polymorphism
 * to pass in a Integer for the precompiled query 
 * or a I_TemplateParams object for a TreeFinder style
 * Query.  exc....
 * 
 * @author scott
 *
 */
public interface I_Registry {
	/**
	 * dynamic locator method to discover your implementations
	 * at run time
	 * 
	 * @param name 
	 * Each usage of this sort of lookup 
	 * will very, and a naming convention should be established
	 * but generally the goal is to keep things simple
	 * so in the adligo code names will look like
	 * 
	 * getPersons
	 * savePersons
	 * findSocks
	 * openJar 
	 * exc...
	 * 
	 * @return
	 */
	public I_CheckedInvoker getCheckedInvoker(String name);
	/**
	 * @see I_Registry#getCheckedInvoker(String)
	 */
	public I_Invoker getInvoker(String name);
	/**
	 * returns true if all of the Invokers and CheckedInvokers 
	 * were found.  This method can be used to provide a way for 
	 * the code to assert if everything is runing properly.
	 * If not fail fast as a Transaction would.
	 * @return
	 */
	public boolean servedAll();
	/**
	 * returns true if all of the Invokers 
	 * were found.  This method can be used to provide a way for 
	 * the code to assert if everything is runing properly.
	 * If not fail fast as a Transaction would.
	 * @return
	 */
	public boolean servedAllInvokers();
	/**
	 * returns true if all of the CheckedInvokers 
	 * were found.  This method can be used to provide a way for 
	 * the code to assert if everything is runing properly.
	 * If not fail fast as a Transaction would.
	 * @return
	 */
	public boolean servedAllCheckedInvokers();
	/**
	 * returns a Vector of names <Strings> of the Invokers 
	 * that weren't found to make using the api easier
	 * by providing a concise list of missing names 
	 * 
	 * Couldn't use Set <String> due to CLDC 2.0
	 * @return
	 */
	public Vector getMissingInvokers();
	/**
	 * returns a Vector of names <Strings> of the CheckedInvokers 
	 * that weren't found to make using the api easier
	 * by providing a concise list of missing names
	 * 
	 * Couldn't use Set <String> due to CLDC 2.0
	 * @return
	 */
	public Vector getMissingCheckedInvokers();

}
