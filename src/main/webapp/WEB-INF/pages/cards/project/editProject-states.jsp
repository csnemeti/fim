<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<stripes:layout-definition>
	<h1><fmt:message key="page.title.project.edit.states.issueStates" /></h1>
        <stripes:form beanclass="pfa.alliance.fim.web.stripes.action.project.EditProjectActionBean" focus="labelName" id="compForm" class="form-vertical">  
            <div class="row">
                <div class="col-sm-12">
                    <div class="form-group form-group-sm">
                        <stripes:label class="col-sm-2 control-label" style="text-align: right" for="editProject.issueFlow"/>
                        <div class="col-sm-4">
                            <stripes:select name="issueFlow">
                                <stripes:options-collection collection="${actionBean.issueFlows}" value="id" label="description" />
                            </stripes:select> 
                        </div>
                    </div>                 
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-6" style="padding-left: 0px">
                        	<textarea style="width: 100%; height: 100px" readonly="readonly"></textarea>
                        </div>
                </div>
            </div>
            <stripes:hidden name="code" value="${actionBean.code}" />
            <stripes:hidden name="focus" value="${actionBean.focus}" />
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-1"><stripes:submit class="btn btn-primary" name="updateFlow"><fmt:message key="action.submit" /></stripes:submit></div>
				<div class="col-md-1"><stripes:reset class="btn btn-default" name="reset"><fmt:message key="action.reset" /></stripes:reset></div>
			</div>  
        </stripes:form>
	<hr class="col-md-12">
	<h1><fmt:message key="page.title.project.edit.states.issuePriorities" /></h1>
</stripes:layout-definition>