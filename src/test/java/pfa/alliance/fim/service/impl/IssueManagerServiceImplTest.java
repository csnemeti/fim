/**
 * 
 */
package pfa.alliance.fim.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import pfa.alliance.fim.dao.IssuePriorityRepository;
import pfa.alliance.fim.dao.IssueRepository;
import pfa.alliance.fim.dao.IssueStateRepository;
import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.model.issue.Issue;
import pfa.alliance.fim.service.ConfigurationService;
import pfa.alliance.fim.service.EmailGeneratorService;
import pfa.alliance.fim.service.EmailService;
import pfa.alliance.fim.service.FimUrlGeneratorService;
import pfa.alliance.fim.service.LocalizationService;

/**
 * This class is used for testing {@link IssueManagerServiceImpl}
 * 
 * @author Csaba
 */
public class IssueManagerServiceImplTest
{
    @Mock
    private IssueRepository issueRepositoryMock;

    @Mock
    private IssueStateRepository issueStateRepositoryMock;

    @Mock
    private ProjectRepository projectRepositoryMock;

    @Mock
    private IssuePriorityRepository issuePriorityRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private EmailService emailServiceMock;

    @Mock
    private EmailGeneratorService emailGeneratorServiceMock;

    @Mock
    private FimUrlGeneratorService fimUrlGeneratorServiceMock;

    @Mock
    private LocalizationService localizationServiceMock;

    @Mock
    private ConfigurationService configurationService;

    private IssueManagerServiceImpl service;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks( this );
        service =
            new IssueManagerServiceImpl( issueRepositoryMock, issueStateRepositoryMock, projectRepositoryMock,
                                         userRepositoryMock, issuePriorityRepositoryMock, emailServiceMock,
                                         emailGeneratorServiceMock, fimUrlGeneratorServiceMock,
                                         localizationServiceMock, configurationService );
    }

    @Test
    public void test_findById_noIssue()
    {
        Issue issue = null;
        Mockito.when( issueRepositoryMock.findOne( 1L ) ).thenReturn( issue );

        Issue result = service.findById( 1L );

        Assert.assertNull( "Result should be null", result );
    }

    @Test
    public void test_findById_withIssue()
    {
        Issue issue = new Issue();
        Mockito.when( issueRepositoryMock.findOne( 1L ) ).thenReturn( issue );

        Issue result = service.findById( 1L );

        Assert.assertSame( "Result should be same", issue, result );
    }
}
