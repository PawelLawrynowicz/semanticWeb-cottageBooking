/**
 * This software is copyrighted and licensed; see the accompanying license file for copyright holders and terms.
 */
package info.sswap.impl.empire.model;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import info.sswap.api.model.DataAccessException;
import info.sswap.api.model.PDG;
import info.sswap.api.model.RDG;
import info.sswap.api.model.SSWAPProvider;
import info.sswap.api.model.SSWAPResource;


/**
 * Implementation of PDG. This abstract class has a few abstract methods with Empire annotations, and the implementation
 * of these abstract methods will be automatically generated by Empire.
 * 
 * @author Blazej Bulka <blazej@clarkparsia.com>
 */
public abstract class PDGImpl extends SourceModelImpl implements PDG {

	private ProviderImpl provider;

	/**
	 * @inheritDoc
	 */
	public SSWAPProvider getProvider() {
		if (provider == null) {
			provider = getDependentObject(ProviderImpl.class);
		}
		
		return provider; 
	}
	
	/**
	 * @inheritDoc
	 */
	public SSWAPProvider createProvider(URI providerURI) {
		provider = ImplFactory.get().createDependentObject(this, providerURI, ProviderImpl.class);

		return provider;
	}
	
	/**
	 * @inheritDoc
	 */
	public Collection<RDG> getRDGs() throws DataAccessException {
		List<RDG> result = new LinkedList<RDG>();
		
		SSWAPProvider provider = getProvider();
		
		if (provider != null) {
			for (SSWAPResource resource : provider.getProvidesResources()) {
				result.add(resource.getRDG());
			}
		}
		
		return result;
	}
}