/**
 * 
 */
package pfa.alliance.fim.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pfa.alliance.fim.dao.RoleAndPermissionRepository;
import pfa.alliance.fim.dao.UserOneTimeLinkRepository;
import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.dto.UserSearchDTO;
import pfa.alliance.fim.dto.UserSearchResultDTO;
import pfa.alliance.fim.model.project.UserProjectRelation;
import pfa.alliance.fim.model.user.OneTimeLinkType;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserOneTimeLink;
import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.EmailGeneratorService;
import pfa.alliance.fim.service.EmailService;
import pfa.alliance.fim.service.EmailType;
import pfa.alliance.fim.service.FimUrlGeneratorService;
import pfa.alliance.fim.service.InvalidUserPasswordException;
import pfa.alliance.fim.service.LoggedInUserDTO;
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

    private RoleAndPermissionRepository roleAndPermissionRepositoryMock;

    @Before
    public void init()
    {
        userRepositoryMock = Mockito.mock( UserRepository.class );
        emailServiceMock = Mockito.mock( EmailService.class );
        emailGeneratorServiceMock = Mockito.mock( EmailGeneratorService.class );
        fimUrlGeneratorServiceMock = Mockito.mock( FimUrlGeneratorService.class );
        userOneTimeLinkRepositoryMock = Mockito.mock( UserOneTimeLinkRepository.class );
        roleAndPermissionRepositoryMock = Mockito.mock( RoleAndPermissionRepository.class );
        userManagetServiceImpl =
            new UserManagerServiceImpl( userRepositoryMock, emailServiceMock, emailGeneratorServiceMock,
                                        fimUrlGeneratorServiceMock, userOneTimeLinkRepositoryMock,
                                        roleAndPermissionRepositoryMock );
    }

    @Test
    public void test_registerUser_validData()
        throws Exception
    {
        // prepare
        userRepositoryMock.save( any( User.class ) );
        when( emailGeneratorServiceMock.getSubject( EmailType.REGISTER_USER, null, Locale.UK ) ).thenReturn( "Subject" );
        when( emailGeneratorServiceMock.getContent( any( EmailType.class ), any( Map.class ), any( Locale.class ) ) ).thenReturn( "Content" );
        when( fimUrlGeneratorServiceMock.getOneTimeLinkLink( any( UserOneTimeLink.class ) ) ).thenReturn( "url" );
        emailServiceMock.sendEmail( "email@test.com", "Subject", "Content" );
        // call
        userManagetServiceImpl.registerUser( "email@test.com", "abc", "First", "Name", Locale.UK );
        // verify
        verify( userRepositoryMock, Mockito.atLeastOnce() ).save( any( User.class ) );
        verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getSubject( EmailType.REGISTER_USER, null, Locale.UK );
        verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getContent( any( EmailType.class ),
                                                                               any( Map.class ), any( Locale.class ) );
        verify( emailServiceMock, Mockito.atLeastOnce() ).sendEmail( "email@test.com", "Subject", "Content" );
        verify( fimUrlGeneratorServiceMock, Mockito.atLeastOnce() ).getOneTimeLinkLink( any( UserOneTimeLink.class ) );
    }

    @Test
    public void test_inviteUser_validData()
        throws Exception
    {
        // prepare
        userRepositoryMock.save( any( User.class ) );
        when( emailGeneratorServiceMock.getSubject( EmailType.INVITE_USER, null, Locale.UK ) ).thenReturn( "Subject" );
        when( emailGeneratorServiceMock.getContent( any( EmailType.class ), any( Map.class ), any( Locale.class ) ) ).thenReturn( "Content" );
        when( fimUrlGeneratorServiceMock.getOneTimeLinkLink( any( UserOneTimeLink.class ) ) ).thenReturn( "url" );
        emailServiceMock.sendEmail( "email@test.com", "Subject", "Content" );
        // call
        userManagetServiceImpl.inviteUser( "email@test.com", "First", "Name", UserRole.STATISTICAL, Locale.UK );
        // verify
        verify( userRepositoryMock, Mockito.atLeastOnce() ).save( any( User.class ) );
        verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getSubject( EmailType.INVITE_USER, null, Locale.UK );
        verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getContent( any( EmailType.class ),
                                                                               any( Map.class ), any( Locale.class ) );
        verify( emailServiceMock, Mockito.atLeastOnce() ).sendEmail( "email@test.com", "Subject", "Content" );
        verify( fimUrlGeneratorServiceMock, Mockito.atLeastOnce() ).getOneTimeLinkLink( any( UserOneTimeLink.class ) );
    }

    @Test( expected = DuplicateDataException.class )
    public void test_registerUser_duplicatedData()
    {
        // prepare
        when( userRepositoryMock.save( any( User.class ) ) ).thenThrow( new PersistenceException(
                                                                                                  "These data violates unique constraint" ) );
        // call
        try
        {
            userManagetServiceImpl.registerUser( "email@test.com", "abc", "First", "Name", Locale.UK );
        }
        finally
        {
            // verify
            verify( userRepositoryMock, Mockito.atLeastOnce() ).save( any( User.class ) );
            Mockito.verifyZeroInteractions( emailGeneratorServiceMock, emailServiceMock, fimUrlGeneratorServiceMock );
        }
    }

    @Test( expected = RuntimeException.class )
    public void test_registerUser_validDataFailEmailSending()
        throws Exception
    {
        // prepare
        userRepositoryMock.save( any( User.class ) );
        when( emailGeneratorServiceMock.getSubject( EmailType.REGISTER_USER, null, Locale.UK ) ).thenReturn( "Subject" );
        when( emailGeneratorServiceMock.getContent( any( EmailType.class ), any( Map.class ), any( Locale.class ) ) ).thenReturn( "Content" );
        Mockito.doThrow( new MessagingException( "4 testing" ) ).when( emailServiceMock ).sendEmail( "email@test.com",
                                                                                                     "Subject",
                                                                                                     "Content" );
        when( fimUrlGeneratorServiceMock.getOneTimeLinkLink( any( UserOneTimeLink.class ) ) ).thenReturn( "url" );
        // call
        try
        {
            userManagetServiceImpl.registerUser( "email@test.com", "abc", "First", "Name", Locale.UK );
        }
        finally
        {
            // verify
            verify( userRepositoryMock, Mockito.atLeastOnce() ).save( any( User.class ) );
            verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getSubject( EmailType.REGISTER_USER, null,
                                                                                   Locale.UK );
            verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getContent( any( EmailType.class ),
                                                                                   any( Map.class ), any( Locale.class ) );
            verify( emailServiceMock, Mockito.atLeastOnce() ).sendEmail( "email@test.com", "Subject", "Content" );
            verify( fimUrlGeneratorServiceMock, Mockito.atLeastOnce() ).getOneTimeLinkLink( any( UserOneTimeLink.class ) );
        }
    }

    @Test( expected = PersistenceException.class )
    public void test_registerUser_simulateOtherError()
    {
        // prepare
        when( userRepositoryMock.save( any( User.class ) ) ).thenThrow( new PersistenceException(
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
            verify( userRepositoryMock, Mockito.atLeastOnce() ).save( any( User.class ) );
            Mockito.verifyZeroInteractions( emailGeneratorServiceMock, emailServiceMock, fimUrlGeneratorServiceMock );
        }
    }

    @Test
    public void test_login_validDataNoProjectAssigned()
    {
        // prepare
        User user = new User();
        user.setId( 1 );
        user.setStatus( UserStatus.ACTIVE );
        user.setUserProjectRelation( new HashSet<>() );
        user.setDefaultRole( UserRole.TEAM );
        when( userRepositoryMock.findBy( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( user );
        // call
        LoggedInUserDTO responseUserDto = userManagetServiceImpl.login( "user1", "password1" );
        Assert.assertNotNull( "LoggedInUserDTO should NOT be null", responseUserDto );
        User responseUser = responseUserDto.getUser();
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findBy( Mockito.anyString(), Mockito.anyString() );
    }

    @Test
    public void test_login_validDataWithProjectAssigned()
    {
        // prepare
        User user = new User();
        user.setId( 1 );
        user.setStatus( UserStatus.ACTIVE );
        // TODO this must be changed
        Set<UserProjectRelation> relations = new HashSet<>();
        user.setUserProjectRelation( relations );
        user.setDefaultRole( UserRole.TEAM );
        when( userRepositoryMock.findBy( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( user );
        // call
        LoggedInUserDTO responseUserDto = userManagetServiceImpl.login( "user1", "password1" );
        Assert.assertNotNull( "LoggedInUserDTO should NOT be null", responseUserDto );
        User responseUser = responseUserDto.getUser();
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findBy( Mockito.anyString(), Mockito.anyString() );
    }

    @Test
    public void test_login_userNotFound()
    {
        // prepare
        User user = null;
        when( userRepositoryMock.findBy( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( user );
        // call
        LoggedInUserDTO responseUserDto = userManagetServiceImpl.login( "user1", "password1" );
        // verify
        Assert.assertNull( "LoggedInUserDTO should be null", responseUserDto );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findBy( Mockito.anyString(), Mockito.anyString() );
    }

    @Test
    public void test_login_newUserData()
    {
        // prepare
        User user = new User();
        user.setId( 1 );
        user.setStatus( UserStatus.NEW );
        when( userRepositoryMock.findBy( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( user );
        // call
        LoggedInUserDTO responseUserDto = userManagetServiceImpl.login( "user1", "password1" );
        Assert.assertNotNull( "LoggedInUserDTO should NOT be null", responseUserDto );
        User responseUser = responseUserDto.getUser();
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findBy( Mockito.anyString(), Mockito.anyString() );
    }

    @Test
    public void test_login_disabledUserData()
    {
        // prepare
        User user = new User();
        user.setId( 1 );
        user.setStatus( UserStatus.DISABLED );
        when( userRepositoryMock.findBy( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( user );
        // call
        LoggedInUserDTO responseUserDto = userManagetServiceImpl.login( "user1", "password1" );
        Assert.assertNotNull( "LoggedInUserDTO should NOT be null", responseUserDto );
        User responseUser = responseUserDto.getUser();
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findBy( Mockito.anyString(), Mockito.anyString() );
    }

    @Test
    public void test_forgotPassword_noUser()
        throws Exception
    {
        // prepare
        User user = null;
        when( userRepositoryMock.findByUsername( "user1" ) ).thenReturn( user );
        // call
        User responseUser = userManagetServiceImpl.forgotPassword( "user1", Locale.US );
        // verify
        Assert.assertNull( "User should be null", responseUser );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findByUsername( Mockito.anyString() );
    }

    @Test
    public void test_forgotPassword_disabledUser()
        throws Exception
    {
        // prepare
        User user = new User();
        user.setId( 1 );
        user.setStatus( UserStatus.DISABLED );
        when( userRepositoryMock.findByUsername( "user1" ) ).thenReturn( user );
        // call
        User responseUser = userManagetServiceImpl.forgotPassword( "user1", Locale.US );
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findByUsername( Mockito.anyString() );
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
        when( userRepositoryMock.findByUsername( "user1" ) ).thenReturn( user );
        when( emailGeneratorServiceMock.getSubject( EmailType.FORGOT_PASSWORD, null, Locale.US ) ).thenReturn( "Forgot password: subject" );
        when( fimUrlGeneratorServiceMock.getOneTimeLinkLink( any( UserOneTimeLink.class ) ) ).thenReturn( "http://link" );
        when( emailGeneratorServiceMock.getContent( any( EmailType.class ), Mockito.anyMap(), any( Locale.class ) ) ).thenReturn( "content" );
        // call
        User responseUser = userManagetServiceImpl.forgotPassword( "user1", Locale.US );
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        // new record is not added to to user
        Assert.assertNull( "Links should be null", responseUser.getOneTimeLinks() );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findByUsername( Mockito.anyString() );
        verify( userOneTimeLinkRepositoryMock, Mockito.atLeastOnce() ).save( any( UserOneTimeLink.class ) );
        verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getSubject( EmailType.FORGOT_PASSWORD, null,
                                                                               Locale.US );
        verify( fimUrlGeneratorServiceMock, Mockito.atLeastOnce() ).getOneTimeLinkLink( any( UserOneTimeLink.class ) );
        verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getContent( any( EmailType.class ),
                                                                               Mockito.anyMap(), any( Locale.class ) );
        verify( emailServiceMock, Mockito.atLeastOnce() ).sendEmail( "user1@email.com", "Forgot password: subject",
                                                                     "content" );
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
        when( userRepositoryMock.findByUsername( "user1" ) ).thenReturn( user );
        when( emailGeneratorServiceMock.getSubject( EmailType.FORGOT_PASSWORD, null, Locale.US ) ).thenReturn( "Forgot password: subject" );
        when( fimUrlGeneratorServiceMock.getOneTimeLinkLink( any( UserOneTimeLink.class ) ) ).thenReturn( "http://link" );
        when( emailGeneratorServiceMock.getContent( any( EmailType.class ), Mockito.anyMap(), any( Locale.class ) ) ).thenReturn( "content" );
        // call
        User responseUser = userManagetServiceImpl.forgotPassword( "user1", Locale.US );
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        // new record is not added to to user
        Assert.assertNotNull( "Links should NOT be null", responseUser.getOneTimeLinks() );
        Assert.assertEquals( "Links size is wrong", 2, responseUser.getOneTimeLinks().size() );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findByUsername( Mockito.anyString() );
        verify( userOneTimeLinkRepositoryMock, Mockito.atLeastOnce() ).save( any( UserOneTimeLink.class ) );
        verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getSubject( EmailType.FORGOT_PASSWORD, null,
                                                                               Locale.US );
        verify( fimUrlGeneratorServiceMock, Mockito.atLeastOnce() ).getOneTimeLinkLink( any( UserOneTimeLink.class ) );
        verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getContent( any( EmailType.class ),
                                                                               Mockito.anyMap(), any( Locale.class ) );
        verify( emailServiceMock, Mockito.atLeastOnce() ).sendEmail( "user1@email.com", "Forgot password: subject",
                                                                     "content" );
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
        when( userRepositoryMock.findByUsername( "user1" ) ).thenReturn( user );
        when( emailGeneratorServiceMock.getSubject( EmailType.FORGOT_PASSWORD, null, Locale.US ) ).thenReturn( "Forgot password: subject" );
        when( fimUrlGeneratorServiceMock.getOneTimeLinkLink( any( UserOneTimeLink.class ) ) ).thenReturn( "http://link" );
        when( emailGeneratorServiceMock.getContent( any( EmailType.class ), Mockito.anyMap(), any( Locale.class ) ) ).thenReturn( "content" );
        // call
        User responseUser = userManagetServiceImpl.forgotPassword( "user1", Locale.US );
        // verify
        Assert.assertNotNull( "User should not be null", responseUser );
        Assert.assertSame( "User incompatibility", user, responseUser );
        // new record is not added to to user
        Assert.assertNotNull( "Links should NOT be null", responseUser.getOneTimeLinks() );
        Assert.assertEquals( "Links size is wrong", 3, responseUser.getOneTimeLinks().size() );
        Assert.assertTrue( "Link timestamp issue: " + link.getExpiresAt() + " > " + ts, link.getExpiresAt().after( ts ) );

        verify( userRepositoryMock, Mockito.atLeastOnce() ).findByUsername( Mockito.anyString() );
        verify( userOneTimeLinkRepositoryMock, Mockito.atLeastOnce() ).save( any( UserOneTimeLink.class ) );
        verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getSubject( EmailType.FORGOT_PASSWORD, null,
                                                                               Locale.US );
        verify( fimUrlGeneratorServiceMock, Mockito.atLeastOnce() ).getOneTimeLinkLink( any( UserOneTimeLink.class ) );
        verify( emailGeneratorServiceMock, Mockito.atLeastOnce() ).getContent( any( EmailType.class ),
                                                                               Mockito.anyMap(), any( Locale.class ) );
        verify( emailServiceMock, Mockito.atLeastOnce() ).sendEmail( "user1@email.com", "Forgot password: subject",
                                                                     "content" );
    }

    @Test( expected = UserActivationFailException.class )
    public void test_activateUser_invalidUuid()
    {
        UserOneTimeLink link = null;
        when( userRepositoryMock.getOneTimeLinkBy( "abc", OneTimeLinkType.USER_REGISTRATION ) ).thenReturn( link );

        try
        {
            userManagetServiceImpl.activateUser( "abc" );
        }
        finally
        {
            verify( userRepositoryMock, Mockito.atLeastOnce() ).getOneTimeLinkBy( "abc",
                                                                                  OneTimeLinkType.USER_REGISTRATION );
        }
    }

    @Test( expected = UserActivationFailException.class )
    public void test_activateUser_expiredUuid()
    {
        UserOneTimeLink link = new UserOneTimeLink();
        link.setExpiresAt( new Timestamp( System.currentTimeMillis() - 1000L ) );
        when( userRepositoryMock.getOneTimeLinkBy( "abc", OneTimeLinkType.USER_REGISTRATION ) ).thenReturn( link );

        try
        {
            userManagetServiceImpl.activateUser( "abc" );
        }
        finally
        {
            verify( userRepositoryMock, Mockito.atLeastOnce() ).getOneTimeLinkBy( "abc",
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
        when( userRepositoryMock.getOneTimeLinkBy( "abc", OneTimeLinkType.USER_REGISTRATION ) ).thenReturn( link );

        try
        {
            userManagetServiceImpl.activateUser( "abc" );
        }
        finally
        {
            verify( userRepositoryMock, Mockito.atLeastOnce() ).getOneTimeLinkBy( "abc",
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
        when( userRepositoryMock.getOneTimeLinkBy( "abc", OneTimeLinkType.USER_REGISTRATION ) ).thenReturn( link );

        userManagetServiceImpl.activateUser( "abc" );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).getOneTimeLinkBy( "abc", OneTimeLinkType.USER_REGISTRATION );

        Assert.assertEquals( "User status error", UserStatus.ACTIVE, user.getStatus() );
        Assert.assertTrue( "Link expiration issue: " + ts + " > " + link.getExpiresAt(), ts.after( link.getExpiresAt() ) );
    }

    @Test
    public void test_count()
    {
        UserSearchDTO searchDto = new UserSearchDTO();
        when( userRepositoryMock.count( searchDto ) ).thenReturn( 4L );

        long result = userManagetServiceImpl.count( searchDto );
        Assert.assertEquals( "Count issues", 4L, result );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).count( any( UserSearchDTO.class ) );
        Mockito.verifyZeroInteractions( emailServiceMock, emailGeneratorServiceMock, fimUrlGeneratorServiceMock,
                                        userOneTimeLinkRepositoryMock );
    }

    @Test
    public void test_search()
    {
        UserSearchDTO searchDto = new UserSearchDTO();
        final List<UserSearchResultDTO> expected = new ArrayList<UserSearchResultDTO>();
        when( userRepositoryMock.search( searchDto ) ).thenReturn( expected );

        List<UserSearchResultDTO> result = userManagetServiceImpl.search( searchDto );
        Assert.assertNotNull( "Result should not be null", result );
        Assert.assertSame( "Result issues", expected, result );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).search( any( UserSearchDTO.class ) );
        Mockito.verifyZeroInteractions( emailServiceMock, emailGeneratorServiceMock, fimUrlGeneratorServiceMock,
                                        userOneTimeLinkRepositoryMock );
    }

    @Test
    public void test_findById_noUser()
    {
        User user = null;
        when( userRepositoryMock.findOne( 1 ) ).thenReturn( user );

        User result = userManagetServiceImpl.findById( 1 );
        Assert.assertNull( "Should be no user", result );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
        Mockito.verifyZeroInteractions( emailServiceMock, emailGeneratorServiceMock, fimUrlGeneratorServiceMock,
                                        userOneTimeLinkRepositoryMock );
    }

    @Test
    public void test_findById_validUser()
    {
        User user = new User();
        when( userRepositoryMock.findOne( 1 ) ).thenReturn( user );

        User result = userManagetServiceImpl.findById( 1 );
        Assert.assertNotNull( "Should be NO user", result );
        Assert.assertSame( "Wrong user", user, result );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
        Mockito.verifyZeroInteractions( emailServiceMock, emailGeneratorServiceMock, fimUrlGeneratorServiceMock,
                                        userOneTimeLinkRepositoryMock );
    }

    @Test
    public void test_disableUserAtOwnRequest_invalidUser()
    {
        User user = null;
        when( userRepositoryMock.findOne( 1 ) ).thenReturn( user );

        userManagetServiceImpl.disableUserAtOwnRequest( 1, "test" );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
        verify( userRepositoryMock, Mockito.times( 0 ) ).save( any( User.class ) );
        Mockito.verifyZeroInteractions( emailServiceMock, emailGeneratorServiceMock, fimUrlGeneratorServiceMock,
                                        userOneTimeLinkRepositoryMock );
    }

    @Test( expected = InvalidUserPasswordException.class )
    public void test_disableUserAtOwnRequest_invalidUserPassword()
        throws Exception
    {
        User user = new User();
        user.setId( 1 );
        // user.setPassword( encryptUserPassword( "test" ) );
        when( userRepositoryMock.findOne( 1 ) ).thenReturn( user );

        try
        {
            userManagetServiceImpl.disableUserAtOwnRequest( 1, "test" );
        }
        finally
        {
            verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
            verify( userRepositoryMock, Mockito.times( 0 ) ).save( any( User.class ) );
            Mockito.verifyZeroInteractions( emailServiceMock, emailGeneratorServiceMock, fimUrlGeneratorServiceMock,
                                            userOneTimeLinkRepositoryMock );
        }
    }

    @Test
    public void test_disableUserAtOwnRequest_validUser()
        throws Exception
    {
        User user = new User();
        user.setId( 1 );
        user.setPassword( encryptUserPassword( "test" ) );
        when( userRepositoryMock.findOne( 1 ) ).thenReturn( user );

        try
        {
            userManagetServiceImpl.disableUserAtOwnRequest( 1, "test" );
        }
        finally
        {
            verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
            verify( userRepositoryMock, Mockito.atLeastOnce() ).save( any( User.class ) );
            Mockito.verifyZeroInteractions( emailServiceMock, emailGeneratorServiceMock, fimUrlGeneratorServiceMock,
                                            userOneTimeLinkRepositoryMock );
        }
    }

    @Test
    public void test_changePassword_invalidUser()
    {
        User user = null;
        when( userRepositoryMock.findOne( 1 ) ).thenReturn( user );

        userManagetServiceImpl.changePassword( 1, "test", "new-test" );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
        verify( userRepositoryMock, Mockito.times( 0 ) ).save( any( User.class ) );
        Mockito.verifyZeroInteractions( emailServiceMock, emailGeneratorServiceMock, fimUrlGeneratorServiceMock,
                                        userOneTimeLinkRepositoryMock );
    }

    @Test
    public void test_changePassword_validUser()
        throws Exception
    {
        User user = new User();
        user.setId( 1 );
        user.setPassword( encryptUserPassword( "test" ) );
        when( userRepositoryMock.findOne( 1 ) ).thenReturn( user );

        try
        {
            userManagetServiceImpl.changePassword( 1, "test", "new-test" );
        }
        finally
        {
            verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
            verify( userRepositoryMock, Mockito.atLeastOnce() ).save( any( User.class ) );
            Mockito.verifyZeroInteractions( emailServiceMock, emailGeneratorServiceMock, fimUrlGeneratorServiceMock,
                                            userOneTimeLinkRepositoryMock );
        }
    }

    @Test
    public void test_changeUserData_invalidUser()
    {
        User user = null;
        when( userRepositoryMock.findOne( 1 ) ).thenReturn( user );

        userManagetServiceImpl.changeUserData( 1, "First", "Last" );
        verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
        verify( userRepositoryMock, Mockito.times( 0 ) ).save( any( User.class ) );
        Mockito.verifyZeroInteractions( emailServiceMock, emailGeneratorServiceMock, fimUrlGeneratorServiceMock,
                                        userOneTimeLinkRepositoryMock );
    }

    @Test
    public void test_changeUserData_validUser()
        throws Exception
    {
        User user = new User();
        user.setId( 1 );
        when( userRepositoryMock.findOne( 1 ) ).thenReturn( user );

        try
        {
            userManagetServiceImpl.changeUserData( 1, "First", "Last" );
        }
        finally
        {
            verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
            verify( userRepositoryMock, Mockito.atLeastOnce() ).save( any( User.class ) );
            Mockito.verifyZeroInteractions( emailServiceMock, emailGeneratorServiceMock, fimUrlGeneratorServiceMock,
                                            userOneTimeLinkRepositoryMock );
        }
    }

    private String encryptUserPassword( String clearTextPassword )
        throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
        InvocationTargetException
    {
        Method method = UserManagerServiceImpl.class.getDeclaredMethod( "encryptPassword", String.class );
        method.setAccessible( true );
        return (String) method.invoke( userManagetServiceImpl, clearTextPassword );
    }
}
