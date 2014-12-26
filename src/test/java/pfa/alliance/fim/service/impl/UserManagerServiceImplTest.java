/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pfa.alliance.fim.dao.UserOneTimeLinkRepository;
import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserOneTimeLink;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.EmailGeneratorService;
import pfa.alliance.fim.service.EmailService;
import pfa.alliance.fim.service.EmailType;
import pfa.alliance.fim.service.FimUrlGeneratorService;

/**
 * This class is used for testing {@link UserManagerServiceImpl}.
 * 
 * @author Csaba
 */
public class UserManagerServiceImplTest
{
    private UserManagerServiceImpl userManagetServiceImpl;

    private UserRepository userRepositoryMock;

    private UserOneTimeLinkRepository userOneTimeLinkRepositoryMock;

    private EmailService emailServiceMock;

    private EmailGeneratorService emailGeneratorServiceMock;

    private FimUrlGeneratorService fimUrlGeneratorServiceMock;

    @Before
    public void init()
    {
        userRepositoryMock = Mockito.mock( UserRepository.class );
        emailServiceMock = Mockito.mock( EmailService.class );
        emailGeneratorServiceMock = Mockito.mock( EmailGeneratorService.class );
        fimUrlGeneratorServiceMock = Mockito.mock( FimUrlGeneratorService.class );
        userOneTimeLinkRepositoryMock = Mockito.mock( UserOneTimeLinkRepository.class );
        userManagetServiceImpl =
            new UserManagerServiceImpl( userRepositoryMock, emailServiceMock, emailGeneratorServiceMock,
                                        fimUrlGeneratorServiceMock, userOneTimeLinkRepositoryMock);
    }

    @Test
    public void test_registerUser_validData()
        throws Exception
    {
        // prepare
        userRepositoryMock.save( Mockito.any( User.class ) );
        Mockito.when( emailGeneratorServiceMock.getSubject( EmailType.REGISTER_USER, null, Locale.UK ) ).thenReturn( "Subject" );
        Mockito.when( emailGeneratorServiceMock.getContent( Mockito.any( EmailType.class ), Mockito.any( Map.class ),
                                                            Mockito.any( Locale.class ) ) ).thenReturn( "Content" );
        Mockito.when( fimUrlGeneratorServiceMock.getOneTimeLinkLink( Mockito.any( UserOneTimeLink.class ) ) ).thenReturn( "url" );
        emailServiceMock.sendEmail( "email@test.com", "Subject", "Content" );
        // call
        userManagetServiceImpl.registerUser( "email@test.com", "abc", "First", "Name", Locale.UK );
        // verify
        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( User.class ) );
        Mockito.verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getSubject( EmailType.REGISTER_USER, null,
                                                                                       Locale.UK );
        Mockito.verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getContent( Mockito.any( EmailType.class ),
                                                                                       Mockito.any( Map.class ),
                                                                                       Mockito.any( Locale.class ) );
        Mockito.verify( emailServiceMock, Mockito.atLeastOnce() ).sendEmail( "email@test.com", "Subject", "Content" );
        Mockito.verify( fimUrlGeneratorServiceMock, Mockito.atLeastOnce() ).getOneTimeLinkLink( Mockito.any( UserOneTimeLink.class ) );
    }

    @Test( expected = DuplicateDataException.class )
    public void test_registerUser_duplicatedData()
    {
        // prepare
        Mockito.when( userRepositoryMock.save( Mockito.any( User.class ) ) ).thenThrow( new PersistenceException(
                                                                                                                  "These data violates unique constraint" ) );
        // call
        try
        {
            userManagetServiceImpl.registerUser( "email@test.com", "abc", "First", "Name", Locale.UK );
        }
        finally
        {
            // verify
            Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( User.class ) );
            Mockito.verifyZeroInteractions( emailGeneratorServiceMock, emailServiceMock, fimUrlGeneratorServiceMock );
        }
    }

    @Test( expected = RuntimeException.class )
    public void test_registerUser_validDataFailEmailSending()
        throws Exception
    {
        // prepare
        userRepositoryMock.save( Mockito.any( User.class ) );
        Mockito.when( emailGeneratorServiceMock.getSubject( EmailType.REGISTER_USER, null, Locale.UK ) ).thenReturn( "Subject" );
        Mockito.when( emailGeneratorServiceMock.getContent( Mockito.any( EmailType.class ), Mockito.any( Map.class ),
                                                            Mockito.any( Locale.class ) ) ).thenReturn( "Content" );
        Mockito.doThrow( new MessagingException( "4 testing" ) ).when( emailServiceMock ).sendEmail( "email@test.com",
                                                                                                     "Subject",
                                                                                                     "Content" );
        Mockito.when( fimUrlGeneratorServiceMock.getOneTimeLinkLink( Mockito.any( UserOneTimeLink.class ) ) ).thenReturn( "url" );
        // call
        try
        {
            userManagetServiceImpl.registerUser( "email@test.com", "abc", "First", "Name", Locale.UK );
        }
        finally
        {
            // verify
            Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( User.class ) );
            Mockito.verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getSubject( EmailType.REGISTER_USER,
                                                                                           null, Locale.UK );
            Mockito.verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getContent( Mockito.any( EmailType.class ),
                                                                                           Mockito.any( Map.class ),
                                                                                           Mockito.any( Locale.class ) );
            Mockito.verify( emailServiceMock, Mockito.atLeastOnce() ).sendEmail( "email@test.com", "Subject", "Content" );
            Mockito.verify( fimUrlGeneratorServiceMock, Mockito.atLeastOnce() ).getOneTimeLinkLink( Mockito.any( UserOneTimeLink.class ) );
        }
    }

    @Test( expected = PersistenceException.class )
    public void test_registerUser_simulateOtherError()
    {
        // prepare
        Mockito.when( userRepositoryMock.save( Mockito.any( User.class ) ) ).thenThrow( new PersistenceException(
                                                                                                                  new IllegalArgumentException(
                                                                                                                                                "4 testing" ) ) );
        // call
        try
        {
            userManagetServiceImpl.registerUser( "email@test.com", "abc", "First", "Name", Locale.UK );
        }
        finally
        {
            // verify
            Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( User.class ) );
            Mockito.verifyZeroInteractions( emailGeneratorServiceMock, emailServiceMock, fimUrlGeneratorServiceMock );
        }
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

    @Test
    public void test_login_userNotFound()
    {
        // prepare
        User user = null;
        Mockito.when( userRepositoryMock.findBy( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( user );
        // call
        User responseUser = userManagetServiceImpl.login( "user1", "password1" );
        // verify
        Assert.assertNull( "User should not be null", responseUser );
        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findBy( Mockito.anyString(), Mockito.anyString() );
    }

    @Test
    public void test_login_newUserData()
    {
        // prepare
        User user = new User();
        user.setId( 1 );
        user.setStatus( UserStatus.NEW );
        Mockito.when( userRepositoryMock.findBy( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( user );
        // call
        User responseUser = userManagetServiceImpl.login( "user1", "password1" );
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findBy( Mockito.anyString(), Mockito.anyString() );
    }

    @Test
    public void test_login_disabledUserData()
    {
        // prepare
        User user = new User();
        user.setId( 1 );
        user.setStatus( UserStatus.DISABLED );
        Mockito.when( userRepositoryMock.findBy( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( user );
        // call
        User responseUser = userManagetServiceImpl.login( "user1", "password1" );
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findBy( Mockito.anyString(), Mockito.anyString() );
    }
}
