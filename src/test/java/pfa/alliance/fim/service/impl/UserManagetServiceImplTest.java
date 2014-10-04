/**
 * 
 */
package pfa.alliance.fim.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserStatus;

/**
 * This class is used for testing {@link UserManagetServiceImpl}.
 * 
 * @author Csaba
 */
public class UserManagetServiceImplTest
{
    private UserManagetServiceImpl userManagetServiceImpl;

    private UserRepository userRepositoryMock;

    @Before
    public void init()
    {
        userRepositoryMock = Mockito.mock( UserRepository.class );
        userManagetServiceImpl = new UserManagetServiceImpl( userRepositoryMock );
    }

    @Test
    public void test_registerUser_validData()
    {
        // prepare
        userRepositoryMock.save( Mockito.any( User.class ) );
        // call
        userManagetServiceImpl.registerUser( "email@test.com", "abc", "First", "Name" );
        // verify
        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( User.class ) );
    }

    @Test
    public void test_login_validData()
    {
        // prepare
        User user = new User();
        user.setId( 1 );
        user.setStatus( UserStatus.ACTIVE );
        Mockito.when( userRepositoryMock.findBy( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( user );
        // call
        User responseUser = userManagetServiceImpl.login( "user1", "password1" );
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findBy( Mockito.anyString(), Mockito.anyString() );
    }
}
