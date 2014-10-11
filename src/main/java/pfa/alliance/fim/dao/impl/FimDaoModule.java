/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import pfa.alliance.fim.dao.UserRepository;

import com.google.inject.AbstractModule;

/**
 * Module containing the DAO module.
 * 
 * @author Csaba
 */
public class FimDaoModule
    extends AbstractModule
{

    @Override
    protected void configure()
    {
        bind( UserRepository.class ).to( UserRepositoryImpl.class );
    }

}
