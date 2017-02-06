<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.ppm.integration.agilesdk.ValueSet
                ,com.ppm.integration.agilesdk.pm.WorkPlanIntegrationContext
                ,com.ppm.integration.agilesdk.pm.JspConstants
                ,com.ppm.integration.agilesdk.connector.local.RALLYLocalIntegrationConnector" %>

<%@ include file="/integrationcenter/sdk/include-workplan-integration.jsp" %>
<%
    String subProjId = ((ValueSet)request.getAttribute(JspConstants.WORKPLAN_INTEGRATION_VALUE_SET)).get("PROJECT");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<link rel="stylesheet" type="text/css" href="/itg/web/new/css/main.css" />
<style>
#link-sub-project {
    margin:0;
}
</style>
</head>
<body>
<div class="button primary" id="link-sub-project"></div>

<script type="text/javascript" src="/itg/integrationcenter/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/itg/web/new/js/ng-common.js"></script>
<script type="text/javascript">
function processResources(data){
    window.getText = function(k){
        return data['<%=RALLYLocalIntegrationConnector.class.getName()%>'][k];
    };
}

$(document).ready(function(){
    $('#link-sub-project')
    .click(function(){
        window.top.open("/itg/project/ViewProject.do?projectView=projectSummary&projectId=<%=subProjId%>","newwindow_" + (Math.random()*10000).toFixed(0));
    })
    .text(getText('SUB_PROJECT_DETAIL'));
});

</script>
<script type="text/javascript" src="/itg/rest2/integration/connector/i18n?jsonp=processResources"></script>
</body>
</html>