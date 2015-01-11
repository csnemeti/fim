/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pfa.alliance.fim.dao.UserOneTimeLinkRepository;
import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.model.user.OneTimeLinkType;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserOneTimeLink;
import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.EmailGeneratorService;
import pfa.alliance.fim.service.EmailService;
import pfa.alliance.fim.service.EmailType;
import pfa.alliance.fim.service.FimUrlGeneratorService;
import pfa.alliance.fim.service.UserActivationFailException;

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
                                        fimUrlGeneratorServiceMock, userOneTimeLinkRepositoryMock );
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

    @Test
    public void test_inviteUser_validData()
        throws Exception
    {
        // prepare
        userRepositoryMock.save( Mockito.any( User.class ) );
        Mockito.when( emailGeneratorServiceMock.getSubject( EmailType.INVITE_USER, null, Locale.UK ) ).thenReturn( "Subject" );
        Mockito.when( emailGeneratorServiceMock.getContent( Mockito.any( EmailType.class ), Mockito.any( Map.class ),
                                                            Mockito.any( Locale.class ) ) ).thenReturn( "Content" );
        Mockito.when( fimUrlGeneratorServiceMock.getOneTimeLinkLink( Mockito.any( UserOneTimeLink.class ) ) ).thenReturn( "url" );
        emailServiceMock.sendEmail( "email@test.com", "Subject", "Content" );
        // call
        userManagetServiceImpl.inviteUser( "email@test.com", "First", "Name", UserRole.STATISTICAL, Locale.UK );
        // verify
        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( User.class ) );
        Mockito.verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getSubject( EmailType.INVITE_USER, null,
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

    @Test
    public void test_forgotPassword_noUser()
        throws Exception
    {
        // prepare
        User user = null;
        Mockito.when( userRepositoryMock.findByUsername( "user1" ) ).thenReturn( user );
        // call
        User responseUser = userManagetServiceImpl.forgotPassword( "user1", Locale.US );
        // verify
        Assert.assertNull( "User should be null", responseUser );
        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findByUsername( Mockito.anyString() );
    }

    @Test
    public void test_forgotPassword_disabledUser()
        throws Exception
    {
        // prepare
        User user = new User();
        user.setId( 1 );
        user.setStatus( UserStatus.DISABLED );
        Mockito.when( userRepositoryMock.findByUsername( "user1" ) ).thenReturn( user );
        // call
        User responseUser = userManagetServiceImpl.forgotPassword( "user1", Locale.US );
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findByUsername( Mockito.anyString() );
    }

    @Test
    public void test_forgotPassword_activeUserNoOneTimeLinks()
        throws Exception
    {
        // prepare
        User user = new User();
        user.setId( 1 );
        user.setStatus( UserStatus.ACTIVE );
        user.setEmail( "user1@email.com" );
        user.setFirstName( "First" );
        user.setLastName( "Last" );
        Mockito.when( userRepositoryMock.findByUsername( "user1" ) ).thenReturn( user );
        Mockito.when( emailGeneratorServiceMock.getSubject( EmailType.FORGOT_PASSWORD, null, Locale.US ) ).thenReturn( "Forgot password: subject" );
        Mockito.when( fimUrlGeneratorServiceMock.getOneTimeLinkLink( Mockito.any( UserOneTimeLink.class ) ) ).thenReturn( "http://link" );
        Mockito.when( emailGeneratorServiceMock.getContent( Mockito.any( EmailType.class ), Mockito.anyMap(),
                                                            Mockito.any( Locale.class ) ) ).thenReturn( "content" );
        // call
        User responseUser = userManagetServiceImpl.forgotPassword( "user1", Locale.US );
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        // new record is not added to to user
        Assert.assertNull( "Links should be null", responseUser.getOneTimeLinks() );
        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findByUsername( Mockito.anyString() );
        Mockito.verify( userOneTimeLinkRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( UserOneTimeLink.class ) );
        Mockito.verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getSubject( EmailType.FORGOT_PASSWORD, null,
                                                                                       Locale.US );
        Mockito.verify( fimUrlGeneratorServiceMock, Mockito.atLeastOnce() ).getOneTimeLinkLink( Mockito.any( UserOneTimeLink.class ) );
        Mockito.verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getContent( Mockito.any( EmailType.class ),
                                                                                       Mockito.anyMap(),
                                                                                       Mockito.any( Locale.class ) );
        Mockito.verify( emailServiceMock, Mockito.atLeastOnce() ).sendEmail( "user1@email.com",
                                                                             "Forgot password: subject", "content" );
    }

    @Test
    public void test_forgotPassword_activeUserUselessOneTimeLinks()
        throws Exception
    {
        // prepare
        User user = new User();
        user.setId( 1 );
        user.setStatus( UserStatus.ACTIVE );
        user.setEmail( "user1@email.com" );
        user.setFirstName( "First" );
        user.setLastName( "Last" );
        Set<UserOneTimeLink> links = new HashSet<UserOneTimeLink>();
        UserOneTimeLink link = new UserOneTimeLink();
        link.setDesignation( OneTimeLinkType.USER_INVITE );
        link.setExpiresAt( new Timestamp( System.currentTimeMillis() + 100000000L ) );
        links.add( link );
        link = new UserOneTimeLink();
        link.setDesignation( OneTimeLinkType.FORGOT_PASWORD );
        link.setExpiresAt( new Timestamp( System.currentTimeMillis() - 100000000L ) );
        links.add( link );
        user.setOneTimeLinks( links );
        Mockito.when( userRepositoryMock.findByUsername( "user1" ) ).thenReturn( user );
        Mockito.when( emailGeneratorServiceMock.getSubject( EmailType.FORGOT_PASSWORD, null, Locale.US ) ).thenReturn( "Forgot password: subject" );
        Mockito.when( fimUrlGeneratorServiceMock.getOneTimeLinkLink( Mockito.any( UserOneTimeLink.class ) ) ).thenReturn( "http://link" );
        Mockito.when( emailGeneratorServiceMock.getContent( Mockito.any( EmailType.class ), Mockito.anyMap(),
                                                            Mockito.any( Locale.class ) ) ).thenReturn( "content" );
        // call
        User responseUser = userManagetServiceImpl.forgotPassword( "user1", Locale.US );
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        // new record is not added to to user
        Assert.assertNotNull( "Links should NOT be null", responseUser.getOneTimeLinks() );
        Assert.assertEquals( "Links size is wrong", 2, responseUser.getOneTimeLinks().size() );
        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findByUsername( Mockito.anyString() );
        Mockito.verify( userOneTimeLinkRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( UserOneTimeLink.class ) );
        Mockito.verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getSubject( EmailType.FORGOT_PASSWORD, null,
                                                                                       Locale.US );
        Mockito.verify( fimUrlGeneratorServiceMock, Mockito.atLeastOnce() ).getOneTimeLinkLink( Mockito.any( UserOneTimeLink.class ) );
        Mockito.verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getContent( Mockito.any( EmailType.class ),
                                                                                       Mockito.anyMap(),
                                                                                       Mockito.any( Locale.class ) );
        Mockito.verify( emailServiceMock, Mockito.atLeastOnce() ).sendEmail( "user1@email.com",
                                                                             "Forgot password: subject", "content" );
    }

    @Test
    public void test_forgotPassword_activeUserExtendingOneTimeLinks()
        throws Exception
    {
        // prepare
        User user = new User();
        user.setId( 1 );
        user.setStatus( UserStatus.ACTIVE );
        user.setEmail( "user1@email.com" );
        user.setFirstName( "First" );
        user.setLastName( "Last" );
        Set<UserOneTimeLink> links = new HashSet<UserOneTimeLink>();
        UserOneTimeLink link = new UserOneTimeLink();
        link.setUser( user );
        link.setDesignation( OneTimeLinkType.USER_INVITE );
        link.setExpiresAt( new Timestamp( System.currentTimeMillis() + 10000L ) );
        links.add( link );
        link = new UserOneTimeLink();
        link.setUser( user );
        link.setDesignation( OneTimeLinkType.FORGOT_PASWORD );
        link.setExpiresAt( new Timestamp( System.currentTimeMillis() - 20000L ) );
        links.add( link );
        // this must be the last one because we're extending this record lifetime
        link = new UserOneTimeLink();
        link.setUser( user );
        link.setDesignation( OneTimeLinkType.FORGOT_PASWORD );
        final Timestamp ts = new Timestamp( System.currentTimeMillis() + 30000L );
        link.setExpiresAt( ts );
        links.add( link );
        user.setOneTimeLinks( links );
        Mockito.when( userRepositoryMock.findByUsername( "user1" ) ).thenReturn( user );
        Mockito.when( emailGeneratorServiceMock.getSubject( EmailType.FORGOT_PASSWORD, null, Locale.US ) ).thenReturn( "Forgot password: subject" );
        Mockito.when( fimUrlGeneratorServiceMock.getOneTimeLinkLink( Mockito.any( UserOneTimeLink.class ) ) ).thenReturn( "http://link" );
        Mockito.when( emailGeneratorServiceMock.getContent( Mockito.any( EmailType.class ), Mockito.anyMap(),
                                                            Mockito.any( Locale.class ) ) ).thenReturn( "content" );
        // call
        User responseUser = userManagetServiceImpl.forgotPassword( "user1", Locale.US );
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        // new record is not added to to user
        Assert.assertNotNull( "Links should NOT be null", responseUser.getOneTimeLinks() );
        Assert.assertEquals( "Links size is wrong", 3, responseUser.getOneTimeLinks().size() );
        Assert.assertTrue( "Link timestamp issue: " + link.getExpiresAt() + " > " + ts, link.getExpiresAt().after( ts ) );

        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findByUsername( Mockito.anyString() );
        Mockito.verify( userOneTimeLinkRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( UserOneTimeLink.class ) );
        Mockito.verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getSubject( EmailType.FORGOT_PASSWORD, null,
                                                                                       Locale.US );
        Mockito.verify( fimUrlGeneratorServiceMock, Mockito.atLeastOnce() ).getOneTimeLinkLink( Mockito.any( UserOneTimeLink.class ) );
        Mockito.verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getContent( Mockito.any( EmailType.class ),
                                                                                       Mockito.anyMap(),
                                                                                       Mockito.any( Locale.class ) );
        Mockito.verify( emailServiceMock, Mockito.atLeastOnce() ).sendEmail( "user1@email.com",
                                                                             "Forgot password: subject", "content" );
    }

    @Test( expected = UserActivationFailException.class )
    public void test_activateUser_invalidUuid()
    {
        UserOneTimeLink link = null;
        Mockito.when( userRepositoryMock.getOneTimeLinkBy( "abc", OneTimeLinkType.USER_REGISTRATION ) ).thenReturn( link );

        try
        {
            userManagetServiceImpl.activateUser( "abc" );
        }
        finally
        {
            Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).getOneTimeLinkBy( "abc",
                                                                                          OneTimeLinkType.USER_REGISTRATION );
        }
    }

    @Test( expected = UserActivationFailException.class )
    public void test_activateUser_expiredUuid()
    {
        UserOneTimeLink link = new UserOneTimeLink();
        link.setExpiresAt( new Timestamp( System.currentTimeMillis() - 1000L ) );
        Mockito.when( userRepositoryMock.getOneTimeLinkBy( "abc", OneTimeLinkType.USER_REGISTRATION ) ).thenReturn( link );

        try
        {
            userManagetServiceImpl.activateUser( "abc" );
        }
        finally
        {
            Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).getOneTimeLinkBy( "abc",
                                                                                          OneTimeLinkType.USER_REGISTRATION );
        }
    }

    @Test( expected = UserActivationFailException.class )
    public void test_activateUser_alreadyActiveuser()
    {
        UserOneTimeLink link = new UserOneTimeLink();
        link.setExpiresAt( new Timestamp( System.currentTimeMillis() + 2000L ) );
        User user = new User();
        user.setStatus( UserStatus.ACTIVE );
        link.setUser( user );
        Mockito.when( userRepositoryMock.getOneTimeLinkBy( "abc", OneTimeLinkType.USER_REGISTRATION ) ).thenReturn( link );

        try
        {
            userManagetServiceImpl.activateUser( "abc" );
        }
        finally
        {
            Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).getOneTimeLinkBy( "abc",
                                                                                          OneTimeLinkType.USER_REGISTRATION );
        }
    }

    @Test
    public void test_activateUser_sucessfull()
    {
        UserOneTimeLink link = new UserOneTimeLink();
        Timestamp ts = new Timestamp( System.currentTimeMillis() + 2000L );
        link.setExpiresAt( ts );
        User user = new User();
        user.setStatus( UserStatus.NEW );
        link.setUser( user );
        Mockito.when( userRepositoryMock.getOneTimeLinkBy( "abc", OneTimeLinkType.USER_REGISTRATION ) ).thenReturn( link );

        userManagetServiceImpl.activateUser( "abc" );
        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).getOneTimeLinkBy( "abc",
                                                                                      OneTimeLinkType.USER_REGISTRATION );
        
        Assert.assertEquals( "User status error", UserStatus.ACTIVE, user.getStatus() );
        Assert.assertTrue( "Link expiration issue: " + ts + " > " + link.getExpiresAt(), ts.after( link.getExpiresAt() ) );
    }
}
