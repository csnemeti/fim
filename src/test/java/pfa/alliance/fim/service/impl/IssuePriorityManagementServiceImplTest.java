/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pfa.alliance.fim.dao.IssuePriorityRepository;
import pfa.alliance.fim.model.issue.IssuePriority;

/**
 * This class is used for testing {@link IssuePriorityManagementServiceImpl}
 * 
 * @author Csaba
 */
public class IssuePriorityManagementServiceImplTest
{
    private IssuePriorityRepository issuePriorityRepositoryMock;

    private IssuePriorityManagementServiceImpl service;

    @Before
    public void init()
    {
        issuePriorityRepositoryMock = Mockito.mock( IssuePriorityRepository.class );
        service = new IssuePriorityManagementServiceImpl( issuePriorityRepositoryMock );
    }

    @Test
    public void test_getPrioritiesForProject()
    {
        List<IssuePriority> priorities = new ArrayList<>();
        Mockito.when( issuePriorityRepositoryMock.findAll( 1 ) ).thenReturn( priorities );

        List<IssuePriority> result = service.getPrioritiesForProject( 1 );

        Assert.assertSame( "Result issues", priorities, result );
    }

}
