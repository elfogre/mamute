<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@attribute name="recentTags" type="java.util.List" required="true"%>
<%@attribute name="relatedQuestions" type="java.util.List" required="false"%>

<aside class="sidebar">
	<c:set var="newses" value="${sidebarNews}" scope="request" />
	<tags:brutal-include value="homeNewsList" />
	<tags:recentTagsUsage tagsUsage="${recentTags}"/>
	<c:if test="${relatedQuestions != null }">
		<tags:relatedQuestions questions="${relatedQuestions}"/>
	</c:if>
</aside>
