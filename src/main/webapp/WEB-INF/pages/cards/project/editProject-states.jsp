<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<stripes:layout-definition>
    Issue States (Denis)
    <div class="col-md-3">
        <stripes:form beanclass="pfa.alliance.fim.web.stripes.action.project.EditProjectActionBean" focus="labelName" id="compForm" class="form-vertical">  
            <div class="row">
                <div class="col-sm-12">
                    <div class="form-group form-group-sm">
                        <stripes:label class="col-sm-4 control-label" style="text-align: right" for="editProject.selected.issueFlow"/>
                        <div class="col-sm-6">
                             <stripes:text class="form-control input-sm" name="issueFlow" id="issueFlow" maxlength="100"></stripes:text>
                        </div>
                    </div>                 
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <div class="form-group form-group-sm">
                        <stripes:label class="col-sm-4 control-label" style="text-align: right" for="editProject.issueFlow"/>
                        ${actionBean.selectedIssueFlow}
                        <div class="col-sm-6">
                            <stripes:select name="issueStates">
                                <stripes:options-collection collection="${actionBean.issueStates}" value="id" label="description" />
                            </stripes:select> 
                        </div>
                    </div>                 
                </div>
            </div>
            <stripes:hidden name="code" value="${actionBean.code}" />
            <stripes:hidden name="focus" value="${actionBean.focus}" />
        </stripes:form>
    </div>
</stripes:layout-definition>